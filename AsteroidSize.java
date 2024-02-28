public enum AsteroidSize {
    SMALL(1, 2), // Example parameters: sizeFactor, speed
    MEDIUM(2, 1.5),
    LARGE(3, 1);

    private final int sizeFactor;
    private final double speed;

    AsteroidSize(int sizeFactor, double speed) {
        this.sizeFactor = sizeFactor;
        this.speed = speed;
    }

    public int getSizeFactor() {
        return sizeFactor;
    }

    public double getSpeed() {
        return speed;
    }
}
