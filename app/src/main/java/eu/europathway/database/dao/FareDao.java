package eu.europathway.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import eu.europathway.database.entities.Fare;
import java.util.List;

@Dao
public interface FareDao {
    @Insert
    void insert(Fare fare);

    @Insert
    void insertAll(List<Fare> fares);

    @Query("SELECT * FROM fares WHERE city_id = :cityId")
    LiveData<List<Fare>> getFaresByCity(int cityId);

    @Query("SELECT * FROM fares WHERE fare_id = :fareId")
    LiveData<Fare> getFareById(int fareId);
}