package I_choose_gachamon;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.i_choose_gacha_mon.R;
import com.example.i_choose_gacha_mon.databinding.ActivityBossBattleGameplayBinding;
import com.example.i_choose_gacha_mon.databinding.ActivityGameplayBinding;
import com.example.i_choose_gacha_mon.databinding.ActivityGymBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import I_choose_gachamon.database.entities.Monster;

public class BossBattleGameplay extends AppCompatActivity {
    ActivityBossBattleGameplayBinding binding;
    private List<Monster> friendlyMonsters;
    private List<Monster> enemyMonsters;
    private int currentFriendlyIndex = 0;
    private int currentEnemyIndex = 0;
    private int tapCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBossBattleGameplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeMonsters();

        binding.BackToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BossBattleGameplay.this, LandingPage.class));
            }
        });

        binding.skill1ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAttack(false);
            }
        });

        binding.skill2ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAttack(true);
            }
        });

        updateUI();
    }
    private void initializeMonsters() {
        friendlyMonsters = new ArrayList<>();
        friendlyMonsters.add(new Monster(null, "Frostbite", 100, 100, 1));
        List<Monster> predefinedMonsters = new ArrayList<>();
        predefinedMonsters.add(new Monster(null, "Shadow", 250, 10, 2));
        predefinedMonsters.add(new Monster(null, "Mystic", 500, 10, 1));
        predefinedMonsters.add(new Monster(null, "Chaos", 1000, 10, 2));
        enemyMonsters = new ArrayList<>();
        Random random = new Random();
        int numberOfMonstersToAdd = random.nextInt(1) + 1;

        for (int i = 0; i < numberOfMonstersToAdd; i++) {
            int index = random.nextInt(predefinedMonsters.size());
            Monster selectedMonster = predefinedMonsters.get(index);
            enemyMonsters.add(selectedMonster);
        }
    }
    private void performAttack(boolean isSpecial) {
        tapCounter++;
        if (currentEnemyIndex < enemyMonsters.size() && currentFriendlyIndex < friendlyMonsters.size()) {
            Monster friendly = friendlyMonsters.get(currentFriendlyIndex);
            Monster enemy = enemyMonsters.get(currentEnemyIndex);

            if (isSpecial && friendly.getEnergy() >= 100) {
                friendly.specialAttack(enemy);
            } else {
                friendly.chargeEnergy();
                friendly.basicAttack(enemy);
            }

            if (enemy.getHp() <= 0 && !nextMonster(enemyMonsters, true)) {
                showVictoryMessage("Victory!");
                return;
            }

            if (tapCounter % 5 == 0) {
                enemy.takeNormalDamage(friendly);
                if (friendly.getHp() <= 0 && !nextMonster(friendlyMonsters, false)) {
                    showVictoryMessage("Defeat!");
                    return;
                }
            }

            updateUI();
        }
    }

    private boolean nextMonster(List<Monster> monsters, boolean isEnemy) {
        if (isEnemy) {
            currentEnemyIndex++;
        } else {
            currentFriendlyIndex++;
        }

        if ((isEnemy && currentEnemyIndex < monsters.size()) || (!isEnemy && currentFriendlyIndex < monsters.size())) {
            return true;
        } else {
            return false;
        }
    }

    private void showVictoryMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        startActivity(new Intent(BossBattleGameplay.this, BossBattle.class));
    }

    private void updateUI() {
        if (currentFriendlyIndex < friendlyMonsters.size() && currentEnemyIndex < enemyMonsters.size()) {
            Monster friendly = friendlyMonsters.get(currentFriendlyIndex);
            Monster enemy = enemyMonsters.get(currentEnemyIndex);
            binding.tvEMonsterName.setText("Enemy: " + enemy.getName());
            binding.tvEMonsterHp.setText("Enemy HP: " + enemy.getHp());
            binding.tvEMonsterEnergy.setText("Enemy Energy: " + enemy.getEnergy());
            binding.tvFMonsterName.setText("Friendly: " + friendly.getName());
            binding.tvFMonsterHp.setText("HP: " + friendly.getHp());
            binding.tvFMonsterEnergy.setText("Energy: " + friendly.getEnergy());

            updateMonsterImage(binding.enemyUnitImageView, enemy.getName());
            updateMonsterImage(binding.friendlyUnitImageView, friendly.getName());
        }
    }
    private void updateMonsterImage(ImageView imageView, String monsterName) {
        int resourceId = this.getResources().getIdentifier(monsterName.toLowerCase(), "drawable", this.getPackageName());
        if (resourceId != 0) {
            imageView.setImageResource(resourceId);
        } else {
            imageView.setImageResource(R.drawable.aqua);
        }
    }


}