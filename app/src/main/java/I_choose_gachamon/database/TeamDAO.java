package I_choose_gachamon.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
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

    @Query("SELECT * FROM team_table WHERE userId = :userId")
    List<Team> getTeamByUserId(int userId);
}

