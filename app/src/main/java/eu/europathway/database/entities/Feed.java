package eu.europathway.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "feeds")
public class Feed {
    @PrimaryKey
    @NonNull
    public String feed_version;

    public int city_id;
    public String feed_format;
    public String source_filename;
    public String imported_by;
    public long imported_at;
    public String notes;

    public Feed() {}
}
