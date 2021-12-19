package lsieun.tank.logic;

import lsieun.tank.ancillary.Group;
import lsieun.tank.geometry.Geometry;
import lsieun.tank.geometry.Point;

public abstract class LifelessElement extends GameElement {

    public LifelessElement(String name, Point center, Group group) {
        super(name, center, group);
    }

    public abstract Geometry getOutline();
}
