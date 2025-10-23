package transmission;

public class AutomaticTransmission implements Transmission {
    private final int speed;
    private final int gear;
    private final int[] threshold;

    // public constructor
    public AutomaticTransmission(int t1, int t2, int t3, int t4, int t5) {
        if (t1 <= 0 || t2 <= t1 || t3 <= t2 || t4 <= t3 || t5 <= t4) {
            throw new IllegalArgumentException("Thresholds must be strictly increasing and positive.");
        }
        this.speed = 0;
        this.gear = 0;
        this.threshold = new int[]{t1, t2, t3, t4, t5};
    }

    // add a private constructor to determine speed
    private AutomaticTransmission(int speed, int[] threshold) {
        this.speed = speed;
        this.threshold = threshold;
        this.gear = determineGear(speed);
    }

    private int determineGear(int speed) {
        if (speed <= 0) return 0;
        if (speed < threshold[0]) return 1;
        if (speed < threshold[1]) return 2;
        if (speed < threshold[2]) return 3;
        if (speed < threshold[3]) return 4;
        if (speed < threshold[4]) return 5;
        return 6;
    }

    @Override
    public Transmission increaseSpeed() {
        return new AutomaticTransmission(this.speed+2, threshold);
    }

    @Override
    public Transmission decreaseSpeed() {
        if (this.speed -2 < 0) {
            throw new IllegalStateException("Speed cannot be negative.");
        }
        return new AutomaticTransmission(this.speed-2, threshold);
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public int getGear() {
        return this.gear;
    }

    @Override
    public String toString() {
        return String.format("Transmission (speed = %d, gear = %d)", speed, gear);
    }
}
