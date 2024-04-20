package I_choose_gachamon.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import I_choose_gachamon.database.entities.User;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + GachamonDatabase.USER_TABLE + " ORDER BY username")
    List<User> getAllUsers();


    @Query("DELETE FROM " + GachamonDatabase.USER_TABLE)
    void deleteAll();
}
