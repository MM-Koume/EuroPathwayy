package eu.europathway.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stops")
public class Stop {
    @PrimaryKey(autoGenerate = true)
    public int stop_id;

    public int city_id;
    public String name;
    public double lat;
    public double lon;
}