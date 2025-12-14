package eu.europathway.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import eu.europathway.database.entities.Service;

@Dao
public interface ServiceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Service service);

    @Query("SELECT * FROM services WHERE service_id = :id")
    Service getById(int id);
}
