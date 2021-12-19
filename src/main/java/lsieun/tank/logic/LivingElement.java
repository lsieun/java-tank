package lsieun.tank.logic;

import lsieun.tank.action.Damageable;
import lsieun.tank.ancillary.Group;
import lsieun.tank.geometry.Geometry;
import lsieun.tank.geometry.Point;

public abstract class LivingElement extends GameElement implements Damageable {
    protected int health;

    public LivingElement(String name, Point center, Group group, int health) {
        super(name, center, group);

        this.health = health;
    }


    public int getHealth() {
        return Math.max(this.health, 0);
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void damage(int amount) {
        this.health = this.health - amount;
    }

    public abstract int getMaxHealth();

    public abstract Geometry getOutline();
}
