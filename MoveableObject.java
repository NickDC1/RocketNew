import java.awt.Rectangle;

public interface MoveableObject {
    void move(int dx, int dy);
    Rectangle getBounds();
}
