package eu.europathway.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

// ============================================
// LIVE DEPARTURE ENTITY (Real-time)
// ============================================
@Entity(tableName = "live_departures",
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
        indices = {@Index("trip_id"), @Index("stop_id"), @Index("last_updated")})
public class LiveDeparture {
    @PrimaryKey
    @ColumnInfo(name = "departure_id")
    public int departureId;

    @ColumnInfo(name = "trip_id")
    public int tripId;

    @ColumnInfo(name = "stop_id")
    public int stopId;

    @ColumnInfo(name = "scheduled_time")
    public String scheduledTime;

    @ColumnInfo(name = "estimated_time")
    public String estimatedTime;

    @ColumnInfo(name = "delay_minutes")
    public int delayMinutes;

    public String status; // 'ON_TIME', 'DELAYED', 'CANCELLED'

    @ColumnInfo(name = "last_updated")
    public long lastUpdated;
}
