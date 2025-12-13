package eu.europathway.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

// ============================================
// FARE ENTITY
// ============================================
@Entity(tableName = "fares",
        foreignKeys = @ForeignKey(
                entity = City.class,
                parentColumns = "city_id",
                childColumns = "city_id",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("city_id")})
public class Fare {
    @PrimaryKey
    @ColumnInfo(name = "fare_id")
    public int fareId;

    @ColumnInfo(name = "city_id")
    public int cityId;

    @ColumnInfo(name = "fare_name")
    public String fareName;

    @ColumnInfo(name = "fare_type")
    public String fareType; // 'single', 'day', 'week', 'month'

    public double price;
    public String currency;

    @ColumnInfo(name = "valid_duration")
    public Integer validDuration; // Minutes

    @ColumnInfo(name = "zones_covered")
    public String zonesCovered;
}
