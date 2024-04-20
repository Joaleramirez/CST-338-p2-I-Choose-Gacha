package I_choose_gachamon.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import I_choose_gachamon.database.entities.User;
import I_choose_gachamon.MainActivity;

public class GachamonRepository {
    private final UserDAO userDAO;
    private ArrayList<User> allUsers;
    private static GachamonRepository repository;

    public GachamonRepository(Application application){
        GachamonDatabase db = GachamonDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
    }

    public static GachamonRepository getRepository(Application application){
        if (repository != null) {
            return repository;
        }
        Future<GachamonRepository> future = GachamonDatabase.databaseWriteExecutor.submit(
                new Callable<GachamonRepository>() {
                    @Override
                    public GachamonRepository call() throws Exception {
                        return new GachamonRepository(application);
                    }
                }
        );
        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e){
            Log.d(MainActivity.TAG, "Problem getting GachamonRepository, thread error.");
        }
        return null;
    }


    public void insertUser(User... user){
        GachamonDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

}
