package eu.europathway.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "feed_trips",
        primaryKeys = {"feed_trip_id","city_id"},
        foreignKeys = {
                @ForeignKey(entity = Trip.class, parentColumns = "trip_id", childColumns = "trip_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Feed.class, parentColumns = "feed_version", childColumns = "feed_version", onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("trip_id")})
public class FeedTrip {@NonNull
    public String feed_trip_id;
    public int city_id;
    public int trip_id;
    public String feed_version;
}
