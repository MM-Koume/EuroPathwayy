package eu.europathway;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.maplibre.android.MapLibre;
import org.maplibre.android.geometry.LatLng;
import org.maplibre.android.maps.MapView;
import org.maplibre.android.maps.MapLibreMap;
import org.maplibre.android.maps.OnMapReadyCallback;
import org.maplibre.android.maps.Style;
import org.maplibre.android.annotations.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eu.europathway.database.TransitRepository;
import eu.europathway.database.entities.City;
import eu.europathway.network.RetrofitClient;
import eu.europathway.network.TransitApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView citySelector;
    private final String[] cities = {};

    private List<String> cityNames = null;
    private MapView mapView;
    private MapLibreMap mapLibreMap;
    private TransitRepository repository;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapLibre.getInstance(this);
        setContentView(R.layout.fragment_city_select_new);
        repository = new TransitRepository(this);
        citySelector = findViewById(R.id.citySelector);
        TransitApiService api = RetrofitClient.getApiService();
        api.getCities().enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<City> cities = response.body();
                    cityNames = new ArrayList<>();
                    for (City city : cities) {
                        cityNames.add(city.name);
                    }

                    runOnUiThread(() -> {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                MainActivity.this,
                                android.R.layout.simple_dropdown_item_1line,
                                cityNames
                        );
                        citySelector.setAdapter(adapter);
                    });
                }
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                Log.e("API", "Failed to fetch cities", t);
            }
        });



        citySelector = findViewById(R.id.citySelector);
        Button confirmButton = findViewById(R.id.btnConfirm);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                cities
        );
        citySelector.setAdapter(adapter);

        confirmButton.setOnClickListener(v -> {
            if (cityNames == null || cityNames.isEmpty()) {
                Toast.makeText(this, "Cities not loaded yet", Toast.LENGTH_SHORT).show();
                return;
            }

            String selectedCity = citySelector.getText().toString().trim();
            String allCitiesText = String.join(", ", cityNames);

            Toast.makeText(this, allCitiesText, Toast.LENGTH_LONG).show();

            if (!selectedCity.isEmpty() && cityNames.contains(selectedCity)) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("selectedCity", selectedCity);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please select a valid city", Toast.LENGTH_SHORT).show();
            }
        });

        // MAP SETUP
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(mapLibreMap -> {
            this.mapLibreMap = mapLibreMap;

            String styleUrl = "https://api.maptiler.com/maps/streets/style.json?key=" + getString(R.string.maptiler_api_key);

            mapLibreMap.setStyle(styleUrl, style -> {

                // Add a click listener for adding temporary waypoints
                mapLibreMap.addOnMapClickListener(point -> {
                    mapLibreMap.addMarker(new MarkerOptions()
                            .position(point)
                            .title("Waypoint"));
                    return true;
                });

                // Observe cities from the database
                repository.getAllCities().observe(MainActivity.this, cities -> {
                    if (cities != null && !cities.isEmpty()) {
                        for (City city : cities) {
                            MarkerOptions markerOptions = new MarkerOptions()
                                    .position(new LatLng(city.latitude, city.longitude))
                                    .title(city.name);
                            mapLibreMap.addMarker(markerOptions);
                        }
                    }
                });

                // Marker click listener
                mapLibreMap.setOnMarkerClickListener(marker -> {
                    marker.showInfoWindow(mapLibreMap, mapView);
                    return true;
                });

                mapLibreMap.setOnInfoWindowClickListener(marker -> {
                    Toast.makeText(MainActivity.this, "Selected: " + marker.getTitle(), Toast.LENGTH_SHORT).show();
                    return true;
                });
            });
        });



    }

    // Lifecycle passthrough for MapLibre
    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}