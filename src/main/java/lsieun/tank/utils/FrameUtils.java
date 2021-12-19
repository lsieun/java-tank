package lsieun.tank.utils;

import javax.swing.*;
import java.awt.*;

public class FrameUtils {
    public static void initFrame(JFrame frame, int width, int height) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension d = toolkit.getScreenSize();

        int screenX = (int) d.getWidth();
        int screenY = (int) d.getHeight();

        int leftTopX = (screenX - width) / 2;
        int leftTopY = (screenY - height) / 2;

        frame.setTitle("TankWar");
        frame.setResizable(false);
        frame.setBackground(Color.BLACK);
        frame.setBounds(leftTopX, leftTopY, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void quit(JFrame frame) {
        frame.setVisible(false);
        frame.dispose();
    }

}
