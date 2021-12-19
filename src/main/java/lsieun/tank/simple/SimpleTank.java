package lsieun.tank.simple;

import lsieun.tank.ancillary.Direction;
import lsieun.tank.ancillary.Group;
import lsieun.tank.geometry.Point;
import lsieun.tank.logic.Tank;

public class SimpleTank extends Tank {

    public SimpleTank(Point center, Group group, Direction direction) {
        this("简易坦克", center, group, direction);
    }

    public SimpleTank(String name, Point center, Group group, Direction direction) {
        super(name, center, group, direction);
    }

    @Override
    public String toString() {
        return "SimpleTank{} \n" + super.toString();
    }
}
