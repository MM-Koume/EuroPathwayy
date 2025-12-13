package eu.europathway.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.europathway.database.entities.Stop;

// ============================================
// STOP DAO
// ============================================
@Dao
public interface StopDao {
    @Insert
    long insert(Stop stop);

    @Insert
    void insertAll(List<Stop> stops);

    @Update
    void update(Stop stop);

    @Delete
    void delete(Stop stop);

    @Query("SELECT * FROM stops WHERE city_id = :cityId")
    LiveData<List<Stop>> getStopsByCity(int cityId);

    @Query("SELECT * FROM stops WHERE stop_id = :stopId")
    LiveData<Stop> getStopById(int stopId);

    // Find nearby stops using bounding box (for efficiency)
    @Query("SELECT * FROM stops WHERE " +
            "latitude BETWEEN :minLat AND :maxLat AND " +
            "longitude BETWEEN :minLon AND :maxLon")
    List<Stop> getNearbyStops(double minLat, double maxLat, double minLon, double maxLon);

    @Query("SELECT * FROM stops WHERE stop_name LIKE '%' || :query || '%' AND city_id = :cityId")
    LiveData<List<Stop>> searchStops(String query, int cityId);
}
