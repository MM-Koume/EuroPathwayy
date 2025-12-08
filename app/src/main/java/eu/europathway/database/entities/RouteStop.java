package eu.europathway.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "route_stops")
public class RouteStop {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int route_id;
    public int stop_id;
    public int stop_order;
}