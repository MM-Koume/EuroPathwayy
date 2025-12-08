package eu.europathway.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import eu.europathway.database.entities.City;

@Dao
public interface CityDao {

    @Insert
    long insert(City city);

    @Query("SELECT * FROM cities ORDER BY name ASC")
    List<City> getAllCities();

    @Query("SELECT * FROM cities WHERE city_id = :id")
    City getCityById(int id);
}