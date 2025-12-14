
package eu.europathway.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import eu.europathway.database.entities.FareRule;

@Dao
public interface FareRuleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FareRule rule);
}
