package I_choose_gachamon;

public class Monster {
    private int hp;
    private int energy;

    // Constructor
    public Monster(int initialHp) {
        this.hp = initialHp;
        this.energy = 0;
    }

    // Get current HP
    public int getHp() {
        return this.hp;
    }

    // Basic attack method
    public void basicAttack(Monster opponent) {
        if (opponent != null) {
            opponent.hp -= 2;  // Opponent loses 2 HP per basic attack
        }
    }

    // Energy charging method
    public void chargeEnergy() {
        this.energy+= 10;  // Increment energy each time this method is called
        System.out.println("Energy charged to: " + this.energy);
    }

    // Special attack method
    public void specialAttack(Monster opponent) {
        if (this.energy >= 100) {  // Checks if energy is sufficient for a special attack
            if (opponent != null) {
                opponent.hp -= 30;  // Opponent loses 30 HP on special attack
            }
            this.energy = 0;  // Reset energy after the attack
            System.out.println("Special attack executed! Energy reset.");
        } else {
            System.out.println("Not enough energy for special attack. Current energy: " + this.energy);
        }
    }

    public int getEnergy() {
        return energy;
    }
}

