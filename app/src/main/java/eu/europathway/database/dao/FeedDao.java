package eu.europathway.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import eu.europathway.database.entities.Feed;

@Dao
public interface FeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Feed feed);

    @Query("SELECT * FROM feeds WHERE city_id = :cityId ORDER BY imported_at DESC")
    List<Feed> getFeedsForCity(int cityId);

    @Query("SELECT * FROM feeds WHERE feed_version = :version LIMIT 1")
    Feed getByVersion(String version);
}
