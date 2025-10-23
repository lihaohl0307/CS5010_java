package transmission;

public interface Transmission {
    /**
     * Increase the car's speed by 2 units.
     * @return a new Transmission object with updated speed and gear
     */
    Transmission increaseSpeed();

    /**
     * Decrease the car's speed by 2 units.
     * @return a new Transmission object with updated speed and gear
     * @throws IllegalStateException if the resulting speed is invalid (below 0)
     */
    Transmission decreaseSpeed();

    /**
     * @return current speed and gears
     */
    int getSpeed();
    int getGear();
}
