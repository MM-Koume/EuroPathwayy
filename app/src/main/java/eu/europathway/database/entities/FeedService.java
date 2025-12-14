package eu.europathway.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "feed_services",
        primaryKeys = {"feed_service_id","city_id"},
        foreignKeys = {
                @ForeignKey(entity = Service.class, parentColumns = "service_id", childColumns = "service_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Feed.class, parentColumns = "feed_version", childColumns = "feed_version", onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("service_id")})
public class FeedService {
    @NonNull
    public String feed_service_id;
    public int city_id;
    public int service_id;
    public String feed_version;
}
