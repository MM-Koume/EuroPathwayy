package eu.europathway.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "trips",
        foreignKeys = {
                @ForeignKey(entity = Route.class, parentColumns = "route_id", childColumns = "route_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Service.class, parentColumns = "service_id", childColumns = "service_id", onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("route_id"), @Index("service_id")})
public class Trip {
    @PrimaryKey(autoGenerate = true)
    public int trip_id;
    public int route_id;
    public int service_id;
    public String trip_headsign;
    public int direction_id;
    public int wheelchair_accessible;
    public int bikes_allowed;
    public int active;
}
