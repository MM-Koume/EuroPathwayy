package eu.europathway.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stop_times")
public class StopTime {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int trip_id;
    public int stop_id;
    public String arrival_time;
    public String departure_time;
    public int stop_order;
}