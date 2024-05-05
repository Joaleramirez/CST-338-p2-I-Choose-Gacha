    package I_choose_gachamon;
    
    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.view.Menu;
    import android.view.MenuInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.Button;
    import android.widget.Toast;
    
    import androidx.activity.EdgeToEdge;
    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AlertDialog;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;
    import androidx.lifecycle.Observer;

    import com.example.i_choose_gacha_mon.R;
    import com.example.i_choose_gacha_mon.databinding.ActivityLandingPageBinding;
    import com.example.i_choose_gacha_mon.databinding.ActivityMainBinding;

    import I_choose_gachamon.database.GachamonRepository;
    import I_choose_gachamon.database.entities.Monster;
    import I_choose_gachamon.database.entities.User;
    import com.example.i_choose_gacha_mon.databinding.ActivitySummoningBinding;

    import java.util.List;
    import java.util.Random;

    public class Summoning extends AppCompatActivity {
        ActivitySummoningBinding binding;
        GachamonRepository repository;
        private List<Monster> allMonsters;
        User currentUser;
        private boolean hasRolled = false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivitySummoningBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            repository = GachamonRepository.getRepository(getApplication());
            assert repository != null;
            repository.getAllMonsters().observe(this, new Observer<List<Monster>>() {
                @Override
                public void onChanged(@Nullable final List<Monster> monsters) {
                    allMonsters = monsters;
                }
            });
            SharedPreferences sharedPreferences = getSharedPreferences("LoginPref", MODE_PRIVATE);
            String currentUsername = sharedPreferences.getString("username", null);
            binding.summonUnitButton.setEnabled(false);
            repository.getUserByUserName(currentUsername).observe(this, user -> {
                if (user != null) {
                    currentUser = user;
                    binding.summonUnitButton.setEnabled(true);
                } else {
                    Toast.makeText(this, "No User", Toast.LENGTH_SHORT).show();
                }
            });

            binding.summonUnitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentUser != null) {
                        if (currentUser.getPellets() < 10) {
                            Toast.makeText(Summoning.this, "Not enough pellets for a 10 roll", Toast.LENGTH_SHORT).show();
                        } else if (!hasRolled) {
                            currentUser.setPellets(currentUser.getPellets() - 10);
                            repository.updateUser(currentUser);
                            gachaRoll();
                            hasRolled = true;
                        }
                    } else {
                        Toast.makeText(Summoning.this, "Invalid User", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            binding.BackToMenuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Summoning.this, LandingPage.class));
                }
            });
        }

        private void gachaRoll() {
            if (allMonsters != null) {
                Random random = new Random();

                for (int i = 0; i < 10; i++) {
                    // 40% chance to get a monster, 60% chance to get XP
                    if (random.nextInt(100) < 40) {
                        // Select a random monster from the list of all monsters
                        Monster originalMonster = allMonsters.get(random.nextInt(allMonsters.size()));
                        // Clone the monster for the user
                        Monster newMonster = repository.cloneMonsterForUser(originalMonster, currentUser.getId());
                        // Add the cloned monster to the user
                        repository.addMonsterToUser(newMonster, currentUser.getId());
                    } else {
                        // Add 10 XP to the user
                        currentUser.setXp(currentUser.getXp() + 10);
                        repository.updateUser(currentUser);
                    }
                }
                Toast.makeText(Summoning.this, "Gacha roll complete!", Toast.LENGTH_SHORT).show();
            }
        }
    }