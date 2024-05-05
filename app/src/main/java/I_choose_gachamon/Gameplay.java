package I_choose_gachamon;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.i_choose_gacha_mon.R;
import com.example.i_choose_gacha_mon.databinding.ActivityGameplayBinding;
import com.example.i_choose_gacha_mon.databinding.ActivityGymBinding;

import java.util.ArrayList;
import java.util.List;

import I_choose_gachamon.database.entities.Monster;

public class Gameplay extends AppCompatActivity {
    ActivityGameplayBinding binding;
    private List<Monster> friendlyMonsters;
    private List<Monster> enemyMonsters;
    private int currentFriendlyIndex = 0;
    private int currentEnemyIndex = 0;
    private int tapCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeMonsters();

        binding.BackToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Gameplay.this, LandingPage.class));
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
        enemyMonsters = new ArrayList<>();
        friendlyMonsters.add(new Monster(null, "Aqua", 100, 2, 1));
        friendlyMonsters.add(new Monster(null, "Blaze", 100, 2, 2));
        enemyMonsters.add(new Monster(null, "Bowser", 100, 1, 1));
        enemyMonsters.add(new Monster(null, "Bowser Jr", 200, 1, 2));
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

            if (tapCounter % 2 == 0) {
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
        startActivity(new Intent(Gameplay.this, GeneralBattle.class));
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
        }
    }


}