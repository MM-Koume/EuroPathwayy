package eu.europathway.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import eu.europathway.database.entities.Alert;

// ============================================
// ALERT DAO
// ============================================
@Dao
public interface AlertDao {
    @Insert
    void insert(Alert alert);

    @Insert
    void insertAll(List<Alert> alerts);

    @Query("SELECT * FROM alerts WHERE " +
            "(route_id = :routeId OR route_id IS NULL) AND " +
            "(stop_id = :stopId OR stop_id IS NULL) AND " +
            "(end_time IS NULL OR end_time > :currentTime) " +
            "ORDER BY severity DESC, created_at DESC")
    LiveData<List<Alert>> getActiveAlerts(Integer routeId, Integer stopId, long currentTime);

    @Query("SELECT * FROM alerts WHERE end_time IS NULL OR end_time > :currentTime " +
            "ORDER BY created_at DESC")
    LiveData<List<Alert>> getAllActiveAlerts(long currentTime);
}
