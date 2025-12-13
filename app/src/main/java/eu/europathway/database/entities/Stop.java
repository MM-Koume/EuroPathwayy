package eu.europathway.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

// ============================================
// STOP ENTITY
// ============================================
@Entity(tableName = "stops",
        foreignKeys = @ForeignKey(
                entity = City.class,
                parentColumns = "city_id",
                childColumns = "city_id",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("city_id"), @Index({"latitude", "longitude"})})
public class Stop {
    @PrimaryKey
    @ColumnInfo(name = "stop_id")
    public int stopId;

    @ColumnInfo(name = "city_id")
    public int cityId;

    @ColumnInfo(name = "stop_code")
    public String stopCode;

    @ColumnInfo(name = "stop_name")
    public String stopName;

    @ColumnInfo(name = "stop_desc")
    public String stopDesc;

    public double latitude;
    public double longitude;

    @ColumnInfo(name = "zone_id")
    public String zoneId;

    @ColumnInfo(name = "wheelchair_accessible")
    public int wheelchairAccessible;

    @ColumnInfo(name = "platform_code")
    public String platformCode;

    @ColumnInfo(name = "parent_station")
    public Integer parentStation;
}
