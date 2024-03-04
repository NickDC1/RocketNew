import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

public class Asteroid implements MoveableObject {
    private int x, y;
    private double speed;
    private AsteroidSize size;

    public Asteroid(int x, int y, AsteroidSize size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed = size.getSpeed();
    }

    @Override
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void draw(Graphics g) {
        int diameter = size.getSizeFactor() * 10; // Example scaling
        g.setColor(Color.GRAY);
        g.fillOval(x, y, diameter, diameter);
    }

    @Override
    public Rectangle getBounds() {
        int diameter = size.getSizeFactor() * 10;
        return new Rectangle(x, y, diameter, diameter);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
    public AsteroidSize getSize() {
        return size;
    }
}
