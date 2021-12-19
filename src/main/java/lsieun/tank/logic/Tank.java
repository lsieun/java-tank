package lsieun.tank.logic;

import java.util.LinkedList;
import java.util.List;

import lsieun.tank.action.Damageable;
import lsieun.tank.action.Equipable;
import lsieun.tank.action.Moveable;
import lsieun.tank.ancillary.Direction;
import lsieun.tank.ancillary.Group;
import lsieun.tank.ancillary.Speed;
import lsieun.tank.ancillary.WeaponType;
import lsieun.tank.geometry.Geometry;
import lsieun.tank.geometry.Point;
import lsieun.tank.geometry.Rectangle;
import lsieun.tank.utils.GameUtils;

public class Tank extends LivingElement implements Moveable, Equipable, Damageable {

    public static final int DEFAULT_HEALTH = 20;
    public static final int DEFAULT_SPEED = 20;
    public static final int DEFAULT_WIDTH = 60;
    public static final int DEFAULT_HEIGHT = 60;

    protected Direction direction;
    protected List<Weapon> weapons;
    protected Weapon currentWeapon;
    protected int speedScalar;


    public Tank(String name, Point center, Group group, Direction direction) {
        this(name, center, group, direction, DEFAULT_SPEED);
    }

    public Tank(String name, Point center, Group group,
                Direction direction, int speedScalar) {
        super(name, center, group, DEFAULT_HEALTH);

        this.direction = direction;
        this.speedScalar = speedScalar;
        this.weapons = new LinkedList<>();
    }


    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public int getSpeedScalar() {
        return speedScalar;
    }

    public void setSpeedScalar(int speedScalar) {
        this.speedScalar = speedScalar;
    }

    public Weapon getCurrentWeapon() {
        if(this.currentWeapon == null || this.currentWeapon.getMissileCount() < 1) {
            for (Weapon weapon : this.weapons) {
                if (weapon.getType() == WeaponType.LIMITED && weapon.getMissileCount() > 0) {
                    this.currentWeapon = weapon;
                }
            }
        }

        if(this.currentWeapon == null || this.currentWeapon.getMissileCount() < 1) {
            for (Weapon weapon : this.weapons) {
                if (weapon.getType() == WeaponType.UNLIMITED) {
                    this.currentWeapon = weapon;
                }
            }
        }
        return currentWeapon;
    }

    public void setCurrentWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    public int getMaxHealth() {
        return DEFAULT_HEALTH;
    }

    @Override
    public Geometry getOutline() {
        Point center = this.getCenter();
        return new Rectangle(center, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public void equip(Weapon weapon) {
        if (weapon == null) return;

        boolean exists = false;
        for (Weapon w : this.weapons) {
            if (w.getId().equals(weapon.getId())) {
                WeaponType type = w.getType();
                if (type == WeaponType.LIMITED) {
                    int oldCount = w.getMissileCount();
                    int newCount = weapon.getMissileCount();
                    w.setMissileCount(oldCount + newCount);
                }

                exists = true;
            }
        }

        if (!exists) {
            this.weapons.add(weapon);
            weapon.setTank(this);
        }

    }

    public Missile fire() {
        Weapon weapon = this.getCurrentWeapon();
        return weapon.fire();
    }

    public Speed getSpeed() {
        return GameUtils.scalar2Speed(this.speedScalar, this.direction);
    }

    public void move() {
        int x = this.center.getX();
        int y = this.center.getY();

        int velocity = (int)Math.sqrt(speedScalar);
        Direction direction = this.getDirection();


        switch (direction) {
            case WEST:
                x -= velocity;
                break;
            case NORTH_WEST:
                x -= velocity;
                y -= velocity;
                break;
            case NORTH:
                y -= velocity;
                break;
            case NORTH_EAST:
                x += velocity;
                y -= velocity;
                break;
            case EAST:
                x += velocity;
                break;
            case SOUTH_EAST:
                x += velocity;
                y += velocity;
                break;
            case SOUTH:
                y += velocity;
                break;
            case SOUTH_WEST:
                x -= velocity;
                y += velocity;
                break;
            case NONE:
                break;
        }

        this.center.setX(x);
        this.center.setY(y);
    }

    @Override
    public String toString() {
        return "Tank{" +
                "\n    direction=" + direction +
                ", \n    speedScalar=" + speedScalar +
                ", \n    currentWeapon=" + currentWeapon.getName() +
                "\n} \n" + super.toString();
    }
}
