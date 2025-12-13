package eu.europathway.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

// ============================================
// AGENCY ENTITY
// ============================================
@Entity(tableName = "agencies",
        foreignKeys = @ForeignKey(
                entity = City.class,
                parentColumns = "city_id",
                childColumns = "city_id",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("city_id")})
public class Agency {
    @PrimaryKey
    @ColumnInfo(name = "agency_id")
    public int agencyId;

    @ColumnInfo(name = "city_id")
    public int cityId;

    public String name;

    @ColumnInfo(name = "short_name")
    public String shortName;

    public String website;
    public String phone;
    public String email;
}
