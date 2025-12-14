package eu.europathway.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.Index;

@Entity(tableName = "cities", indices = {@Index("name")})
public class City {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int city_id;

    public String name;
    public String country;
    public String timezone;
    public double latitude;
    public double longitude;
    public String currency;
    public long created_at; // store as epoch millis

    public City() {}
}
