package weather;

public class WeatherReading {
    private final int temperature; // celsius
    private final int dewPoint;    // celsius
    private final int windSpeed;   // mph
    private final int totalRain;   // mm

    public WeatherReading(int temperature, int dewPoint, int windSpeed, int totalRain) {
        if (dewPoint > temperature) {
            throw new IllegalArgumentException("Dew point cannot exceed air temperature.");
        }
        if (windSpeed < 0) {
            throw new IllegalArgumentException("Wind speed must be non-negative.");
        }
        if (totalRain < 0) {
            throw new IllegalArgumentException("Total rain must be non-negative.");
        }
        this.temperature = temperature;
        this.dewPoint = dewPoint;
        this.windSpeed = windSpeed;
        this.totalRain = totalRain;
    }

    public int getTemperature() { return temperature; }
    public int getDewPoint()    { return dewPoint; }
    public int getWindSpeed()   { return windSpeed; }
    public int getTotalRain()   { return totalRain; }

    // RH = clamp(100 - 5*(T - D), 0..100)
    public int getRelativeHumidity() {
        int rh = 100 - 5 * (temperature - dewPoint);
        if (rh < 0) rh = 0;
        if (rh > 100) rh = 100;
        return rh;
    }

    public double getHeatIndex() {
        double T = temperature;
        double R = getRelativeHumidity();
        return -8.78469475556
                + 1.61139411 * T
                + 2.33854883889 * R
                - 0.14611605 * T * R
                - 0.012308094 * T * T
                - 0.0164248277778 * R * R
                + 0.002211732 * T * T * R
                + 0.00072546 * T * R * R
                - 0.000003582 * T * T * R * R;
    }

    public double getWindChill() {
        double Tf = temperature * 9.0 / 5.0 + 32.0; // °F
        double v  = windSpeed; // mph
        if (v == 0) {
            // Keep return units consistent: °C
            return temperature;
        }
        double WCf = 35.74 + 0.6215 * Tf
                - 35.75 * Math.pow(v, 0.16)
                + 0.4275 * Tf * Math.pow(v, 0.16); // °F
        return WCf;
    }


    @Override
    public String toString() {
        return String.format("Reading: T = %d, D = %d, v = %d, rain = %d",
                temperature, dewPoint, windSpeed, totalRain);
    }
}
