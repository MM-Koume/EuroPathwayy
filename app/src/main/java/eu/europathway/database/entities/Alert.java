package eu.europathway.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

// ============================================
// ALERT ENTITY
// ============================================
@Entity(tableName = "alerts",
        indices = {@Index("route_id"), @Index("stop_id"), @Index({"start_time", "end_time"})})
public class Alert {
    @PrimaryKey
    @ColumnInfo(name = "alert_id")
    public int alertId;

    @ColumnInfo(name = "route_id")
    public Integer routeId;

    @ColumnInfo(name = "stop_id")
    public Integer stopId;

    @ColumnInfo(name = "alert_type")
    public String alertType; // 'DELAY', 'CANCELLATION', 'DETOUR', 'MAINTENANCE'

    public String severity; // 'INFO', 'WARNING', 'CRITICAL'
    public String title;
    public String description;

    @ColumnInfo(name = "start_time")
    public Long startTime;

    @ColumnInfo(name = "end_time")
    public Long endTime;

    @ColumnInfo(name = "created_at")
    public long createdAt;
}
