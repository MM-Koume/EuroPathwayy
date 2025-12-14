package eu.europathway.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "services")
public class Service {@NonNull
    @PrimaryKey(autoGenerate = true)
    public int service_id;
    public String service_code; // original feed service_id
    public String feed_version;
    public long created_at;
}
