public class SDE extends AbstractEngineer {

    private final int linesOfCode;
    private final int numDesignDoc;

    public SDE(String name, double baseSalary, int linesOfCode, int numDesignDoc) {
        super(name, baseSalary);
        this.linesOfCode = linesOfCode;
        this.numDesignDoc = numDesignDoc;
    }

    @Override
    protected double computeBonusRatio(Rating rating) {
        double r = linesOfCode / 80.0 + numDesignDoc / 5.0;
        if (rating == Rating.EXCEED_EXPECTATION) {
            r = r * 1.2;
        } else if (rating == Rating.SUPERB) {
            r = r * 1.7;
        }
        return r;
    }
}
