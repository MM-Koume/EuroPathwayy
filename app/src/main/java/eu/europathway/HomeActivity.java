package eu.europathway;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home); // reuse your XML

        TextView textCity = findViewById(R.id.textCity);
        Button btnChangeCity = findViewById(R.id.btnChangeCity);

        // Get the selected city from Intent
        Intent intent = getIntent();
        String selectedCity = intent.getStringExtra("selectedCity");
        if (selectedCity != null) {
            textCity.setText(selectedCity);
        }

        // Go back to city selector (MainActivity)
        btnChangeCity.setOnClickListener(v -> {
            finish(); // closes HomeActivity and returns to MainActivity
        });

        // TODO: Add click listeners for other buttons
    }
}
