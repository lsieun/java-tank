package lsieun.tank.logic;

import lsieun.tank.ancillary.Group;
import lsieun.tank.geometry.Point;
import lsieun.tank.geometry.Rectangle;

/**
 * 游戏中的块状元素：
 * 例如，墙体、石头（材质）
 */
public abstract class Block extends LivingElement {
    public static final int DEFAULT_HEALTH = 100;
    private Rectangle outline;

    public Block(String name, Point center, Group group) {
        super(name, center, group, DEFAULT_HEALTH);

        this.health = DEFAULT_HEALTH;
    }
}
