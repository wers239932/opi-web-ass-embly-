package model;

public class CheckerImpl implements Checker<Integer, Double, Double> {
    @Override
    public boolean check(Integer x, Double y, Double r) {
        if(x <= 0 && y <= 0) return x * x + y * y  <= r*r/4;
        else if(x < 0 && y >= 0) return y <= r/2 && -x <= r;
        else if(x >= 0 && y >= 0) return (x) / (r/2 - 0) <= (y - r) / (0 - r);
        return false;
    }
}
