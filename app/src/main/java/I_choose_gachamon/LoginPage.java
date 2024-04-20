package I_choose_gachamon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.i_choose_gacha_mon.R;
import com.example.i_choose_gacha_mon.databinding.ActivityLoginPageBinding;
import com.example.i_choose_gacha_mon.databinding.ActivityMainBinding;

public class LoginPage extends AppCompatActivity {
    private ActivityLoginPageBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Login button on LoginPage
        binding.LoginPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LandingPage.landingPageIntentFactory(getApplicationContext(), 0);
                startActivity(intent);
            }
        });
    }

    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginPage.class);
    }

}