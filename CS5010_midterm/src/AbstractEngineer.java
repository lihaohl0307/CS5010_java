public abstract class AbstractEngineer implements Engineer {

    private final String name;
    protected final double baseSalary;
    private double bonus;

    protected AbstractEngineer(String name, double baseSalary) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        this.name = name;
        this.baseSalary = baseSalary;
        this.bonus = 0.0;
    }

    @Override
    public String getName() {
        return name;
    }

    // Template method: shared base bonus, subclass-specific ratio
    @Override
    public final void setBonus(Rating rating) {
        double baseBonus = this.baseSalary;
        baseBonus += pullDepartmentProfit();
        baseBonus += pullNASDQIndex();
        baseBonus += pullManagerMood();
        baseBonus += pullCPI();
        // potentially more...

        double ratio = computeBonusRatio(rating);
        this.bonus = baseBonus * ratio;
    }

    // Each concrete engineer defines how to compute its ratio
    protected abstract double computeBonusRatio(Rating rating);

    @Override
    public double getBonus() {
        return bonus;
    }

    @Override
    public int compareTo(Engineer o) {
        if (o == null) {
            throw new NullPointerException("Engineer to compare cannot be null");
        }
        // order engineers alphabetically by name
        return this.getName().compareTo(o.getName());
    }

    @Override
    public String toString() {
        return name + " (bonus=" + bonus + ")";
    }
}
