package eu.europathway.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import eu.europathway.database.entities.*;
import java.util.List;


// ============================================
// TRIP DAO
// ============================================
@Dao
public interface TripDao {
    @Insert
    long insert(Trip trip);

    @Insert
    void insertAll(List<Trip> trips);

    @Query("SELECT * FROM trips WHERE route_id = :routeId")
    LiveData<List<Trip>> getTripsByRoute(int routeId);

    @Query("SELECT * FROM trips WHERE trip_id = :tripId")
    LiveData<Trip> getTripById(int tripId);
}

