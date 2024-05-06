package I_choose_gachamon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.i_choose_gacha_mon.R;
import com.example.i_choose_gacha_mon.databinding.ActivityCreateAccountBinding;
import com.example.i_choose_gacha_mon.databinding.ActivityMainBinding;

import I_choose_gachamon.database.GachamonRepository;
import I_choose_gachamon.database.entities.User;

public class CreateAccount extends AppCompatActivity {

    private ActivityCreateAccountBinding binding;
    private GachamonRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GachamonRepository.getRepository(getApplication());

        binding.CreateNewAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.CreateUsernameEditTextText.getText().toString();
                String password = binding.CreateNewpasswordEditTextTextPassword.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(CreateAccount.this, "Username and password cannot be empty.", Toast.LENGTH_SHORT).show();
                } else if (username.length() > 10) {
                    Toast.makeText(CreateAccount.this, "Username cannot exceed 10 characters.", Toast.LENGTH_SHORT).show();
                } else if (!username.matches("[a-zA-Z0-9]*")) {
                    Toast.makeText(CreateAccount.this, "Username can only contain letters and numbers.", Toast.LENGTH_SHORT).show();
                } else {
                    User newUser = new User(username, password);
                    repository.insertUser(newUser);
                    Toast.makeText(CreateAccount.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.BackToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccount.this, MainActivity.class));
            }
        });
    }
}
