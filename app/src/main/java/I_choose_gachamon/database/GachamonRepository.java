package I_choose_gachamon.database;

import android.app.Application;
import android.util.Log;

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
        this.allUsers = (ArrayList<User>) this.userDAO.getAllUsers();
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

    public ArrayList<User> getAllUsers() {
        Future<ArrayList<User>> future = GachamonDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<User>>() {
                    @Override
                    public ArrayList<User> call() throws Exception {
                        return (ArrayList<User>) userDAO.getAllUsers();
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

    public void insertUser(User... user){
        GachamonDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }


}
