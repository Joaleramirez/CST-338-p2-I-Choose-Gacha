package I_choose_gachamon;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.i_choose_gacha_mon.databinding.ActivitySummoningBinding;

public class Summoning extends AppCompatActivity {
    ActivitySummoningBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySummoningBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.BackToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Summoning.this, LandingPage.class));
            }
        });
    }

}