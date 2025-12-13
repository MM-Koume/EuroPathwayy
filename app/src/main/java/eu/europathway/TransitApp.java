package eu.europathway;

import android.app.Application;
import android.content.SharedPreferences;
import eu.europathway.database.DatabasePopulator;

/**
 * OPTION 1: Use Application class (RECOMMENDED)
 * Create this class and add it to AndroidManifest.xml
 */
public class TransitApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Check if this is first launch
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        boolean isFirstLaunch = prefs.getBoolean("is_first_launch", true);

        if (isFirstLaunch) {
            // Populate database on first launch
            DatabasePopulator populator = new DatabasePopulator(this);
            populator.populateIfEmpty(this);

            // Mark as not first launch anymore
            prefs.edit().putBoolean("is_first_launch", false).apply();
        }
    }
}