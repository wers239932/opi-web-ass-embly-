package model;

public interface Checker<X extends Comparable<X>,
        Y extends Comparable<Y>,
        R extends Comparable<R>> {
    boolean check(X x, Y y, R r);
}
