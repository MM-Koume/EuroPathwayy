package eu.europathway.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Index;
import androidx.room.ForeignKey;

@Entity(tableName = "stops",
        indices = {@Index("city_id"), @Index({"latitude","longitude"} )},
        foreignKeys = @ForeignKey(entity = City.class,
                parentColumns = "city_id",
                childColumns = "city_id",
                onDelete = ForeignKey.CASCADE))
public class Stop {@NonNull
    @PrimaryKey(autoGenerate = true)
    public int stop_id;
    public int city_id;
    public String stop_code;
    public String stop_name;
    public String stop_desc;
    public double latitude;
    public double longitude;
    public String zone_id;
    public int wheelchair_accessible;
    public String platform_code;
    public Integer parent_station; // FK to stops.stop_id
    public int active; // 1/0
}
