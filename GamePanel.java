import javax.swing.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private Rocket rocket;
    private ArrayList<Asteroid> asteroids;
    private ArrayList<Laser> lasers;

    // Constants for the rocket movement distance
    private static final int ROCKET_MOVE_DISTANCE = 20;
    private static final int ASTEROID_SPAWN_FREQUENCY = 20000; // Milliseconds between asteroid spawns

    private int asteroidSpawnTimer = ASTEROID_SPAWN_FREQUENCY;
    private Timer gameTimer;

    public GamePanel() {
        rocket = new Rocket(375, 550); // Starting position of the rocket
        asteroids = new ArrayList<>();
        lasers = new ArrayList<>();


        setupKeyBindings();
        addMouseListener(new ShootingMouseListener());


        // Start the game loop
        startGameLoop();
        startAsteroidSpawningTimer();
        gameTimer = new Timer(50, e -> updateGame());
        gameTimer.start();
    }

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

    private void shootLaser() {
        lasers.add(new Laser(rocket.getX() + 15, rocket.getY())); // Center the laser on the rocket
        repaint();
    }

    private void startGameLoop() {
        Timer timer = new Timer(100, e -> updateGame());
        timer.start();
    }

    private static final int LASER_SPEED = 10; // Define this constant at the class level

    private void updateGame() {
        // Update the asteroidSpawnTimer
        asteroidSpawnTimer -= 100;

        // Check if it's time to spawn a new asteroid
        if (asteroidSpawnTimer <= 0) {
            spawnAsteroid();
            asteroidSpawnTimer = ASTEROID_SPAWN_FREQUENCY; // Reset the timer
        }

        // Update positions of existing asteroids and lasers
        asteroids.forEach(asteroid -> asteroid.move(0, 1)); // Move asteroids downwards
        lasers.forEach(laser -> laser.move(0, -1)); // Move lasers upwards

        // Check for collisions
        checkCollisions();
    }
    private void startAsteroidSpawningTimer() {
        Timer timer = new Timer(ASTEROID_SPAWN_FREQUENCY, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spawnAsteroid();
            }
        });
        timer.start();
    }
    private void spawnAsteroid() {
        int x = (int) (Math.random() * getWidth());
        AsteroidSize size = AsteroidSize.values()[(int) (Math.random() * AsteroidSize.values().length)];
        asteroids.add(new Asteroid(x, -50, size));
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        rocket.draw(g);
        for (Asteroid asteroid : asteroids) {
            asteroid.draw(g);
        }
        for (Laser laser : lasers) {
            laser.draw(g);
        }
    }

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

    public void addAsteroid(Asteroid asteroid) {
        asteroids.add(asteroid);
    }

    private class ShootingMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            shootLaser();
        }
    }
}
