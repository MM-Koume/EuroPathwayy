package eu.europathway;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import eu.europathway.database.TransitRepository;
import eu.europathway.database.entities.Route;
import java.util.ArrayList;
import java.util.List;

public class RoutesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RouteAdapter adapter;
    private TransitRepository repository;
    private int cityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        // Get city ID from intent
        cityId = getIntent().getIntExtra("cityId", 1);
        String cityName = getIntent().getStringExtra("cityName");

        // Set title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Routes in " + cityName);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize repository
        repository = new TransitRepository(this);

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerViewRoutes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RouteAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Load routes
        loadRoutes();
    }

    private void loadRoutes() {
        repository.getRoutesByCity(cityId).observe(this, routes -> {
            if (routes != null) {
                adapter.updateRoutes(routes);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // Adapter for routes
    class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.RouteViewHolder> {

        private List<Route> routes;

        RouteAdapter(List<Route> routes) {
            this.routes = routes;
        }

        void updateRoutes(List<Route> newRoutes) {
            this.routes = newRoutes;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_route, parent, false);
            return new RouteViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RouteViewHolder holder, int position) {
            Route route = routes.get(position);
            holder.bind(route);
        }

        @Override
        public int getItemCount() {
            return routes.size();
        }

        class RouteViewHolder extends RecyclerView.ViewHolder {
            TextView routeName, routeType, routeLongName;
            View colorIndicator;
            CardView cardView;

            RouteViewHolder(@NonNull View itemView) {
                super(itemView);
                routeName = itemView.findViewById(R.id.textRouteName);
                routeType = itemView.findViewById(R.id.textRouteType);
                routeLongName = itemView.findViewById(R.id.textRouteLongName);
                colorIndicator = itemView.findViewById(R.id.colorIndicator);
                cardView = itemView.findViewById(R.id.cardRoute);
            }

            void bind(Route route) {
                routeName.setText(route.routeShortName);
                routeLongName.setText(route.routeLongName);

                // Set route type text
                String type = getRouteTypeText(route.routeType);
                routeType.setText(type);

                // Set route color
                try {
                    if (route.routeColor != null && !route.routeColor.isEmpty()) {
                        int color = Color.parseColor(route.routeColor);
                        colorIndicator.setBackgroundColor(color);
                    }
                } catch (Exception e) {
                    // Use default color if parsing fails
                }
            }

            String getRouteTypeText(int type) {
                switch (type) {
                    case 0: return "Tram";
                    case 1: return "Subway";
                    case 2: return "Rail";
                    case 3: return "Bus";
                    case 4: return "Ferry";
                    default: return "Transit";
                }
            }
        }
    }
}