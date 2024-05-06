package I_choose_gachamon.database.entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.i_choose_gacha_mon.R;

import java.util.ArrayList;
import java.util.List;

public class MonsterAdapter extends RecyclerView.Adapter<MonsterAdapter.MonsterViewHolder> {

    public List<Monster> monsters; // Cached copy of monsters
    public List<Monster> selectedMonsters;

    public MonsterAdapter(Context context) {
        selectedMonsters = new ArrayList<>();
    }


    @Override
    @NonNull
    public MonsterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.monster_item, parent, false);
        return new MonsterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MonsterViewHolder holder, int position) {
        Monster current = monsters.get(position);
        holder.monsterNameTextView.setText(current.getName());
        holder.monsterLevelTextView.setText(String.format(holder.itemView.getContext().getResources().getString(R.string.monster_level), current.getBaseLevel()));
        holder.monsterCheckBox.setOnCheckedChangeListener(null);
        holder.monsterCheckBox.setChecked(current.isSelected());
        holder.monsterCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (selectedMonsters.size() < 4) {
                        selectedMonsters.add(current);
                        current.setSelected(true);
                    } else {
                        buttonView.setChecked(false);
                        Toast.makeText(buttonView.getContext(), "You can only select 4 monsters.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    selectedMonsters.remove(current);
                    current.setSelected(false);
                }
            }
        });
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (monsters != null)
            return monsters.size();
        else return 0;
    }

    public List<Monster> getSelectedMonsters() {
        return selectedMonsters;
    }

    public Monster getSelectedMonster() {
        return selectedMonsters.isEmpty() ? null : selectedMonsters.get(0);
    }


    public static class MonsterViewHolder extends RecyclerView.ViewHolder {
        CheckBox monsterCheckBox;
        TextView monsterNameTextView;
        TextView monsterLevelTextView;

        private MonsterViewHolder(View itemView) {
            super(itemView);
            monsterCheckBox = itemView.findViewById(R.id.monsterCheckBox);
            monsterNameTextView = itemView.findViewById(R.id.monsterNameTextView);
            monsterLevelTextView = itemView.findViewById(R.id.monsterLevelTextView);
        }
    }

}
