package eu.europathway.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

@Entity(tableName = "fare_rules")
public class FareRule {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;
    public int fare_id;
    public Integer route_id;
    public String origin_zone;
    public String destination_zone;
}
