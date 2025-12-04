    package eu.europathway;

    import android.content.Intent;
    import android.os.Bundle;
    import android.widget.ArrayAdapter;
    import android.widget.AutoCompleteTextView;
    import android.widget.Button;
    import android.widget.Toast;

    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;

    import java.util.Arrays;

    public class MainActivity extends AppCompatActivity {

        private AutoCompleteTextView citySelector;
        private final String[] cities = {
                "Berlin", "Paris", "Madrid", "Rome", "Warsaw", "Amsterdam", "Vienna"
        };

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_city_select); // Use your existing XML

            citySelector = findViewById(R.id.citySelector);
            Button confirmButton = findViewById(R.id.btnConfirmCity);

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

        }
    }
