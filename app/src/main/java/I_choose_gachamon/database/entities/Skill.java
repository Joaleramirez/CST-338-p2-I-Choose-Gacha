package I_choose_gachamon.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

import I_choose_gachamon.database.GachamonDatabase;

@Entity(tableName = GachamonDatabase.SKILL_TABLE)
public class Skill {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int skillDmg;
    private int energyCost;

    public Skill(String name, int skillDmg, int energyCost) {
        this.name = name;
        this.skillDmg = skillDmg;
        this.energyCost = energyCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return id == skill.id && skillDmg == skill.skillDmg && energyCost == skill.energyCost && Objects.equals(name, skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, skillDmg, energyCost);
    }

    public int getSkillDmg() {
        return skillDmg;
    }

    public void setSkillDmg(int skillDmg) {
        this.skillDmg = skillDmg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEnergyCost() {
        return energyCost;
    }

    public void setEnergyCost(int energyCost) {
        this.energyCost = energyCost;
    }
}