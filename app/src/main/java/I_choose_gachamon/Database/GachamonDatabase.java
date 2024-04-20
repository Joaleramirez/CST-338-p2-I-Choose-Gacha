package I_choose_gachamon.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import I_choose_gachamon.Database.entities.User;
import I_choose_gachamon.MainActivity;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class GachamonDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "Gachamon_database";
    public static final String USER_TABLE = "user_table";


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
        }
    };

    public abstract UserDAO userDAO();

}
