
package eu.europathway.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import eu.europathway.database.entities.LiveDeparture;

@Dao
public interface LiveDepartureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LiveDeparture dep);

    @Query("SELECT * FROM live_departures WHERE stop_id = :stopId ORDER BY scheduled_time")
    List<LiveDeparture> getForStop(int stopId);
}
