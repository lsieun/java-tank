package lsieun.tank;

import lsieun.tank.ancillary.Direction;
import lsieun.tank.cst.GameConstants;
import lsieun.tank.ancillary.Group;
import lsieun.tank.geometry.Point;
import lsieun.tank.logic.Explode;
import lsieun.tank.logic.Missile;
import lsieun.tank.logic.Tank;
import lsieun.tank.logic.Weapon;
import lsieun.tank.simple.SimpleTank;
import lsieun.tank.simple.SimpleWeapon;
import lsieun.tank.ui.SimpleExplodeUI;
import lsieun.tank.ui.SimpleMissileUI;
import lsieun.tank.ui.SimpleTankUI;
import lsieun.tank.utils.FrameUtils;
import lsieun.tank.utils.GameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TankSimpleClient extends JFrame {

    private Tank myTank;
    private List<Tank> enemyTanks;
    private List<Missile> missiles;
    private List<Explode> explodes;
    private Weapon weapon;
    private Direction dir = null;
    private boolean bL, bU, bR, bD, bFire;
    private Image offScreenImage = null;
    private final Random random = new Random();

    public TankSimpleClient() {
        this.init();
    }

    private void init() {
        Point center = new Point(400, 500);
        this.myTank = new SimpleTank("我的坦克", center, Group.GOOD, Direction.NORTH);
        this.myTank.setHealth(100000000);
        this.weapon = new SimpleWeapon();
        this.myTank.equip(weapon);

        this.enemyTanks = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            Point p = new Point(80 * i + 40, 100);
            SimpleTank tank = new SimpleTank("敌人坦克" + (i + 1), p, Group.BAD, Direction.SOUTH);
            Weapon w = new SimpleWeapon();
            tank.equip(w);
            this.enemyTanks.add(tank);
        }

        this.missiles = new LinkedList<>();
        this.explodes = new LinkedList<>();
    }

    @Override
    public void paint(Graphics g) {
        // 在重绘函数中实现双缓冲机制
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GameConstants.GAME_WINDOW_WIDTH, GameConstants.GAME_WINDOW_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();

        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.GREEN);
        gOffScreen.fillRect(0, 0, GameConstants.GAME_WINDOW_WIDTH, GameConstants.GAME_WINDOW_HEIGHT);
        gOffScreen.setColor(c);
        super.paint(gOffScreen);


        for (Tank tank : this.enemyTanks) {
            SimpleTankUI.drawTank(gOffScreen, tank);
        }

        for (Missile m : this.missiles) {
            SimpleMissileUI.drawMissile(gOffScreen, m);
        }

        SimpleTankUI.drawTank(gOffScreen, this.myTank);

        for (Explode e : this.explodes) {
            SimpleExplodeUI.drawExplode(gOffScreen, e);
        }

        gOffScreen.drawString("missiles count:" + this.missiles.size(), 10, 50);
        gOffScreen.drawString("explodes count:" + this.explodes.size(), 10, 70);
        gOffScreen.drawString("tanks    count:" + this.enemyTanks.size(), 10, 90);

        g.drawImage(offScreenImage, 0, 0, null);
    }


    public void launchFrame() {
        FrameUtils.initFrame(this, GameConstants.GAME_WINDOW_WIDTH, GameConstants.GAME_WINDOW_HEIGHT);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                TankSimpleClient.this.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                TankSimpleClient.this.keyReleased(e);
            }
        });

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    TankSimpleClient.this.update();
                    try {
                        Thread.sleep(80);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    private void update() {
            for (Tank tank : this.enemyTanks) {
                double value = random.nextDouble();
                if (value > 0.95) {
                    Missile m = tank.fire();
                    this.missiles.add(m);
                }

                if (value < 0.9) continue;

                int dirRandomValue = random.nextInt(8);
                Direction enemyDirection;
                switch (dirRandomValue) {
                    case 0:
                        enemyDirection = Direction.NORTH;
                        break;
                    case 1:
                        enemyDirection = Direction.NORTH_EAST;
                        break;
                    case 2:
                        enemyDirection = Direction.EAST;
                        break;
                    case 3:
                        enemyDirection = Direction.SOUTH_EAST;
                        break;
                    case 4:
                        enemyDirection = Direction.SOUTH;
                        break;
                    case 5:
                        enemyDirection = Direction.SOUTH_WEST;
                        break;
                    case 6:
                        enemyDirection = Direction.WEST;
                        break;
                    case 7:
                        enemyDirection = Direction.NORTH_WEST;
                        break;
                    default:
                        enemyDirection = Direction.SOUTH;
                        break;
                }
                tank.setDirection(enemyDirection);

                Point center = tank.getCenter();
                int x = center.getX();
                int y = center.getY();

                if (x < GameConstants.GAME_WORLD_LEFT + 30) x = GameConstants.GAME_WORLD_LEFT + 30;
                if (x > GameConstants.GAME_WORLD_RIGHT - 30) x = GameConstants.GAME_WORLD_RIGHT - 30;
                if (y < GameConstants.GAME_WORLD_TOP + 70) y = GameConstants.GAME_WORLD_TOP + 70;
                if (y > GameConstants.GAME_WORLD_BOTTOM - 30) y = GameConstants.GAME_WORLD_BOTTOM - 30;
                center.setX(x);
                center.setY(y);

                tank.move();
            }


            // Missile
            for (int i = this.missiles.size() - 2; i >= 0; i--) {
                Missile m1 = this.missiles.get(i);

                for (int j = i + 1; j < this.missiles.size(); j++) {
                    Missile m2 = this.missiles.get(j);
                    GameUtils.hit(m1, m2);
                }
            }

            // Enemy Tank
            for (int i = this.missiles.size() - 1; i >= 0; i--) {
                Missile m = this.missiles.get(i);
                for (int j = this.enemyTanks.size() - 1; j >= 0; j--) {
                    Tank t = this.enemyTanks.get(j);
                    GameUtils.hit(m, t);

                    if (t.getHealth() <= 0) {
                        this.enemyTanks.remove(j);
                    }
                }
            }

            // My Tank
            for (int i = this.missiles.size() - 1; i >= 0; i--) {
                Missile m = this.missiles.get(i);
                GameUtils.hit(m, this.myTank);

                if (this.myTank.getHealth() <= 0) {
                    System.out.println("GAME OVER");
                }
            }

            // Wall
            for (int i = this.missiles.size() - 1; i >= 0; i--) {
                Missile m = this.missiles.get(i);
                GameUtils.hitWall(m);
            }


            for (int i = this.missiles.size() - 1; i >= 0; i--) {
                Missile m = this.missiles.get(i);
                Point center = m.getCenter();
                int x = center.getX();
                int y = center.getY();

                if (m.getHealth() <= 0) {
                    this.missiles.remove(i);

                    Explode explode = new Explode(m.getCenter());
                    this.explodes.add(explode);
                    continue;
                }

                if (x <= GameConstants.GAME_WORLD_LEFT
                        || x >= GameConstants.GAME_WORLD_RIGHT
                        || y <= GameConstants.GAME_WORLD_TOP
                        || y >= GameConstants.GAME_WORLD_BOTTOM) {
                    this.missiles.remove(i);
                    continue;
                }


                m.move();
            }


            // Explode
            for (int i = this.explodes.size() - 1; i >= 0; i--) {
                Explode explode = this.explodes.get(i);
                int health = explode.getHealth();
                if (health <= 0) {
                    this.explodes.remove(i);
                }
            }

            this.repaint();
    }

    public static void main(String[] args) {
        TankSimpleClient client = new TankSimpleClient();
        client.launchFrame();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                bL = true;
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                bU = true;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                bR = true;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                bD = true;
                break;
        }
        locateDirection();
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_CONTROL:
            case KeyEvent.VK_SPACE:
                Missile m = this.myTank.fire();
                this.missiles.add(m);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                bL = false;
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                bU = false;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                bR = false;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                bD = false;
                break;
        }
        locateDirection();
    }

    private void locateDirection() {
        if (bL && !bU && !bR && !bD) dir = Direction.WEST;
        else if (bL && bU && !bR && !bD) dir = Direction.NORTH_WEST;
        else if (!bL && bU && !bR && !bD) dir = Direction.NORTH;
        else if (!bL && bU && bR && !bD) dir = Direction.NORTH_EAST;
        else if (!bL && !bU && bR && !bD) dir = Direction.EAST;
        else if (!bL && !bU && bR && bD) dir = Direction.SOUTH_EAST;
        else if (!bL && !bU && !bR && bD) dir = Direction.SOUTH;
        else if (bL && !bU && !bR && bD) dir = Direction.SOUTH_WEST;
        else if (!bL && !bU && !bR && !bD) dir = null;

        if (dir != null) {
            this.myTank.setDirection(dir);
            this.myTank.move();
            Point center = this.myTank.getCenter();
            int x = center.getX();
            int y = center.getY();

            if (x < GameConstants.GAME_WORLD_LEFT + 30) x = GameConstants.GAME_WORLD_LEFT + 30;
            if (x > GameConstants.GAME_WORLD_RIGHT - 30) x = GameConstants.GAME_WORLD_RIGHT - 30;
            if (y < GameConstants.GAME_WORLD_TOP + 70) y = GameConstants.GAME_WORLD_TOP + 70;
            if (y > GameConstants.GAME_WORLD_BOTTOM - 30) y = GameConstants.GAME_WORLD_BOTTOM - 30;
            center.setX(x);
            center.setY(y);
        }
    }

}
