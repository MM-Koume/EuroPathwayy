package eu.europathway.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.europathway.database.entities.Agency;

@Dao
public interface AgencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Agency agency);

    @Update
    void update(Agency agency);

    @Query("SELECT * FROM agencies WHERE city_id = :cityId AND active = 1")
    List<Agency> getActiveForCity(int cityId);
}
