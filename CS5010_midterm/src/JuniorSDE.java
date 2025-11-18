public class JuniorSDE extends AbstractEngineer {

    private final int linesOfCode;

    public JuniorSDE(String name, double baseSalary, int linesOfCode) {
        super(name, baseSalary);
        this.linesOfCode = linesOfCode;
    }

    @Override
    protected double computeBonusRatio(Rating rating) {
        double r = linesOfCode / 100.0;
        if (rating == Rating.SUPERB) {
            r = r * 2.0;
        }
        return r;
    }
}
