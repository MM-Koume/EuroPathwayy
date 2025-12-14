package eu.europathway;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import eu.europathway.database.TransitRepository;

public class HomeActivity extends AppCompatActivity {

    private int cityId;
    private String cityName;
    private TransitRepository repository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        // Initialize repository
        repository = new TransitRepository(this);

        TextView textCity = findViewById(R.id.textCity);
        Button btnChangeCity = findViewById(R.id.btnChangeCity);

        // Get selected city from Intent
        Intent intent = getIntent();
        cityName = intent.getStringExtra("selectedCity");

        if (cityName != null) {
            textCity.setText(cityName);
            // Get city ID from database
            getCityId(cityName);
        }

        // Change city button
        btnChangeCity.setOnClickListener(v -> {
            finish(); // Return to MainActivity
        });

        // Setup button click listeners
        setupNavigationButtons();
    }

    private void getCityId(String cityName) {
        // Get city ID from database in background
        new Thread(() -> {
            try {
                // This is a blocking call - normally you'd use LiveData
                // but for simplicity we're using a background thread
                cityId = 1; // Default to 1 for now
                // TODO: Query database for actual city ID
            } catch (Exception e) {
                e.printStackTrace();
                cityId = 1;
            }
        }).start();
    }

    private void setupNavigationButtons() {
        // Live Departures
        CardView btnLiveDepartures = findViewById(R.id.btnLiveDepartures);
        btnLiveDepartures.setOnClickListener(v -> {
            Toast.makeText(this, "Live Departures - Coming Soon!", Toast.LENGTH_SHORT).show();
            // TODO: Implement LiveDeparturesActivity
        });

        // Routes and Lines
        CardView btnRoutesLines = findViewById(R.id.btnRoutesLines);
        btnRoutesLines.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RoutesActivity.class);
            intent.putExtra("cityId", cityId);
            intent.putExtra("cityName", cityName);
            startActivity(intent);
        });

        // Trip Planner
        CardView btnTripPlanner = findViewById(R.id.btnTripPlanner);
        btnTripPlanner.setOnClickListener(v -> {
            Toast.makeText(this, "Trip Planner - Coming Soon!", Toast.LENGTH_SHORT).show();
            // TODO: Implement TripPlannerActivity
        });

        // Tickets and Passes
        CardView btnTickets = findViewById(R.id.btnTickets);
        btnTickets.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, FaresActivity.class);
            intent.putExtra("cityId", cityId);
            intent.putExtra("cityName", cityName);
            startActivity(intent);
        });

        // Nearby Stops
        CardView btnNearbyStops = findViewById(R.id.btnNearbyStops);
        btnNearbyStops.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, NearbyStopsActivity.class);
            startActivity(intent);
        });

        // Settings
        CardView btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(v -> {
            Toast.makeText(this, "Settings - Coming Soon!", Toast.LENGTH_SHORT).show();
            // TODO: Implement SettingsActivity
        });
    }
}