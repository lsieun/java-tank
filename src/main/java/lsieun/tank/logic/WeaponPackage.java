package lsieun.tank.logic;

import lsieun.tank.ancillary.Group;
import lsieun.tank.geometry.Geometry;
import lsieun.tank.geometry.Point;
import lsieun.tank.geometry.Rectangle;

public class WeaponPackage extends LifelessElement {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;

    private Weapon weapon;

    public WeaponPackage(String name, Point center, Group group, Weapon weapon) {
        super(name, center, group);

        this.weapon = weapon;
    }

    public Geometry getOutline() {
        Point center = this.getCenter();
        return new Rectangle(center, WIDTH, HEIGHT);
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public String toString() {
        return "WeaponPackage{" +
                "weapon=" + weapon +
                "} " + super.toString();
    }
}
