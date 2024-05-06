package I_choose_gachamon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.i_choose_gacha_mon.R;
import com.example.i_choose_gacha_mon.databinding.ActivityEnhancmentBinding;
import com.example.i_choose_gacha_mon.databinding.ActivityGymBinding;

import I_choose_gachamon.database.GachamonRepository;
import I_choose_gachamon.database.entities.Monster;
import I_choose_gachamon.database.entities.MonsterAdapter;
import I_choose_gachamon.database.entities.MonsterEnhancementAdapter;
import I_choose_gachamon.database.entities.User;

public class Enhancement extends AppCompatActivity {

    ActivityEnhancmentBinding binding;
    private GachamonRepository repository;
    private User currentUser;
    private MonsterEnhancementAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnhancmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recyclerView = findViewById(R.id.monster_recycler_view);
        adapter = new MonsterEnhancementAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = GachamonRepository.getRepository(getApplication());
        assert repository != null;
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPref", MODE_PRIVATE);
        String currentUsername = sharedPreferences.getString("username", null);
        repository.getUserByUserName(currentUsername).observe(this, user -> {
            if (user != null) {
                currentUser = user;
                binding.monsterXPTextView.setText(String.format(getResources().getString(R.string.user_xp), currentUser.getXp()));
                repository.getMonstersForUser(currentUser.getId()).observe(this, monsters -> {
                    // Update the cached copy of the monsters in the adapter.
                    adapter.setMonsters(monsters);
                });
            }
        });

        binding.enhancementButton.setOnClickListener(v -> {
            Monster selectedMonster = adapter.getSelectedMonster();
            if (selectedMonster != null && currentUser.getXp() >= 100 && selectedMonster.getBaseLevel() < 10) {
                // Deduct XP from user
                currentUser.setXp(currentUser.getXp() - 100);
                repository.updateUser(currentUser);
                binding.monsterXPTextView.setText(String.format(getResources().getString(R.string.user_xp), currentUser.getXp()));

                // Increase monster level
                selectedMonster.setBaseLevel(selectedMonster.getBaseLevel() + 1);
                repository.updateMonster(selectedMonster);

                Toast.makeText(Enhancement.this, "Monster level increased!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Enhancement.this, "Ensure sufficient XP and monster is not max level.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.BackToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Enhancement.this, LandingPage.class));
            }
        });

    }
}