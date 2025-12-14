package eu.europathway.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.europathway.database.entities.City;

@Dao
public interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(City city);

    @Update
    void update(City city);

    @Delete
    void delete(City city);

    @Query("SELECT * FROM cities ORDER BY name")
    List<City> getAll();

    @Query("SELECT * FROM cities WHERE city_id = :id LIMIT 1")
    City getById(int id);
}
