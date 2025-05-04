package beans;

import model.Attempt;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserRequest implements Serializable {
    private int x;
    private double y;
    private double r = 1;


    public void setService(Service service) {
        this.service = service;
    }

    private Service service;

    public UserRequest() {}

    public int getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void submit() {
        Attempt attempt = new Attempt(x, y, r, LocalDateTime.now());
        this.service.process(attempt);
    }
}

