package lsieun.tank.ui;

import lsieun.tank.logic.Missile;

import java.awt.*;

public class SimpleMissileUI {

    public static void drawMissile(Graphics g, Missile missile) {
        int x = missile.getCenter().getX();
        int y = missile.getCenter().getY();
        int radius = Missile.RADIUS;

        Color c = g.getColor();
        g.setColor(Color.MAGENTA);
        g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        g.setColor(c);
    }
}
