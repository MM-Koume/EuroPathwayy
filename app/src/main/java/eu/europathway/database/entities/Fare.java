package eu.europathway.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

@Entity(tableName = "fares",
        foreignKeys = @ForeignKey(entity = City.class, parentColumns = "city_id", childColumns = "city_id", onDelete = ForeignKey.CASCADE))
public class Fare {
    @PrimaryKey(autoGenerate = true)
    public int fare_id;
    public int city_id;
    public String fare_name;
    public String fare_type;
    public double price;
    public String currency;
    public Integer valid_minutes;
    public String zones_covered;
}
