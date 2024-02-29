import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;


public class Rocket implements MoveableObject {
    private int x, y;
    private final int width = 30;
    private final int height = 50;

    public Rocket(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void move(int dx, int dy) {
        // Add boundary checks here if necessary
        x += dx;
        y += dy;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
