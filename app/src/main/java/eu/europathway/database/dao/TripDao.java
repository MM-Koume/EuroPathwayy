
package eu.europathway.database.dao;
import androidx.room.*;
import java.util.List;
import eu.europathway.database.entities.Trip;
@Dao
public interface TripDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Trip trip);

    @Update
    void update(Trip trip);

    @Query("SELECT * FROM trips WHERE route_id = :routeId AND active = 1")
    List<Trip> getForRoute(int routeId);

    @Query("SELECT * FROM trips WHERE trip_id = :id")
    Trip getById(int id);
}


