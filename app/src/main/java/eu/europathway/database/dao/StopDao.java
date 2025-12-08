package eu.europathway.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import eu.europathway.database.entities.Stop;

@Dao
public interface StopDao {

    @Insert
    long insert(Stop stop);

    @Query("SELECT * FROM stops WHERE city_id = :cityId")
    List<Stop> getStopsForCity(int cityId);

    @Query("SELECT * FROM stops WHERE stop_id = :id")
    Stop getStopById(int id);
}