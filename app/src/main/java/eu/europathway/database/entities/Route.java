package eu.europathway.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "routes",
        foreignKeys = @ForeignKey(entity = Agency.class,
                parentColumns = "agency_id",
                childColumns = "agency_id",
                onDelete = ForeignKey.SET_NULL),
        indices = {@Index("agency_id")})
public class Route {
    @PrimaryKey(autoGenerate = true)
    public int route_id;
    public Integer agency_id; // may be null
    public String route_short_name;
    public String route_long_name;
    public int route_type;
    public String route_color;
    public String route_text_color;
    public int active;
}
