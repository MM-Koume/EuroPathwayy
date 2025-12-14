package eu.europathway;

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
import eu.europathway.database.entities.Fare;
import java.util.ArrayList;
import java.util.List;

public class FaresActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FareAdapter adapter;
    private TransitRepository repository;
    private int cityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fares);

        cityId = getIntent().getIntExtra("cityId", 1);
        String cityName = getIntent().getStringExtra("cityName");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Tickets & Passes - " + cityName);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        repository = new TransitRepository(this);

        recyclerView = findViewById(R.id.recyclerViewFares);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FareAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        loadFares();
    }

    private void loadFares() {
        repository.getFaresByCity(cityId).observe(this, fares -> {
            if (fares != null) {
                adapter.updateFares(fares);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    class FareAdapter extends RecyclerView.Adapter<FareAdapter.FareViewHolder> {

        private List<Fare> fares;

        FareAdapter(List<Fare> fares) {
            this.fares = fares;
        }

        void updateFares(List<Fare> newFares) {
            this.fares = newFares;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public FareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_fare, parent, false);
            return new FareViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FareViewHolder holder, int position) {
            Fare fare = fares.get(position);
            holder.bind(fare);
        }

        @Override
        public int getItemCount() {
            return fares.size();
        }

        class FareViewHolder extends RecyclerView.ViewHolder {
            TextView fareName, farePrice, fareType, fareValidity;
            CardView cardView;

            FareViewHolder(@NonNull View itemView) {
                super(itemView);
                fareName = itemView.findViewById(R.id.textFareName);
                farePrice = itemView.findViewById(R.id.textFarePrice);
                fareType = itemView.findViewById(R.id.textFareType);
                fareValidity = itemView.findViewById(R.id.textFareValidity);
                cardView = itemView.findViewById(R.id.cardFare);
            }

            void bind(Fare fare) {
                fareName.setText(fare.fareName);
                farePrice.setText(String.format("%.2f %s", fare.price, fare.currency));
                fareType.setText(getFareTypeText(fare.fareType));

                if (fare.validDuration != null && fare.validDuration > 0) {
                    String validity = formatDuration(fare.validDuration);
                    fareValidity.setText("Valid for: " + validity);
                    fareValidity.setVisibility(View.VISIBLE);
                } else {
                    fareValidity.setVisibility(View.GONE);
                }
            }

            String getFareTypeText(String type) {
                switch (type) {
                    case "single": return "🎫 Single Journey";
                    case "day": return "📅 Day Pass";
                    case "week": return "📆 Weekly Pass";
                    case "month": return "🗓️ Monthly Pass";
                    case "annual": return "📋 Annual Pass";
                    default: return "🎫 Ticket";
                }
            }

            String formatDuration(int minutes) {
                if (minutes < 60) {
                    return minutes + " minutes";
                } else if (minutes < 1440) {
                    int hours = minutes / 60;
                    return hours + (hours == 1 ? " hour" : " hours");
                } else {
                    int days = minutes / 1440;
                    return days + (days == 1 ? " day" : " days");
                }
            }
        }
    }
}