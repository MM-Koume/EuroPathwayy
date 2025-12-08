package eu.europathway.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import eu.europathway.database.entities.*;
import eu.europathway.database.dao.*;

@Database(
        entities = {
                City.class,
                Route.class,
                Stop.class,
                RouteStop.class,
                Trip.class,
                StopTime.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract CityDao cityDao();
    public abstract RouteDao routeDao();
    public abstract StopDao stopDao();
    public abstract RouteStopDao routeStopDao();
    public abstract TripDao tripDao();
    public abstract StopTimeDao stopTimeDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "euro_pathway.db"
                    ).fallbackToDestructiveMigration()
                     .build();
                }
            }
        }
        return INSTANCE;
    }
}