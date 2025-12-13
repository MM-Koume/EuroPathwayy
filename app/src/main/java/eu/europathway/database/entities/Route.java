package eu.europathway.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

// ============================================
// ROUTE ENTITY
// ============================================
@Entity(tableName = "routes",
        foreignKeys = @ForeignKey(
                entity = Agency.class,
                parentColumns = "agency_id",
                childColumns = "agency_id",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("agency_id")})
public class Route {
    @PrimaryKey
    @ColumnInfo(name = "route_id")
    public int routeId;

    @ColumnInfo(name = "agency_id")
    public int agencyId;

    @ColumnInfo(name = "route_short_name")
    public String routeShortName;

    @ColumnInfo(name = "route_long_name")
    public String routeLongName;

    @ColumnInfo(name = "route_type")
    public int routeType; // 0=tram, 1=subway, 2=rail, 3=bus, 4=ferry

    @ColumnInfo(name = "route_color")
    public String routeColor;

    @ColumnInfo(name = "route_text_color")
    public String routeTextColor;
}
