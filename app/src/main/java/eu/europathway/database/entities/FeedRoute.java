package eu.europathway.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "feed_routes",
        primaryKeys = {"feed_route_id","city_id"},
        foreignKeys = {
                @ForeignKey(entity = Route.class, parentColumns = "route_id", childColumns = "route_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Feed.class, parentColumns = "feed_version", childColumns = "feed_version", onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("route_id")})
public class FeedRoute {
    public String feed_route_id;
    public int city_id;
    public int route_id;
    public String feed_version;
}
