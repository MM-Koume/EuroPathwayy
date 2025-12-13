package eu.europathway.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

// ============================================
// STOP TIME ENTITY
// ============================================
@Entity(tableName = "stop_times",
        foreignKeys = {
                @ForeignKey(entity = Trip.class,
                        parentColumns = "trip_id",
                        childColumns = "trip_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Stop.class,
                        parentColumns = "stop_id",
                        childColumns = "stop_id",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("trip_id"), @Index("stop_id")})
public class StopTime {
    @PrimaryKey
    @ColumnInfo(name = "stop_time_id")
    public int stopTimeId;

    @ColumnInfo(name = "trip_id")
    public int tripId;

    @ColumnInfo(name = "stop_id")
    public int stopId;

    @ColumnInfo(name = "arrival_time")
    public String arrivalTime; // Format: "HH:MM:SS"

    @ColumnInfo(name = "departure_time")
    public String departureTime;

    @ColumnInfo(name = "stop_sequence")
    public int stopSequence;

    @ColumnInfo(name = "pickup_type")
    public int pickupType;

    @ColumnInfo(name = "drop_off_type")
    public int dropOffType;
}
