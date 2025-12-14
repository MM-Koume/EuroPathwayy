package eu.europathway.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "live_departures")
public class LiveDeparture {@NonNull
    @PrimaryKey(autoGenerate = true)
    public int departure_id;
    public int trip_id;
    public int stop_id;
    public String scheduled_time; // HH:MM:SS
    public String estimated_time;
    public Integer delay_minutes;
    public String status; // ON_TIME, DELAYED, CANCELLED
    public long last_updated;
}
