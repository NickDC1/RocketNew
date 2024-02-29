import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

public class Laser implements MoveableObject {
    private int x, y;
    private static final int LASER_WIDTH = 5;
    private static final int LASER_HEIGHT = 10;

    public Laser(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void move(int dx, int dy) {
        // Lasers typically move straight up, so dy is expected to be negative
        x += dx;
        y += dy;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, LASER_WIDTH, LASER_HEIGHT);
    }
    public int getY() {
        return y; // Return the y-coordinate of the laser
    }


    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, LASER_WIDTH, LASER_HEIGHT);
    }
    public static int getLaserHeight() {
        return LASER_HEIGHT;
    }
}
