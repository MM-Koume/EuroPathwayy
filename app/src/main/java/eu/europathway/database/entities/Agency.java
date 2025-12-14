package eu.europathway.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "agencies")
public class Agency {
    @PrimaryKey(autoGenerate = true)
    public int agency_id;
    public int city_id;
    public String name;
    public String short_name;
    public String url;
    public String phone;
    public String email;
    public int active; // 1 or 0
}
