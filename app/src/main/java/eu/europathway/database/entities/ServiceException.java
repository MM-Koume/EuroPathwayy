package eu.europathway.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "service_exceptions")
public class ServiceException {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int service_id;
    public String date; // YYYY-MM-DD
    public String exception_type; // ADD or REMOVE
}
