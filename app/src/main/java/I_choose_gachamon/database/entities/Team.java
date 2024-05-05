package I_choose_gachamon.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

import I_choose_gachamon.database.GachamonDatabase;

@Entity(tableName = GachamonDatabase.TEAM_TABLE,
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Monster.class,
                        parentColumns = "id",
                        childColumns = "monsterId1",
                        onDelete = ForeignKey.SET_NULL),
                @ForeignKey(entity = Monster.class,
                        parentColumns = "id",
                        childColumns = "monsterId2",
                        onDelete = ForeignKey.SET_NULL),
                @ForeignKey(entity = Monster.class,
                        parentColumns = "id",
                        childColumns = "monsterId3",
                        onDelete = ForeignKey.SET_NULL),
                @ForeignKey(entity = Monster.class,
                        parentColumns = "id",
                        childColumns = "monsterId4",
                        onDelete = ForeignKey.SET_NULL)
        },
        indices = {@Index(value = "userId", unique = true)})
public class Team {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private Integer monsterId1;
    private Integer monsterId2;
    private Integer monsterId3;
    private Integer monsterId4;

    public Team(int userId, Integer monsterId1, Integer monsterId2, Integer monsterId3, Integer monsterId4) {
        this.userId = userId;
        this.monsterId1 = monsterId1;
        this.monsterId2 = monsterId2;
        this.monsterId3 = monsterId3;
        this.monsterId4 = monsterId4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return id == team.id && userId == team.userId && Objects.equals(monsterId1, team.monsterId1) && Objects.equals(monsterId2, team.monsterId2) && Objects.equals(monsterId3, team.monsterId3) && Objects.equals(monsterId4, team.monsterId4);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, monsterId1, monsterId2, monsterId3, monsterId4);
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

    public Integer getMonsterId1() {
        return monsterId1;
    }

    public void setMonsterId1(Integer monsterId1) {
        this.monsterId1 = monsterId1;
    }

    public Integer getMonsterId2() {
        return monsterId2;
    }

    public void setMonsterId2(Integer monsterId2) {
        this.monsterId2 = monsterId2;
    }

    public Integer getMonsterId3() {
        return monsterId3;
    }

    public void setMonsterId3(Integer monsterId3) {
        this.monsterId3 = monsterId3;
    }

    public Integer getMonsterId4() {
        return monsterId4;
    }

    public void setMonsterId4(Integer monsterId4) {
        this.monsterId4 = monsterId4;
    }
}
