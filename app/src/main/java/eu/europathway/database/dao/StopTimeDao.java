package eu.europathway.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import eu.europathway.database.entities.StopTime;

@Dao
public interface StopTimeDao {

    @Insert
    long insert(StopTime stopTime);

    @Query("SELECT * FROM stop_times WHERE trip_id = :tripId ORDER BY stop_order ASC")
    List<StopTime> getTimesForTrip(int tripId);
}