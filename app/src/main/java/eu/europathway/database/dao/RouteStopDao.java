package eu.europathway.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import eu.europathway.database.entities.RouteStop;

@Dao
public interface RouteStopDao {

    @Insert
    long insert(RouteStop routeStop);

    @Query("SELECT * FROM route_stops WHERE route_id = :routeId ORDER BY stop_order ASC")
    List<RouteStop> getStopsForRoute(int routeId);
}