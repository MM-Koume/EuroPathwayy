package eu.europathway.network;

import java.util.List;

import eu.europathway.database.entities.Alert;
import eu.europathway.database.entities.City;
import eu.europathway.database.entities.Fare;
import eu.europathway.database.entities.LiveDeparture;
import eu.europathway.database.entities.Route;
import eu.europathway.database.entities.Stop;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

// ============================================
// API SERVICE INTERFACE
// ============================================
public interface TransitApiService {

    // Cities
    @GET("cities")
    Call<List<City>> getCities();

    @GET("cities/{id}")
    Call<City> getCityById(@Path("id") int cityId);

    @GET("cities/{id}/stops")
    Call<List<Stop>> getStopsByCity(@Path("id") int cityId);

    @GET("cities/{id}/routes")
    Call<List<Route>> getRoutesByCity(@Path("id") int cityId);

    @GET("cities/{id}/fares")
    Call<List<Fare>> getFaresByCity(@Path("id") int cityId);

    // Stops
    @GET("stops/{id}")
    Call<Stop> getStopById(@Path("id") int stopId);

    @GET("stops/nearby")
    Call<List<Stop>> getNearbyStops(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("radius") double radiusKm
    );

    @GET("stops/{id}/departures")
    Call<List<LiveDeparture>> getDeparturesByStop(
            @Path("id") int stopId,
            @Query("limit") int limit
    );

    // Routes
    @GET("routes/{id}")
    Call<Route> getRouteById(@Path("id") int routeId);

    // Alerts
    @GET("alerts")
    Call<List<Alert>> getActiveAlerts();
}
