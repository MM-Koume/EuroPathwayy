package eu.europathway.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import eu.europathway.database.dao.*;
import eu.europathway.database.entities.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {
                City.class,
                Agency.class,
                Route.class,
                Stop.class,
                Trip.class,
                StopTime.class,
                LiveDeparture.class,
                Alert.class,
                Fare.class
        },
        version = 1,
        exportSchema = false
)
public abstract class TransitDatabase extends RoomDatabase {

    // DAOs
    public abstract CityDao cityDao();
    public abstract StopDao stopDao();
    public abstract RouteDao routeDao();
    public abstract TripDao tripDao();
    public abstract StopTimeDao stopTimeDao();
    public abstract LiveDepartureDao liveDepartureDao();
    public abstract AlertDao alertDao();
    public abstract FareDao fareDao();

    // Singleton instance
    private static volatile TransitDatabase INSTANCE;

    // Thread pool for background operations
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Get database instance (Singleton pattern)
     */
    public static TransitDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TransitDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    TransitDatabase.class,
                                    "transit_database"
                            )
                            // Enable this for debugging only - shows queries in logcat
                            // .setQueryCallback((sqlQuery, bindArgs) -> {
                            //     Log.d("RoomQuery", "Query: " + sqlQuery);
                            // }, Executors.newSingleThreadExecutor())

                            // Uncomment to allow queries on main thread (NOT RECOMMENDED for production)
                            // .allowMainThreadQueries()

                            // Add migrations if you change schema
                            // .addMigrations(MIGRATION_1_2)

                            // Fallback to destructive migration during development
                            .fallbackToDestructiveMigration()

                            // Add callback for initial data population
                            .addCallback(roomDatabaseCallback)

                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Callback to populate database with initial data
     */
    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                // Populate with initial data
                TransitDatabase database = TransitDatabase.getDatabase(null);
                populateInitialData(database);
            });
        }
    };

    /**
     * Populate database with sample data for testing
     */
    private static void populateInitialData(TransitDatabase db) {
        CityDao cityDao = db.cityDao();

        // Add sample cities (you'll replace this with real data)
        City berlin = new City("Berlin", "Germany", "Europe/Berlin",
                52.5200, 13.4050, "EUR");
        City paris = new City("Paris", "France", "Europe/Paris",
                48.8566, 2.3522, "EUR");
        City warsaw = new City("Warsaw", "Poland", "Europe/Warsaw",
                52.2297, 21.0122, "PLN");

        cityDao.insert(berlin);
        cityDao.insert(paris);
        cityDao.insert(warsaw);
    }

    /**
     * Example migration (if you update schema in future versions)
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Example: Add a new column
            // database.execSQL("ALTER TABLE stops ADD COLUMN stop_url TEXT");
        }
    };

    /**
     * Close database and clear instance (useful for testing)
     */
    public static void closeDatabase() {
        if (INSTANCE != null) {
            INSTANCE.close();
            INSTANCE = null;
        }
    }
}