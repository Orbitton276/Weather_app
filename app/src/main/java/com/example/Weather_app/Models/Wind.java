package com.example.Weather_app.Models;

public class Wind{
    private double speed;
    private double degree;

    public Wind(double speed, double degree) {
        this.speed = speed;
        this.degree = degree;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Wind{" +
                "speed=" + speed +
                ", degree=" + degree +
                '}';
    }

    public double getDegree() {
        return degree;
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }
}