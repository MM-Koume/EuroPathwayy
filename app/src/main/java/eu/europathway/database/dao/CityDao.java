package eu.europathway.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import eu.europathway.database.entities.City;
import java.util.List;

@Dao
public interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(City city);

    @Update
    void update(City city);

    @Delete
    void delete(City city);

    @Query("SELECT * FROM cities")
    LiveData<List<City>> getAllCities();

    @Query("SELECT * FROM cities WHERE city_id = :cityId")
    LiveData<City> getCityById(int cityId);

    @Query("SELECT * FROM cities WHERE name = :name LIMIT 1")
    City getCityByName(String name);
}