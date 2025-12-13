package eu.europathway.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// ============================================
// CITY ENTITY
// ============================================
@Entity(tableName = "cities")
public class City {
    @PrimaryKey
    @ColumnInfo(name = "city_id")
    public int cityId;

    public String name;
    public String country;
    public String timezone;
    public double latitude;
    public double longitude;
    public String currency;

    @ColumnInfo(name = "created_at")
    public long createdAt;

    public City(String name, String country, String timezone,
                double latitude, double longitude, String currency) {
        this.name = name;
        this.country = country;
        this.timezone = timezone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.currency = currency;
        this.createdAt = System.currentTimeMillis();
    }
}
