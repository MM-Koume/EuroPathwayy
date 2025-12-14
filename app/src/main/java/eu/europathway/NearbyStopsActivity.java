package eu.europathway;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import eu.europathway.database.TransitRepository;
import eu.europathway.database.ApiCallback;
import eu.europathway.database.entities.Stop;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NearbyStopsActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST = 100;
    private RecyclerView recyclerView;
    private StopAdapter adapter;
    private TransitRepository repository;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_stops);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Nearby Stops");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        repository = new TransitRepository(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        recyclerView = findViewById(R.id.recyclerViewStops);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StopAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        checkLocationPermission();
    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST);
        } else {
            loadNearbyStops();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadNearbyStops();
            } else {
                Toast.makeText(this, "Location permission required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadNearbyStops() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();

                // Get nearby stops (within 1km radius)
                new Thread(() -> {
                    try {
                        List<Stop> stops = repository.getNearbyStops(lat, lon, 1.0, new ApiCallback<>() {
                            @Override
                            public void onSuccess(List<Stop> data) {
                                // This callback can be used to handle the result on the main thread
                            }

                            @Override
                            public void onError(Throwable error) {
                                // This callback can be used to handle errors on the main thread
                            }
                        });
                        runOnUiThread(() -> {
                            adapter.updateStops(stops);
                            if (stops.isEmpty()) {
                                Toast.makeText(this, "No stops found nearby",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (Exception e) { // <-- MODIFIED LINE
                        e.printStackTrace();
                        runOnUiThread(() ->
                                Toast.makeText(this, "Error loading stops",
                                        Toast.LENGTH_SHORT).show()
                        );
                    }
                }).start();
            } else {
                Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // Adapter for stops
    class StopAdapter extends RecyclerView.Adapter<StopAdapter.StopViewHolder> {

        private List<Stop> stops;

        StopAdapter(List<Stop> stops) {
            this.stops = stops;
        }

        void updateStops(List<Stop> newStops) {
            this.stops = newStops;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public StopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_stop, parent, false);
            return new StopViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StopViewHolder holder, int position) {
            Stop stop = stops.get(position);
            holder.bind(stop);
        }

        @Override
        public int getItemCount() {
            return stops.size();
        }

        class StopViewHolder extends RecyclerView.ViewHolder {
            TextView stopName, stopCode, stopDesc, wheelchair;
            CardView cardView;

            StopViewHolder(@NonNull View itemView) {
                super(itemView);
                stopName = itemView.findViewById(R.id.textStopName);
                stopCode = itemView.findViewById(R.id.textStopCode);
                stopDesc = itemView.findViewById(R.id.textStopDesc);
                wheelchair = itemView.findViewById(R.id.textWheelchair);
                cardView = itemView.findViewById(R.id.cardStop);
            }

            void bind(Stop stop) {
                stopName.setText(stop.stopName);
                stopCode.setText(stop.stopCode != null ? stop.stopCode : "");
                stopDesc.setText(stop.stopDesc != null ? stop.stopDesc : "");

                // Show wheelchair accessibility
                if (stop.wheelchairAccessible == 1) {
                    wheelchair.setVisibility(View.VISIBLE);
                    wheelchair.setText("♿ Accessible");
                } else {
                    wheelchair.setVisibility(View.GONE);
                }
            }
        }
    }
}