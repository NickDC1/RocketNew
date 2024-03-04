import javax.swing.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Toolkit;


public class GamePanel extends JPanel {
    private Rocket rocket;
    private ArrayList<Asteroid> asteroids;
    private ArrayList<Laser> lasers;

    // Constants for the rocket movement distance
    private static final int ROCKET_MOVE_DISTANCE = 20;
    private static final int ASTEROID_SPAWN_FREQUENCY = 5000; // Milliseconds between asteroid spawns

    private int asteroidSpawnTimer = ASTEROID_SPAWN_FREQUENCY;
    private Timer gameTimer;
    private Image backgroundImage;

    // Initializes game components and loads resources.
    public GamePanel() {
        rocket = new Rocket(375, 550); // Starting position of the rocket
        asteroids = new ArrayList<>();
        lasers = new ArrayList<>();

        backgroundImage = Toolkit.getDefaultToolkit().createImage("C:\\Users\\Nickc\\OneDrive\\Desktop\\RocketNew\\Space.jpg");

        setupKeyBindings();
        addMouseListener(new ShootingMouseListener());


        // Start the game loop
        startAsteroidSpawningTimer();
        gameTimer = new Timer(50, e -> updateGame());
        gameTimer.start();
    }

    //Keybinds to move rocket
    private void setupKeyBindings() {
        // Shoot Laser
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "shootLaser");
        getActionMap().put("shootLaser", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shootLaser();
            }
        });

        // Move Up
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "MoveUp");
        getActionMap().put("MoveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rocket.move(0, -ROCKET_MOVE_DISTANCE);
                repaint();
            }
        });

        // Move Down
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "MoveDown");
        getActionMap().put("MoveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rocket.move(0, ROCKET_MOVE_DISTANCE);
                repaint();
            }
        });

        // Move Left
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "MoveLeft");
        getActionMap().put("MoveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rocket.move(-ROCKET_MOVE_DISTANCE, 0);
                repaint();
            }
        });

        // Move Right
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "MoveRight");
        getActionMap().put("MoveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rocket.move(ROCKET_MOVE_DISTANCE, 0);
                repaint();
            }
        });
    }

    // Creates and adds a new laser to the game based on the rocket's position.
    private void shootLaser() {
        lasers.add(new Laser(rocket.getX() + 15, rocket.getY())); // Center the laser on the rocket
        repaint();
    }


    private static final int LASER_SPEED = 50; // Define this constant at the class level

    // Updates game state including moving objects and checking for collisions.
    private void updateGame() {
        // Update the asteroidSpawnTimer
        asteroidSpawnTimer -= 100; // Assuming this method is called every 100ms, adjust as needed

        // Check if it's time to spawn a new asteroid
        if (asteroidSpawnTimer <= 0) {
            spawnAsteroid();
            asteroidSpawnTimer = ASTEROID_SPAWN_FREQUENCY; // Reset the timer
        }

        // Update positions of existing asteroids and lasers
        ArrayList<Asteroid> asteroidsToRemove = new ArrayList<>();
        ArrayList<Laser> lasersToRemove = new ArrayList<>();

        // Move asteroids downwards
        for (Asteroid asteroid : asteroids) {
            asteroid.move(0, 1); // Adjust movement speed as needed
            if (asteroid.getY() > getHeight()) { // Remove asteroid if it moves beyond the bottom
                asteroidsToRemove.add(asteroid);
            }
        }

        // Move lasers upwards
        for (Laser laser : lasers) {
            laser.move(0, -LASER_SPEED); // Use the LASER_SPEED constant
            if (laser.getY() + Laser.getLaserHeight() < 0) { // Remove laser if it moves beyond the top
                lasersToRemove.add(laser);
            }
        }

        // Check for collisions
        checkCollisions();

        // Remove any asteroids and lasers that have been marked for removal
        asteroids.removeAll(asteroidsToRemove);
        lasers.removeAll(lasersToRemove);

        // Repaint the panel to reflect the updated game state
        repaint();
    }

    // Initializes a timer to spawn asteroids at regular intervals.
    private void startAsteroidSpawningTimer() {
        Timer timer = new Timer(ASTEROID_SPAWN_FREQUENCY, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spawnAsteroid();
            }
        });
        timer.start();
    }

    // Adds a new asteroid at a random position at the top of the screen.
    private void spawnAsteroid() {
        int x = (int) (Math.random() * getWidth());
        AsteroidSize size = AsteroidSize.values()[(int) (Math.random() * AsteroidSize.values().length)];
        asteroids.add(new Asteroid(x, -50, size));
    }

    //draw the game state, including the background, rocket, asteroids, and lasers.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        rocket.draw(g);
        for (Asteroid asteroid : asteroids) {
            asteroid.draw(g);
        }
        for (Laser laser : lasers) {
            laser.draw(g);
        }
    }

    // Checks for collisions between lasers and asteroids and handles them.
    private void checkCollisions() {
        ArrayList<Asteroid> asteroidsToRemove = new ArrayList<>();
        ArrayList<Laser> lasersToRemove = new ArrayList<>();

        for (Laser laser : lasers) {
            for (Asteroid asteroid : asteroids) {
                if (laser.getBounds().intersects(asteroid.getBounds())) {
                    asteroidsToRemove.add(asteroid);
                    lasersToRemove.add(laser);
                }
            }

        }

        asteroids.removeAll(asteroidsToRemove);
        lasers.removeAll(lasersToRemove);
    }

    // Mouse listener to handle shooting lasers on mouse click.
    private class ShootingMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            shootLaser();
        }
    }
}
