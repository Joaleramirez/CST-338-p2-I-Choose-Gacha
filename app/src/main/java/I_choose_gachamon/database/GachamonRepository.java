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
import I_choose_gachamon.database.entities.Team;
import I_choose_gachamon.database.entities.User;
import I_choose_gachamon.MainActivity;

public class GachamonRepository {
    private final UserDAO userDAO;
    private final MonsterDAO monsterDAO;
    private final SkillDAO skillDAO;
    private final TeamDAO teamDAO;
    private ArrayList<User> allUsers;
    private static GachamonRepository repository;

    public GachamonRepository(Application application){
        GachamonDatabase db = GachamonDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
        this.monsterDAO = db.monsterDAO();
        this.skillDAO = db.skillDAO();
        this.teamDAO = db.teamDAO();
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

    public void updateUser(User user) {
        GachamonDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.update(user);
        });
    }

    public void deleteUser(User user) {
        GachamonDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.delete(user);
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

    public void updateMonster(Monster monster) {
        GachamonDatabase.databaseWriteExecutor.execute(() -> {
            monsterDAO.update(monster);
        });
    }

    public Monster cloneMonsterForUser(Monster originalMonster, int userId) {
        Monster newMonster = new Monster(userId, originalMonster.getName(), originalMonster.getHp(), originalMonster.getAttack(), originalMonster.getSkillId());
        newMonster.setEnergy(originalMonster.getEnergy());
        newMonster.setBaseLevel(originalMonster.getBaseLevel());
        return newMonster;
    }

    public void addMonsterToUser(Monster originalMonster, int userId){
        Monster newMonster = cloneMonsterForUser(originalMonster, userId);
        GachamonDatabase.databaseWriteExecutor.execute(() -> {
            monsterDAO.insert(newMonster);
        });
    }

    public LiveData<List<Monster>> getMonstersForUser(int userId) {
        return monsterDAO.getMonstersForUser(userId);
    }

    public LiveData<List<Monster>> getAllMonsters() {
        return monsterDAO.getAllMonsters();
    }

    public LiveData<Monster> getMonsterById(int monsterId) {
        return monsterDAO.getMonsterById(monsterId);
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

    public void insertTeam(Team team) {
        GachamonDatabase.databaseWriteExecutor.execute(() -> {
            teamDAO.insert(team);
        });
    }

    public void deleteTeamFromUser(int userId) {
        GachamonDatabase.databaseWriteExecutor.execute(() -> {
            teamDAO.deleteTeamFromUser(userId);
        });
    }

    public LiveData<Team> getTeamByUserId(int userId) {
        return teamDAO.getTeamByUserId(userId);
    }

    public void replaceTeam(int userId, Team newTeam) {
        GachamonDatabase.databaseWriteExecutor.execute(() -> {
            teamDAO.replaceTeam(userId, newTeam);
        });
    }

}
