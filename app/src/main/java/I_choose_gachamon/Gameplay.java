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
import com.example.i_choose_gacha_mon.databinding.ActivityGameplayBinding;
import com.example.i_choose_gacha_mon.databinding.ActivityGymBinding;

public class Gameplay extends AppCompatActivity {
    ActivityGameplayBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.BackToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Gameplay.this, LandingPage.class));
            }
        });
    };
}