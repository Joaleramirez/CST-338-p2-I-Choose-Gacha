package I_choose_gachamon.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import I_choose_gachamon.database.entities.Team;

@Dao
public interface TeamDAO {
    @Insert
    void insert(Team team);

    @Update
    void update(Team team);

    @Delete
    void delete(Team team);

    @Query("DELETE FROM " + GachamonDatabase.TEAM_TABLE + " WHERE userId = :userId")
    void deleteTeamFromUser(int userId);

    @Query("SELECT * FROM team_table WHERE userId = :userId LIMIT 1")
    LiveData<Team> getTeamByUserId(int userId);

    @Transaction
    default void replaceTeam(int userId, Team newTeam) {
        deleteTeamFromUser(userId);
        insert(newTeam);
    }
}

