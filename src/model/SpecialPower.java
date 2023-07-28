package model;

public class SpecialPower {

    private String name;
    private int damage;
    private int remainingRights;

    public SpecialPower(String name, int damage, int remainingRights) {
        this.name = name;
        this.damage = damage;
        this.remainingRights = remainingRights;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRemainingRights() {
        return remainingRights;
    }

    public void setRemainingRights(int remainingRights) {
        this.remainingRights = remainingRights;
    }

    @Override
    public String toString() {
        return "SpecialPower{" +
                "name='" + name + '\'' +
                ", extraDamage=" + damage +
                ", remainingRights=" + remainingRights +
                '}';
    }
}
