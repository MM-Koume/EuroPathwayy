package eu.europathway.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import eu.europathway.database.entities.Route;

@Dao
public interface RouteDao {

    @Insert
    long insert(Route route);

    @Query("SELECT * FROM routes WHERE city_id = :cityId")
    List<Route> getRoutesForCity(int cityId);
}