package eu.europathway.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import eu.europathway.database.entities.LiveDeparture;

// ============================================
// LIVE DEPARTURE DAO
// ============================================
@Dao
public interface LiveDepartureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LiveDeparture departure);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<LiveDeparture> departures);

    @Query("SELECT * FROM live_departures WHERE stop_id = :stopId " +
            "ORDER BY estimated_time ASC LIMIT :limit")
    LiveData<List<LiveDeparture>> getLiveDeparturesByStop(int stopId, int limit);

    // Clean up old live departure data (older than 1 hour)
    @Query("DELETE FROM live_departures WHERE last_updated < :cutoffTime")
    void deleteOldDepartures(long cutoffTime);
}
