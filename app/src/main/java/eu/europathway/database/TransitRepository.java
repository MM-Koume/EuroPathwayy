package eu.europathway.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import eu.europathway.database.dao.*;
import eu.europathway.database.entities.*;
import eu.europathway.network.RetrofitClient;
import eu.europathway.network.TransitApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Repository class - Single source of truth for data
 * Abstracts access to multiple data sources (local DB, REST API, etc.)
 *
 * IMPORTANT: This is designed to work locally now, but can easily be extended
 * to sync with a server API later.
 */
public class TransitRepository {

    private final CityDao cityDao;
    private final StopDao stopDao;
    private final RouteDao routeDao;
    private final TripDao tripDao;
    private final StopTimeDao stopTimeDao;
    private final LiveDepartureDao liveDepartureDao;
    private final AlertDao alertDao;
    private final FareDao fareDao;
    private final TransitApiService apiService;


    public TransitRepository(Context context) {
        TransitDatabase db = TransitDatabase.getDatabase(context);

        // Initialize DAOs
        cityDao = db.cityDao();
        stopDao = db.stopDao();
        routeDao = db.routeDao();
        tripDao = db.tripDao();
        stopTimeDao = db.stopTimeDao();
        liveDepartureDao = db.liveDepartureDao();
        alertDao = db.alertDao();
        fareDao = db.fareDao();

        apiService = RetrofitClient.getApiService();
    }

    // ============================================
    // CITY OPERATIONS
    // ============================================

    public LiveData<List<City>> getAllCities() {
        // Return cached data immediately
        LiveData<List<City>> cachedCities = cityDao.getAllCities();

        // Fetch fresh data from API in background
        apiService.getCities().enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TransitDatabase.databaseWriteExecutor.execute(() -> {
                        // Update local database
                        for (City city : response.body()) {
                            cityDao.insert(city);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                Log.e("Repository", "Failed to fetch cities from API", t);
                // App continues to work with cached data
            }
        });

        return cachedCities;
    }

    public LiveData<City> getCityById(int cityId) {
        return cityDao.getCityById(cityId);
    }

    public void insertCity(City city) {
        TransitDatabase.databaseWriteExecutor.execute(() -> {
            cityDao.insert(city);
        });
    }

    // ============================================
    // STOP OPERATIONS
    // ============================================

    public LiveData<List<Stop>> getStopsByCity(int cityId) {
        // Return cached data immediately
        LiveData<List<Stop>> cachedStops = stopDao.getStopsByCity(cityId);

        // Fetch from API
        apiService.getStopsByCity(cityId).enqueue(new Callback<List<Stop>>() {
            @Override
            public void onResponse(Call<List<Stop>> call, Response<List<Stop>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TransitDatabase.databaseWriteExecutor.execute(() -> {
                        stopDao.insertAll(response.body());
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Stop>> call, Throwable t) {
                Log.e("Repository", "Failed to fetch stops from API", t);
            }
        });

        return cachedStops;
    }

    public LiveData<Stop> getStopById(int stopId) {
        return stopDao.getStopById(stopId);
    }

    public LiveData<List<Stop>> searchStops(String query, int cityId) {
        return stopDao.searchStops(query, cityId);
    }

    /**
     * Get nearby stops within a radius
     * @param lat Current latitude
     * @param lon Current longitude
     * @param radius Radius in kilometers
     */
    public void getNearbyStops(double lat, double lon, double radius,
                               ApiCallback<List<Stop>> callback) {
        apiService.getNearbyStops(lat, lon, radius).enqueue(new Callback<List<Stop>>() {
            @Override
            public void onResponse(Call<List<Stop>> call, Response<List<Stop>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Stop>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void insertStop(Stop stop) {
        TransitDatabase.databaseWriteExecutor.execute(() -> {
            stopDao.insert(stop);
        });
    }

    public void insertStops(List<Stop> stops) {
        TransitDatabase.databaseWriteExecutor.execute(() -> {
            stopDao.insertAll(stops);
        });
    }

    // ============================================
    // ROUTE OPERATIONS
    // ============================================

    public LiveData<List<Route>> getRoutesByCity(int cityId) {
        return routeDao.getRoutesByCity(cityId);
    }

    public LiveData<Route> getRouteById(int routeId) {
        return routeDao.getRouteById(routeId);
    }

    public void insertRoute(Route route) {
        TransitDatabase.databaseWriteExecutor.execute(() -> {
            routeDao.insert(route);
        });
    }

    public void insertRoutes(List<Route> routes) {
        TransitDatabase.databaseWriteExecutor.execute(() -> {
            routeDao.insertAll(routes);
        });
    }

    // ============================================
    // LIVE DEPARTURE OPERATIONS
    // ============================================

    public LiveData<List<LiveDeparture>> getLiveDeparturesByStop(int stopId, int limit) {
        return liveDepartureDao.getLiveDeparturesByStop(stopId, limit);
    }

    public void updateLiveDepartures(List<LiveDeparture> departures) {
        TransitDatabase.databaseWriteExecutor.execute(() -> {
            liveDepartureDao.insertAll(departures);
        });
    }

    /**
     * Clean up old real-time data (call this periodically)
     */
    public void cleanOldDepartures() {
        TransitDatabase.databaseWriteExecutor.execute(() -> {
            long oneHourAgo = System.currentTimeMillis() - (60 * 60 * 1000);
            liveDepartureDao.deleteOldDepartures(oneHourAgo);
        });
    }

    // ============================================
    // ALERT OPERATIONS
    // ============================================

    public LiveData<List<Alert>> getActiveAlerts(Integer routeId, Integer stopId) {
        return alertDao.getActiveAlerts(routeId, stopId, System.currentTimeMillis());
    }

    public LiveData<List<Alert>> getAllActiveAlerts() {
        return alertDao.getAllActiveAlerts(System.currentTimeMillis());
    }

    public void insertAlert(Alert alert) {
        TransitDatabase.databaseWriteExecutor.execute(() -> {
            alertDao.insert(alert);
        });
    }

    // ============================================
    // FARE OPERATIONS
    // ============================================

    public LiveData<List<Fare>> getFaresByCity(int cityId) {
        return fareDao.getFaresByCity(cityId);
    }

    public void insertFare(Fare fare) {
        TransitDatabase.databaseWriteExecutor.execute(() -> {
            fareDao.insert(fare);
        });
    }

    // ============================================
    // SERVER SYNC (Placeholder for future implementation)
    // ============================================

    /**
     * Sync data with server API
     * TODO: Implement when you have a backend server
     */
    public void syncWithServer(int cityId) {
        TransitDatabase.databaseWriteExecutor.execute(() -> {
            // Example flow:
            // 1. Fetch data from API (using Retrofit or similar)
            // 2. Parse response
            // 3. Insert into local database
            // 4. Handle conflicts (server data usually wins)

            // Placeholder:
            // ApiService api = RetrofitClient.getInstance().create(ApiService.class);
            // Response<StopsResponse> response = api.getStops(cityId).execute();
            // if (response.isSuccessful()) {
            //     insertStops(response.body().getStops());
            // }
        });
    }

    /**
     * Push local changes to server
     * TODO: Implement when you have a backend
     */
    public void pushToServer() {
        // Implementation for syncing local changes to server
    }
}