package I_choose_gachamon.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import I_choose_gachamon.database.entities.Monster;

@Dao
public interface MonsterDAO {
    // Insert a new monster into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Monster... monster);

    // Update a monster in the database
    @Update
    void update(Monster monster);

    // Delete a monster from the database
    @Delete
    void delete(Monster monster);

    @Query("DELETE FROM " + GachamonDatabase.MONSTER_TABLE)
    void deleteAll();

    // Get all monsters for a specific user
    @Query("SELECT * FROM " + GachamonDatabase.MONSTER_TABLE +" WHERE userId = :userId")
    List<Monster> getMonstersForUser(int userId);

    // Get a specific monster for a user
    @Query("SELECT * FROM " + GachamonDatabase.MONSTER_TABLE + " WHERE userId = :userId AND name = :name")
    Monster getMonsterForUser(int userId, String name);
}
