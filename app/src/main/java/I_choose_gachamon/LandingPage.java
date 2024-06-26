package I_choose_gachamon;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.i_choose_gacha_mon.R;
import com.example.i_choose_gacha_mon.databinding.ActivityLandingPageBinding;
import com.example.i_choose_gacha_mon.databinding.ActivityMainBinding;

import I_choose_gachamon.database.entities.User;

public class LandingPage extends AppCompatActivity {
    ActivityLandingPageBinding binding;

    private static final String LANDING_PAGE_USER_ID = "I_choose_gachamon.LANDING_PAGE_USER_ID";

    private int loggedInUserId = -1;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginUser();
        invalidateOptionsMenu();

        if(loggedInUserId == -1){
            Intent intent = LoginPage.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }


        //Code for seeing admin only stuff
        Button adminButton = findViewById(R.id.Admin_button);
        Button settingsButton = findViewById(R.id.settingsButton);
        if (user.isAdmin()) {
            adminButton.setVisibility(View.VISIBLE);
            settingsButton.setVisibility(View.GONE);
        } else {
            settingsButton.setVisibility(View.VISIBLE);
            adminButton.setVisibility(View.GONE);
        }
        //Summon button on Landing page
        binding.summonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingPage.this, Summoning.class));
            }
        });

        //Formation button on Landing page
        binding.formationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingPage.this, Formation.class));
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


        binding.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingPage.this, Settings.class));
            }
        });
        //Admin settings button on Landing page

    }
    private void loginUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPref", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        if (username != null) {
            user = new User(username, "password");
            loggedInUserId = getIntent().getIntExtra(LANDING_PAGE_USER_ID, -1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        item.setTitle(user.getUsername());
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {

                showLogoutDialog();
                return false;
            }
        });
        return true;
    }

    private void showLogoutDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LandingPage.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Logout?");
        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertBuilder.create().show();
    }

    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        startActivity(LoginPage.loginIntentFactory(getApplicationContext()));
        finish();
    }

    static Intent landingPageIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, LandingPage.class);
        intent.putExtra(LANDING_PAGE_USER_ID, userId);
        return intent;
    }
}