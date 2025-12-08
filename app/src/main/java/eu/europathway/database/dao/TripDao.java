package eu.europathway.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import eu.europathway.database.entities.Trip;

@Dao
public interface TripDao {

    @Insert
    long insert(Trip trip);

    @Query("SELECT * FROM trips WHERE route_id = :routeId")
    List<Trip> getTripsForRoute(int routeId);
}