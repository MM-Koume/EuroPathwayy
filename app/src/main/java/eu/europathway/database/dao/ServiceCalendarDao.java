package eu.europathway.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import eu.europathway.database.entities.ServiceCalendar;

@Dao
public interface ServiceCalendarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ServiceCalendar calendar);

    @Query("SELECT * FROM service_calendar WHERE service_id = :serviceId")
    ServiceCalendar getForService(int serviceId);
}
