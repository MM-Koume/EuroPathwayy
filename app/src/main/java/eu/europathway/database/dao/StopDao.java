package eu.europathway.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.europathway.database.entities.Stop;

@Dao
public interface StopDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Stop stop);

    @Update
    void update(Stop stop);

    @Query("SELECT * FROM stops WHERE city_id = :cityId AND active = 1")
    List<Stop> getActiveStops(int cityId);

    @Query("SELECT * FROM stops WHERE stop_id = :id")
    Stop getById(int id);
}
