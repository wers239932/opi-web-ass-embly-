package beans;

import model.*;


public class Service {
    private final Checker<Integer, Double, Double> checker;



    private Results results;

    public Service() {
        this.checker = new CheckerImpl();
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public void process(Attempt attempt) {
        long startTime = System.nanoTime();
        attempt.setResult(this.checker.check(attempt.getX(), attempt.getY(), attempt.getR()));
        attempt.setWorkingTime(System.nanoTime() - startTime);
        this.results.addResult(attempt);

    }
}
