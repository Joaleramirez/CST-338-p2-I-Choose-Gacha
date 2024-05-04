package I_choose_gachamon.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

import I_choose_gachamon.database.GachamonDatabase;

@Entity(tableName = GachamonDatabase.MONSTER_TABLE, foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "userId",
        onDelete = ForeignKey.CASCADE))
public class Monster {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId; // foreign key to tie a monster to a user
    private String name;
    private int hp;
    private int attack;
    private int energy = 0;
    private int baseLevel = 1;
    private int skillId;

    public Monster(int userId, String name, int hp, int attack, int skillId) {
        this.userId = userId;
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.skillId = skillId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monster monster = (Monster) o;
        return id == monster.id && userId == monster.userId && hp == monster.hp && attack == monster.attack && energy == monster.energy && baseLevel == monster.baseLevel && skillId == monster.skillId && Objects.equals(name, monster.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, name, hp, attack, energy, baseLevel, skillId);
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getBaseLevel() {
        return baseLevel;
    }

    public void setBaseLevel(int baseLevel) {
        this.baseLevel = baseLevel;
    }
}
