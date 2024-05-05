package I_choose_gachamon.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import I_choose_gachamon.MainActivity;
import I_choose_gachamon.database.entities.Monster;
import I_choose_gachamon.database.entities.Skill;
import I_choose_gachamon.database.entities.Team;
import I_choose_gachamon.database.entities.User;

@Database(entities = {User.class, Monster.class, Skill.class, Team.class}, version = 1, exportSchema = false)
public abstract class GachamonDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "Gachamon_database";
    public static final String USER_TABLE = "user_table";
    public static final String MONSTER_TABLE = "monster_table";
    public static final String SKILL_TABLE = "skill_table";
    public static final String TEAM_TABLE = "team_table";
    private static volatile GachamonDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static GachamonDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GachamonDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            GachamonDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();

                }
            }
        }
        return INSTANCE;
    }


    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            databaseWriteExecutor.execute(()-> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("admin2", "admin2");
                admin.setAdmin(true);
                dao.insert(admin);
                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);
                SkillDAO sDao = INSTANCE.skillDAO();
                sDao.deleteAll();
                MonsterDAO mDao = INSTANCE.monsterDAO();
                mDao.deleteAll();
                // Skill 1 - AquaSplash
                Skill skill1 = new Skill("AquaSplash", 25, 110);
                sDao.insert(skill1);

                // Monster 1 - Aqua
                Monster monster1 = new Monster(null, "Aqua", 100, 5, 1);
                mDao.insert(monster1);

                // Skill 2 - BlazeStrike
                Skill skill2 = new Skill("BlazeStrike", 30, 120);
                sDao.insert(skill2);

                // Monster 2 - Blaze
                Monster monster2 = new Monster(null, "Blaze", 110, 10, 2);
                mDao.insert(monster2);

                // Skill 3 - ElectricShock
                Skill skill3 = new Skill("ElectricShock", 25, 130);
                sDao.insert(skill3);

                // Monster 3 - Volt
                Monster monster3 = new Monster(null, "Volt", 120, 5, 3);
                mDao.insert(monster3);

                // Skill 4 - IceShard
                Skill skill4 = new Skill("IceShard", 30, 140);
                sDao.insert(skill4);

                // Monster 4 - Frostbite
                Monster monster4 = new Monster(null, "Frostbite", 130, 10, 4);
                mDao.insert(monster4);

                // Skill 5 - ToxicSting
                Skill skill5 = new Skill("ToxicSting", 35, 150);
                sDao.insert(skill5);

                // Monster 5 - Venom
                Monster monster5 = new Monster(null, "Venom", 140, 10, 5);
                mDao.insert(monster5);

                // Skill 6 - WindSlash
                Skill skill6 = new Skill("WindSlash", 40, 160);
                sDao.insert(skill6);

                // Monster 6 - Gale
                Monster monster6 = new Monster(null, "Gale", 150, 15, 6);
                mDao.insert(monster6);

                // Skill 7 - Earthquake
                Skill skill7 = new Skill("Earthquake", 45, 170);
                sDao.insert(skill7);

                // Monster 7 - Quake
                Monster monster7 = new Monster(null, "Quake", 160, 15, 7);
                mDao.insert(monster7);

                // Skill 8 - ShadowStrike
                Skill skill8 = new Skill("ShadowStrike", 30, 180);
                sDao.insert(skill8);

                // Monster 8 - Shadow
                Monster monster8 = new Monster(null, "Shadow", 170, 10, 8);
                mDao.insert(monster8);

                // Skill 9 - MysticBlast
                Skill skill9 = new Skill("MysticBlast", 35, 190);
                sDao.insert(skill9);

                // Monster 9 - Mystic
                Monster monster9 = new Monster(null, "Mystic", 180, 15, 9);
                mDao.insert(monster9);

                // Skill 10 - ChaosWave
                Skill skill10 = new Skill("ChaosWave", 40, 200);
                sDao.insert(skill10);

                // Monster 10 - Chaos
                Monster monster10 = new Monster(null, "Chaos", 190, 10, 10);
                mDao.insert(monster10);
            });
        }
    };

    public abstract UserDAO userDAO();

    public abstract MonsterDAO monsterDAO();

    public abstract SkillDAO skillDAO();

    public abstract TeamDAO teamDAO();

}
