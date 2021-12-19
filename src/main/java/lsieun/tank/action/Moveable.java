package lsieun.tank.action;

import lsieun.tank.ancillary.Speed;

public interface Moveable {
    Speed getSpeed();

    void move();
}
