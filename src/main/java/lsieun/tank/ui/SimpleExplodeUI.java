package lsieun.tank.ui;

import lsieun.tank.geometry.Point;
import lsieun.tank.logic.Explode;

import java.awt.*;

public class SimpleExplodeUI {

    public static void drawExplode(Graphics g, Explode e) {
        Point center = e.getCenter();
        int radius = e.getRadius();

        int x = center.getX();
        int y = center.getY();

        Color c = g.getColor();
        g.setColor(Color.ORANGE);
        g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        g.setColor(c);
    }
}
