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
        // Since asteroids typically move down the screen,
        // dx will often be 0 and dy will be the speed at which the asteroid moves down.
        x += dx;
        y += dy; // This could use the speed variable directly or use the dy parameter.
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
