package eu.europathway.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import eu.europathway.database.entities.StopTime;

// ============================================
// STOP TIME DAO
// ============================================
@Dao
public interface StopTimeDao {
    @Insert
    void insert(StopTime stopTime);

    @Insert
    void insertAll(List<StopTime> stopTimes);

    // Get all stop times for a specific trip (ordered by sequence)
    @Query("SELECT * FROM stop_times WHERE trip_id = :tripId ORDER BY stop_sequence ASC")
    LiveData<List<StopTime>> getStopTimesForTrip(int tripId);

    // Get upcoming departures from a stop
    @Query("SELECT * FROM stop_times " +
            "WHERE stop_id = :stopId " +
            "AND departure_time >= :currentTime " +
            "ORDER BY departure_time ASC " +
            "LIMIT :limit")
    List<StopTime> getUpcomingDepartures(int stopId, String currentTime, int limit);
}
