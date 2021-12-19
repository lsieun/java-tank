package lsieun.tank.logic;

import lsieun.tank.action.Damageable;
import lsieun.tank.ancillary.Group;
import lsieun.tank.geometry.Geometry;
import lsieun.tank.geometry.Point;

public class Explode extends LivingElement implements Damageable {
    public static final int DEFAULT_HEALTH = 10;

    private static final int[] diameters = {4, 7, 12, 18, 26, 32, 49, 30, 14, 6};

    private final Point center;

    public Explode(Point center) {
        super("", center, Group.NEUTRAL, DEFAULT_HEALTH);

        this.center = center;
    }

    @Override
    public int getMaxHealth() {
        return DEFAULT_HEALTH;
    }

    @Override
    public Geometry getOutline() {
        return null;
    }

    public int getRadius() {
        if (health <= 0) return 0;

        int radius = diameters[DEFAULT_HEALTH - health];
        this.health--;
        return radius;
    }
}
