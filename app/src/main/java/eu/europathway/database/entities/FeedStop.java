package eu.europathway.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "feed_stops",
        primaryKeys = {"feed_stop_id","city_id"},
        foreignKeys = {
                @ForeignKey(entity = Stop.class, parentColumns = "stop_id", childColumns = "stop_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Feed.class, parentColumns = "feed_version", childColumns = "feed_version", onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("stop_id")})
public class FeedStop {

    @NonNull
    public String feed_stop_id;
    public int city_id;
    public int stop_id;
    public String feed_version;

}
