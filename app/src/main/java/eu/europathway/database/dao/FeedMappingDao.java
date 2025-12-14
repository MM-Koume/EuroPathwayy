package eu.europathway.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import eu.europathway.database.entities.FeedRoute;
import eu.europathway.database.entities.FeedService;
import eu.europathway.database.entities.FeedStop;
import eu.europathway.database.entities.FeedTrip;

@Dao
public interface FeedMappingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStop(FeedStop fs);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRoute(FeedRoute fr);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrip(FeedTrip ft);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertService(FeedService fs);

    @Query("SELECT stop_id FROM feed_stops WHERE feed_stop_id = :fid AND city_id = :cityId LIMIT 1")
    Integer findStop(String fid, int cityId);

    @Query("SELECT route_id FROM feed_routes WHERE feed_route_id = :fid AND city_id = :cityId LIMIT 1")
    Integer findRoute(String fid, int cityId);

    @Query("SELECT trip_id FROM feed_trips WHERE feed_trip_id = :fid AND city_id = :cityId LIMIT 1")
    Integer findTrip(String fid, int cityId);

    @Query("SELECT service_id FROM feed_services WHERE feed_service_id = :fid AND city_id = :cityId LIMIT 1")
    Integer findService(String fid, int cityId);
}
