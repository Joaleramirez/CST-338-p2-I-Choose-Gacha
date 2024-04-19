package I_choose_gachamon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.i_choose_gacha_mon.R;
import com.example.i_choose_gacha_mon.databinding.ActivityCreateAccountBinding;
import com.example.i_choose_gacha_mon.databinding.ActivityMainBinding;

public class  CreateAccount extends AppCompatActivity {
    ActivityCreateAccountBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Create account button on CreateAccount
        binding.CreateNewAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccount.this, LoginPage.class));
            }
        });
    }
}