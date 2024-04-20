package I_choose_gachamon.Database;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import I_choose_gachamon.Database.entities.User;
import I_choose_gachamon.MainActivity;

public class GachamonRepository {
    private UserDAO userDAO;
    private ArrayList<User> allUsers;

    public GachamonRepository(Application application){
        GachamonDatabase db = GachamonDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
        this.allUsers = this.userDAO.getAllUsers();
    }

    public ArrayList<User> getAllUsers() {
        Future<ArrayList<User>> future = GachamonDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<User>>() {
                    @Override
                    public ArrayList<User> call() throws Exception {
                        return userDAO.getAllUsers();
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
           Log.i(MainActivity.TAG, "Problem when getting all Users in the repository");
        }
        return null;
    }

    public void insertUsers(User user){
        GachamonDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }


}
