package I_choose_gachamon.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import I_choose_gachamon.database.entities.User;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM " + GachamonDatabase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getAllUsers();


    @Query("DELETE FROM " + GachamonDatabase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * FROM  " + GachamonDatabase.USER_TABLE + " WHERE username == :username")
    LiveData<User> getUserByUserName(String username);
}
