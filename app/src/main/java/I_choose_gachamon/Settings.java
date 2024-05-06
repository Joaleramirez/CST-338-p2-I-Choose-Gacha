package I_choose_gachamon;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.i_choose_gacha_mon.R;
import com.example.i_choose_gacha_mon.databinding.ActivityGymBinding;
import com.example.i_choose_gacha_mon.databinding.ActivitySettingsBinding;

import I_choose_gachamon.database.GachamonRepository;
import I_choose_gachamon.database.entities.User;

public class Settings extends AppCompatActivity {
    ActivitySettingsBinding binding;
    GachamonRepository repository;
    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GachamonRepository.getRepository(getApplication());

        SharedPreferences sharedPreferences = getSharedPreferences("LoginPref", MODE_PRIVATE);
        String currentUsername = sharedPreferences.getString("username", null);
        repository.getUserByUserName(currentUsername).observe(this, user -> {
            if (user != null) {
                currentUser = user;
            }
        });

        binding.deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Settings.this)
                        .setTitle("Delete account")
                        .setMessage("Are you sure you want to delete your account?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                repository.deleteUser(currentUser);
                                startActivity(new Intent(Settings.this, LoginPage.class));
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }
}