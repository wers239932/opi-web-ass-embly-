package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Attempt {
    private int id;
    private int x;
    private double y;
    private double r;
    private long workingTime;
    private LocalDateTime sentAt;
    private boolean result;

    public Attempt(int x, double y, double r, LocalDateTime sentAt) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.sentAt = sentAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public long getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(long workingTime) {
        this.workingTime = workingTime;
    }

    public int getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public boolean getResult() {
        return result;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public String getFormattedSentAt() {
        return sentAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"));
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

    public void setResult(boolean result) {
        this.result = result;
    }


    @Override
    public String toString() {
        return "{" +
                "x:" + x +
                ", y:" + y +
                ", r:" + r +
                ", result:" + result +
                ", sentAt:" + '"' + sentAt + '"' +
                '}';
    }
}
