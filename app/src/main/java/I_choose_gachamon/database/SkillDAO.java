package I_choose_gachamon.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import I_choose_gachamon.database.entities.Skill;

@Dao
public interface SkillDAO {
    // Insert a new skill
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Skill... skill);

    // Delete a skill
    @Delete
    void delete(Skill skill);

    @Query("DELETE FROM " + GachamonDatabase.SKILL_TABLE)
    void deleteAll();

    // Get all skills
    @Query("SELECT * FROM " + GachamonDatabase.SKILL_TABLE + " ORDER BY id")
    List<Skill> getAllSkills();

    @Query("SELECT * FROM " + GachamonDatabase.SKILL_TABLE + " WHERE id = :skillId")
    LiveData<Skill> getMonsterSkill(int skillId);
}
