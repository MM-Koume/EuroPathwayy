package eu.europathway.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "stop_times",
        foreignKeys = {
                @ForeignKey(entity = Trip.class, parentColumns = "trip_id", childColumns = "trip_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Stop.class, parentColumns = "stop_id", childColumns = "stop_id", onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("trip_id"), @Index("stop_id")})
public class StopTime {@NonNull
    @PrimaryKey(autoGenerate = true)
    public int stop_time_id;
    public int trip_id;
    public int stop_id;
    public String arrival_time; // "HH:MM:SS"
    public String departure_time;
    public int stop_sequence;
    public int pickup_type;
    public int drop_off_type;
}
