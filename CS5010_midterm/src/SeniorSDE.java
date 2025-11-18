public class SeniorSDE extends AbstractEngineer {

    private final int numOfReports;

    public SeniorSDE(String name, double baseSalary, int numOfReports) {
        super(name, baseSalary);
        this.numOfReports = numOfReports;
    }

    @Override
    protected double computeBonusRatio(Rating rating) {
        // rating doesn't change the formula for seniors
        return numOfReports / 5.0;
    }
}
