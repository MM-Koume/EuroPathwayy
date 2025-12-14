package eu.europathway.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import eu.europathway.database.entities.ServiceException;

@Dao
public interface ServiceExceptionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ServiceException exception);

    @Query("SELECT * FROM service_exceptions WHERE service_id = :serviceId")
    List<ServiceException> getForService(int serviceId);
}
