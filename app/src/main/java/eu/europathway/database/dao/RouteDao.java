
package eu.europathway.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.europathway.database.entities.Route;

// ============================================
// ROUTE DAO
// ============================================
@Dao
public interface RouteDao {
    @Insert
    long insert(Route route);

    @Insert
    void insertAll(List<Route> routes);

    @Update
    void update(Route route);

    @Delete
    void delete(Route route);

    @Query("SELECT * FROM routes WHERE agency_id = :agencyId")
    LiveData<List<Route>> getRoutesByAgency(int agencyId);

    @Query("SELECT * FROM routes WHERE route_id = :routeId")
    LiveData<Route> getRouteById(int routeId);

    @Query("SELECT routes.* FROM routes " +
            "INNER JOIN agencies ON routes.agency_id = agencies.agency_id " +
            "WHERE agencies.city_id = :cityId")
    LiveData<List<Route>> getRoutesByCity(int cityId);
}
