package I_choose_gachamon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.i_choose_gacha_mon.databinding.ActivityFormationBinding;

public class Formation extends AppCompatActivity {

    ActivityFormationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.BackToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Formation.this, LandingPage.class));
            }
        });


    }
}