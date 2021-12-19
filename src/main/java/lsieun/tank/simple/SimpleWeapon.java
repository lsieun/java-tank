package lsieun.tank.simple;

import lsieun.tank.ancillary.WeaponType;
import lsieun.tank.logic.Weapon;

public class SimpleWeapon extends Weapon {

    public SimpleWeapon() {
        this("简易武器", WeaponType.UNLIMITED);
    }

    private SimpleWeapon(String name, WeaponType type) {
        super(name, type, 0);
    }

    public static SimpleWeapon getInstance() {
        return new SimpleWeapon();
    }

    @Override
    public String toString() {
        return "SimpleWeapon{} \n" + super.toString();
    }
}
