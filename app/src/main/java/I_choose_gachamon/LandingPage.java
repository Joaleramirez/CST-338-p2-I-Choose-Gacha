package I_choose_gachamon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.i_choose_gacha_mon.R;
import com.example.i_choose_gacha_mon.databinding.ActivityLandingPageBinding;
import com.example.i_choose_gacha_mon.databinding.ActivityMainBinding;

public class LandingPage extends AppCompatActivity {
    ActivityLandingPageBinding binding;

    private static final String LANDING_PAGE_USER_ID = "I_choose_gachamon.LANDING_PAGE_USER_ID";

    int loggedInUserId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginUser();

        if(loggedInUserId == -1){
            Intent intent = LoginPage.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }

        //Uncomment when pages are made
    /*
        //Formation button on Landing page
        binding.formationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingPage.this, Formation.class));
            }
        });

        //Summon button on Landing page
        binding.summonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingPage.this, Summon.class));
            }
        });

        //Enhancement button on Landing page
        binding.enhancementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingPage.this, Enhancement.class));
            }
        });

        //Gym button on Landing page
        binding.gymButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingPage.this, Gym.class));
            }
        });

        //Settings button on Landing page
        binding.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingPage.this, Settings.class));
            }
        });

    */
    }
    private void loginUser() {
        loggedInUserId = getIntent().getIntExtra(LANDING_PAGE_USER_ID, -1);
    }

    static Intent landingPageIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, LandingPage.class);
        intent.putExtra(LANDING_PAGE_USER_ID, userId);
        return intent;
    }
}