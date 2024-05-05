package I_choose_gachamon.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

import I_choose_gachamon.database.GachamonDatabase;

@Entity(tableName = GachamonDatabase.MONSTER_TABLE,
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Skill.class,
                        parentColumns = "id",
                        childColumns = "skillId",
                        onDelete = ForeignKey.CASCADE)
        })
public class Monster {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Integer userId; // foreign key to tie a monster to a user
    private String name;
    private int hp;
    private int attack;
    private int energy = 0;
    private int baseLevel = 1;
    private int skillId; // foreign key to a skill

    public Monster(Integer userId, String name, int hp, int attack, int skillId) {
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
        return id == monster.id && Objects.equals(userId, monster.userId) && hp == monster.hp && attack == monster.attack && energy == monster.energy && baseLevel == monster.baseLevel && skillId == monster.skillId && Objects.equals(name, monster.name);
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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

    public void chargeEnergy() {
        energy += 10;
    }
    public void basicAttack(Monster opponent) {
        if (opponent != null) {
            opponent.hp -= getAttack();  // Opponent loses 2 HP per basic attack
        }
    }
    public void specialAttack(Monster opponent) {
        if (this.energy >= 100) {  // Checks if energy is sufficient for a special attack
            if (opponent != null) {
                opponent.hp -= 30;  // Opponent loses 30 HP on special attack
            }
            this.energy -= 100;  // Reset energy after the attack
            System.out.println("Special attack executed! Energy reset.");
        } else {
            System.out.println("Not enough energy for special attack. Current energy: " + this.energy);
        }
    }
    public void takeNormalDamage(Monster opponent){
        if (opponent != null) {
            opponent.hp -= getAttack();  // Opponent loses 2 HP per basic attack
        }
    }
    public void takeSpecialDamage(Monster opponent) {
        if (this.energy >= 100) {  // Checks if energy is sufficient for a special attack
            if (opponent != null) {
                opponent.hp -= 30;  // Opponent loses 30 HP on special attack
            }
            this.energy -= 100;  // Reset energy after the attack
            System.out.println("Special attack executed! Energy reset.");
        } else {
            System.out.println("Not enough energy for special attack. Current energy: " + this.energy);
        }
    }
}

