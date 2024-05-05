package I_choose_gachamon.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import I_choose_gachamon.database.entities.Monster;
import I_choose_gachamon.database.entities.Skill;
import I_choose_gachamon.database.entities.User;
import I_choose_gachamon.MainActivity;

public class GachamonRepository {
    private final UserDAO userDAO;
    private final MonsterDAO monsterDAO;
    private final SkillDAO skillDAO;
    private ArrayList<User> allUsers;
    private static GachamonRepository repository;

    public GachamonRepository(Application application){
        GachamonDatabase db = GachamonDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
        this.monsterDAO = db.monsterDAO();
        this.skillDAO = db.skillDAO();
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

    public void insertMonster(Monster... monster){
        GachamonDatabase.databaseWriteExecutor.execute(() ->
        {
            monsterDAO.insert(monster);
        });
    }

    public List<Monster> getMonstersForUser(int userId) {
        return monsterDAO.getMonstersForUser(userId);
    }

    public void insertSkill(Skill...skill) {
        GachamonDatabase.databaseWriteExecutor.execute(() ->
        {
            skillDAO.insert(skill);
        });
    }

    public List<Skill> getSkills() {
        return skillDAO.getAllSkills();
    }

    public Skill getMonsterSkill(int skillId) {
        return skillDAO.getMonsterSkill(skillId);
    }


}
