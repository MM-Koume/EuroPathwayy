package eu.europathway.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cities")
public class City {
    @PrimaryKey(autoGenerate = true)
    public int city_id;

    public String name;
    public double lat;
    public double lon;
}