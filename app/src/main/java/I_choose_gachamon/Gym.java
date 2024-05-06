package I_choose_gachamon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.i_choose_gacha_mon.R;
import com.example.i_choose_gacha_mon.databinding.ActivityGymBinding;
import com.example.i_choose_gacha_mon.databinding.ActivitySummoningBinding;

public class Gym extends AppCompatActivity {
    ActivityGymBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGymBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    //Back to menu button
        binding.BackToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Gym.this, LandingPage.class));
            }
        });
        //General battle map button
        binding.generalBattleImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Gym.this, GeneralBattle.class));
            }
        });
        //Boss battle map button
        binding.bossBattleImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Gym.this, BossBattle.class));
            }
        });

        };
}
