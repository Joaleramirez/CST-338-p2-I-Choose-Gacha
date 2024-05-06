package I_choose_gachamon.database.entities;

import android.content.Context;
import android.widget.CompoundButton;

import com.example.i_choose_gacha_mon.R;

public class MonsterEnhancementAdapter extends MonsterAdapter {

    public MonsterEnhancementAdapter(Context context) {
        super(context);
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
                    // Unselect previously selected monster
                    if (!selectedMonsters.isEmpty()) {
                        Monster previouslySelectedMonster = selectedMonsters.get(0);
                        previouslySelectedMonster.setSelected(false);
                        notifyItemChanged(monsters.indexOf(previouslySelectedMonster));
                        selectedMonsters.clear();
                    }
                    // Select the current monster
                    selectedMonsters.add(current);
                    current.setSelected(true);
                } else {
                    selectedMonsters.remove(current);
                    current.setSelected(false);
                }
            }
        });
    }


}

