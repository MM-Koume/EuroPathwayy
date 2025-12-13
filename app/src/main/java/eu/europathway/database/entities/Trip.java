package eu.europathway.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.ColumnInfo;

// ============================================
// TRIP ENTITY
// ============================================
@Entity(tableName = "trips",
        foreignKeys = {
                @ForeignKey(entity = Route.class,
                        parentColumns = "route_id",
                        childColumns = "route_id",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("route_id")})
public class Trip {
    @PrimaryKey
    @ColumnInfo(name = "trip_id")
    public int tripId;

    @ColumnInfo(name = "route_id")
    public int routeId;

    @ColumnInfo(name = "service_id")
    public int serviceId;

    @ColumnInfo(name = "trip_headsign")
    public String tripHeadsign;

    @ColumnInfo(name = "direction_id")
    public int directionId;

    @ColumnInfo(name = "wheelchair_accessible")
    public int wheelchairAccessible;

    @ColumnInfo(name = "bikes_allowed")
    public int bikesAllowed;
}

