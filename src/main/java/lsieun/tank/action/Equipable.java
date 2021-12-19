package lsieun.tank.action;

import lsieun.tank.logic.Missile;
import lsieun.tank.logic.Weapon;

public interface Equipable {
    void equip(Weapon weapon);

    Missile fire();
}
