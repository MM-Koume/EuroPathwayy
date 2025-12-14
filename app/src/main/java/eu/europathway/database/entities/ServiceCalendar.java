package eu.europathway.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "service_calendar")
public class ServiceCalendar {@NonNull
    @PrimaryKey
    public int service_id;
    public int monday;
    public int tuesday;
    public int wednesday;
    public int thursday;
    public int friday;
    public int saturday;
    public int sunday;
    public String start_date; // YYYY-MM-DD
    public String end_date;
}
