package eu.europathway;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView citySelector;
    private final String[] cities = {
            "Berlin", "Paris", "Madrid", "Rome", "Warsaw", "Amsterdam", "Vienna"
    };

    private MapView mapView;
    private MapboxMap mapboxMap;
    private TransitRepository repo;
    private List<String> cityNames = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Mapbox (use your token)
        Mapbox.getInstance(this, "YOUR_MAPBOX_API_KEY_HERE");

        setContentView(R.layout.fragment_city_select_new);
repo = new TransitRepository(this);

        // UI
        citySelector = findViewById(R.id.citySelector);
        Button confirmButton = findViewById(R.id.btnConfirm);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                cities
        );
        citySelector.setAdapter(adapter);

        confirmButton.setOnClickListener(v -> {
            String selectedCity = citySelector.getText().toString().trim();
            if (!selectedCity.isEmpty() && Arrays.asList(cities).contains(selectedCity)) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("selectedCity", selectedCity);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please select a valid city", Toast.LENGTH_SHORT).show();
            }
        });

        // MAP STUFF
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMapInstance) {
                mapboxMap = mapboxMapInstance;

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        // Allow placing marker by tapping
                        mapboxMap.addOnMapClickListener(point -> {
                            mapboxMap.addMarker(new MarkerOptions()
                                    .position(point)
                                    .title("Waypoint"));
                            return true;
                        });
                    }
                });
            }
        });
    }

    // Lifecycle passthrough for Mapbox
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
    protected void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}