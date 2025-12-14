package eu.europathway.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tickets")
public class Ticket {@NonNull
    @PrimaryKey(autoGenerate = true)
    public int ticket_id;
    public int fare_id;
    public Integer user_id;
    public long issued_at;
    public String valid_from; // ISO datetime
    public String valid_to;
    public String status; // active/used/cancelled/refunded
    public String metadata; // store JSON string
}
