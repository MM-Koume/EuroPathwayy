package eu.europathway.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import eu.europathway.database.entities.StopTime;

@Dao
public interface StopTimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StopTime stopTime);

    @Query("SELECT * FROM stop_times WHERE trip_id = :tripId ORDER BY stop_sequence")
    List<StopTime> getForTrip(int tripId);
}
