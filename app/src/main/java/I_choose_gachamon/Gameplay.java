package I_choose_gachamon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.i_choose_gacha_mon.R;
import com.example.i_choose_gacha_mon.databinding.ActivityGameplayBinding;
import com.example.i_choose_gacha_mon.databinding.ActivityGymBinding;

import I_choose_gachamon.database.entities.Monster;

public class Gameplay extends AppCompatActivity {
    ActivityGameplayBinding binding;
    Monster monster;
    TextView tvMonsterHp, tvMonsterEnergy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        monster = new Monster(1,"badguy", 100,10,1);
        tvMonsterHp = findViewById(R.id.tvMonsterHp);
        tvMonsterEnergy = findViewById(R.id.tvMonsterEnergy);

        binding.BackToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Gameplay.this, LandingPage.class));
            }
        });
        binding.skill1ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monster.basicAttack(monster);// Adjust the target as needed
                monster.chargeEnergy();
                updateUI();
            }
        });
        binding.skill2ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monster.getEnergy() >= 100) {
                    monster.specialAttack(monster);
                    updateUI();
                }
            }
        });

    };
    private void updateUI() {
        tvMonsterHp.setText("HP: " + monster.getHp());
        tvMonsterEnergy.setText("Energy: " + monster.getEnergy());
    }
}