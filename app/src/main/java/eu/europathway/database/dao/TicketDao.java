package eu.europathway.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import eu.europathway.database.entities.Ticket;

@Dao
public interface TicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Ticket ticket);

    @Query("SELECT * FROM tickets WHERE user_id = :userId")
    List<Ticket> getForUser(int userId);
}
