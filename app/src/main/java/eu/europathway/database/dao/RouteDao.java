package eu.europathway.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.europathway.database.entities.Route;

@Dao
public interface RouteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Route route);

    @Update
    void update(Route route);

    @Query("SELECT * FROM routes WHERE active = 1")
    List<Route> getActiveRoutes();

    @Query("SELECT * FROM routes WHERE route_id = :id")
    Route getById(int id);
}
