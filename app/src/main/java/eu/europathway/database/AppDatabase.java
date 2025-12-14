package eu.europathway.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import eu.europathway.database.dao.AgencyDao;
import eu.europathway.database.dao.AlertDao;
import eu.europathway.database.dao.CityDao;
import eu.europathway.database.dao.FareDao;
import eu.europathway.database.dao.FareRuleDao;
import eu.europathway.database.dao.FeedDao;
import eu.europathway.database.dao.FeedMappingDao;
import eu.europathway.database.dao.LiveDepartureDao;
import eu.europathway.database.dao.RouteDao;
import eu.europathway.database.dao.ServiceCalendarDao;
import eu.europathway.database.dao.ServiceDao;
import eu.europathway.database.dao.ServiceExceptionDao;
import eu.europathway.database.dao.StopDao;
import eu.europathway.database.dao.StopTimeDao;
import eu.europathway.database.dao.TicketDao;
import eu.europathway.database.dao.TripDao;
import eu.europathway.database.entities.Agency;
import eu.europathway.database.entities.Alert;
import eu.europathway.database.entities.City;
import eu.europathway.database.entities.Fare;
import eu.europathway.database.entities.FareRule;
import eu.europathway.database.entities.Feed;
import eu.europathway.database.entities.FeedRoute;
import eu.europathway.database.entities.FeedService;
import eu.europathway.database.entities.FeedStop;
import eu.europathway.database.entities.FeedTrip;
import eu.europathway.database.entities.LiveDeparture;
import eu.europathway.database.entities.Route;
import eu.europathway.database.entities.Service;
import eu.europathway.database.entities.ServiceCalendar;
import eu.europathway.database.entities.ServiceException;
import eu.europathway.database.entities.Stop;
import eu.europathway.database.entities.StopTime;
import eu.europathway.database.entities.Ticket;
import eu.europathway.database.entities.Trip;

