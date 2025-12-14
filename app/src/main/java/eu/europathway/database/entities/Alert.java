package eu.europathway.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alerts")
public class Alert {
    @PrimaryKey(autoGenerate = true)
    public int alert_id;
    public String feed_version;
    public Integer route_id;
    public Integer stop_id;
    public String alert_type;
    public String severity;
    public String title;
    public String description;
    public String start_time;
    public String end_time;
    public long created_at;
}
