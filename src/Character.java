public class Character
{
    private int health;
    private String name;
    private int strength;

    public Character(String name, int health, int strength)
    {
        this.health = health;
        this.name = name;
        this.strength = strength;
    }

    public int getHealth()
    {
        return health;
    }

    public String getName() {
        return name;
    }

    public int getStrength() {
        return strength;
    }

    public String fight(Character defender)
    {
        String dead = null;
        int attack = (int) (Math.random() * this.strength);
        int defense = (int) (Math.random() * defender.getStrength());
        if (attack > defense)
        {
            defender.setHealth(defender.getHealth() - attack);
            System.out.printf("%n%s suffered %d damage. %s's health is now at %d%n", defender.getName(), attack, defender.getName(), defender.getHealth());
        }
        else if (attack < defense)
        {
            this.health -= defense;
            System.out.printf("%n%s suffered %d damage. %s's health is now at %d%n", this.name, defense, this.name, this.health);
        }
        else
        {
            System.out.println("No damage");
        }

        if (this.health < 1)
        {
            dead = this.getName();
        }
        else if (defender.getHealth() < 1)
        {
            dead = defender.getName();
        }
        if (dead != null)
        {
            System.out.printf("%n%s has been killed!%n%n", dead);
        }
        return dead;
    }
    public void setHealth(int health) {
        this.health = health;
    }
}