@Database(entities = {
        City.class, Feed.class, Service.class, ServiceCalendar.class, ServiceException.class,
        Agency.class, Stop.class, Route.class, Trip.class, StopTime.class,
        FeedStop.class, FeedRoute.class, FeedTrip.class, FeedService.class,
        Fare.class, FareRule.class, Ticket.class, LiveDeparture.class, Alert.class
}, version = 2, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "transit_db.db";
    private static volatile AppDatabase INSTANCE;

    public abstract CityDao cityDao();
    public abstract FeedDao feedDao();
    public abstract StopDao stopDao();
    public abstract RouteDao routeDao();
    public abstract TripDao tripDao();
    public abstract StopTimeDao stopTimeDao();
    public abstract FeedMappingDao feedMappingDao();
    public abstract FareDao fareDao();
    public abstract TicketDao ticketDao();
    public abstract AgencyDao agencyDao();
    public abstract ServiceDao serviceDao();
    public abstract ServiceCalendarDao serviceCalendarDao();
    public abstract ServiceExceptionDao serviceExceptionDao();
    public abstract LiveDepartureDao liveDepartureDao();
    public abstract AlertDao alertDao();
    public abstract FareRuleDao fareRuleDao();
    


    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    // Add migrations as needed
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, DB_NAME)
                            .addMigrations(MIGRATION_1_2) // example migration hook
                            .fallbackToDestructiveMigrationOnDowngrade() // optional
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Example migration: v1 -> v2 add new tables/columns (adjust SQL to your previous schema)
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase db) {
            // Create feeds table
            db.execSQL("CREATE TABLE IF NOT EXISTS `feeds` (`feed_version` TEXT NOT NULL PRIMARY KEY, `city_id` INTEGER NOT NULL, `feed_format` TEXT NOT NULL, `source_filename` TEXT, `imported_by` TEXT, `imported_at` INTEGER NOT NULL DEFAULT (strftime('%s','now')), `notes` TEXT)");
            // services
            db.execSQL("CREATE TABLE IF NOT EXISTS `services` (`service_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `service_code` TEXT, `feed_version` TEXT, `created_at` INTEGER)");
            // service_calendar
            db.execSQL("CREATE TABLE IF NOT EXISTS `service_calendar` (`service_id` INTEGER NOT NULL PRIMARY KEY, `monday` INTEGER NOT NULL, `tuesday` INTEGER NOT NULL, `wednesday` INTEGER NOT NULL, `thursday` INTEGER NOT NULL, `friday` INTEGER NOT NULL, `saturday` INTEGER NOT NULL, `sunday` INTEGER NOT NULL, `start_date` TEXT, `end_date` TEXT)");
            // service_exceptions
            db.execSQL("CREATE TABLE IF NOT EXISTS `service_exceptions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `service_id` INTEGER NOT NULL, `date` TEXT NOT NULL, `exception_type` TEXT NOT NULL)");
            // feed mapping tables
            db.execSQL("CREATE TABLE IF NOT EXISTS `feed_stops` (`feed_stop_id` TEXT NOT NULL, `city_id` INTEGER NOT NULL, `stop_id` INTEGER NOT NULL, `feed_version` TEXT NOT NULL, PRIMARY KEY(`feed_stop_id`, `city_id`))");
            db.execSQL("CREATE TABLE IF NOT EXISTS `feed_routes` (`feed_route_id` TEXT NOT NULL, `city_id` INTEGER NOT NULL, `route_id` INTEGER NOT NULL, `feed_version` TEXT NOT NULL, PRIMARY KEY(`feed_route_id`, `city_id`))");
            db.execSQL("CREATE TABLE IF NOT EXISTS `feed_trips` (`feed_trip_id` TEXT NOT NULL, `city_id` INTEGER NOT NULL, `trip_id` INTEGER NOT NULL, `feed_version` TEXT NOT NULL, PRIMARY KEY(`feed_trip_id`, `city_id`))");
            db.execSQL("CREATE TABLE IF NOT EXISTS `feed_services` (`feed_service_id` TEXT NOT NULL, `city_id` INTEGER NOT NULL, `service_id` INTEGER NOT NULL, `feed_version` TEXT NOT NULL, PRIMARY KEY(`feed_service_id`, `city_id`))");

            // fares & fare_rules
            db.execSQL("CREATE TABLE IF NOT EXISTS `fares` (`fare_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `city_id` INTEGER NOT NULL, `fare_name` TEXT NOT NULL, `fare_type` TEXT NOT NULL, `price` REAL NOT NULL, `currency` TEXT NOT NULL, `valid_minutes` INTEGER, `zones_covered` TEXT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS `fare_rules` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `fare_id` INTEGER NOT NULL, `route_id` INTEGER, `origin_zone` TEXT, `destination_zone` TEXT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS `tickets` (`ticket_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `fare_id` INTEGER NOT NULL, `user_id` INTEGER, `issued_at` INTEGER NOT NULL DEFAULT (strftime('%s','now')), `valid_from` TEXT, `valid_to` TEXT, `status` TEXT, `metadata` TEXT)");

            // live_departures and alerts
            db.execSQL("CREATE TABLE IF NOT EXISTS `live_departures` (`departure_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `trip_id` INTEGER NOT NULL, `stop_id` INTEGER NOT NULL, `scheduled_time` TEXT NOT NULL, `estimated_time` TEXT, `delay_minutes` INTEGER DEFAULT 0, `status` TEXT, `last_updated` INTEGER)");
            db.execSQL("CREATE TABLE IF NOT EXISTS `alerts` (`alert_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `feed_version` TEXT, `route_id` INTEGER, `stop_id` INTEGER, `alert_type` TEXT NOT NULL, `severity` TEXT NOT NULL, `title` TEXT, `description` TEXT, `start_time` TEXT, `end_time` TEXT, `created_at` INTEGER NOT NULL DEFAULT (strftime('%s','now')))");
            // ensure new columns exist in old tables if necessary (example)
            try {
                db.execSQL("ALTER TABLE stops ADD COLUMN active INTEGER NOT NULL DEFAULT 1");
            } catch (Exception ignored) {}
            try {
                db.execSQL("ALTER TABLE routes ADD COLUMN active INTEGER NOT NULL DEFAULT 1");
            } catch (Exception ignored) {}
            try {
                db.execSQL("ALTER TABLE trips ADD COLUMN active INTEGER NOT NULL DEFAULT 1");
            } catch (Exception ignored) {}
        }
    };
}
