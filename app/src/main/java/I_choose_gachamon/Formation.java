package I_choose_gachamon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.i_choose_gacha_mon.R;
import com.example.i_choose_gacha_mon.databinding.ActivityFormationBinding;

import java.util.ArrayList;
import java.util.List;

import I_choose_gachamon.database.GachamonRepository;
import I_choose_gachamon.database.entities.Monster;
import I_choose_gachamon.database.entities.MonsterAdapter;
import I_choose_gachamon.database.entities.Team;
import I_choose_gachamon.database.entities.User;

public class Formation extends AppCompatActivity {

    ActivityFormationBinding binding;
    private GachamonRepository repository;
    private User currentUser;
    private MonsterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recyclerView = findViewById(R.id.monster_recycler_view);
        adapter = new MonsterAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = GachamonRepository.getRepository(getApplication());
        assert repository != null;
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPref", MODE_PRIVATE);
        String currentUsername = sharedPreferences.getString("username", null);
        repository.getUserByUserName(currentUsername).observe(this, user -> {
            if (user != null) {
                currentUser = user;
                repository.getMonstersForUser(currentUser.getId()).observe(this, new Observer<List<Monster>>() {
                    @Override
                    public void onChanged(@Nullable final List<Monster> monsters) {
                        // Update the cached copy of the monsters in the adapter.
                        adapter.setMonsters(monsters);
                    }
                });
            }
        });
                binding.BackToMenuButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Formation.this, LandingPage.class));
                    }
                });

                binding.formationSaveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<Monster> selectedMonsters = adapter.getSelectedMonsters();
                        if (selectedMonsters.size() == 4) {
                            // Create a new team
                            Team team = new Team(currentUser.getId(), selectedMonsters.get(0).getId(), selectedMonsters.get(1).getId(), selectedMonsters.get(2).getId(), selectedMonsters.get(3).getId());
                            repository.replaceTeam(currentUser.getId(), team);
                            Toast.makeText(Formation.this, "Team saved successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Formation.this, "Please select exactly 4 monsters.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            public List<Monster> getSelectedMonsters () {
                return adapter.getSelectedMonsters();
            }

}