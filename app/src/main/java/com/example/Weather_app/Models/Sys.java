package com.example.Weather_app.Models;

public class Sys{
    private int type;
    private int id;
    private double sunrise;
    private double sunset;
    private String country;


    @Override
    public String toString() {
        return "Sys{" +
                "type=" + type +
                ", id=" + id +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                ", country='" + country + '\'' +
                '}';
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public double getSunrise() {
        return sunrise;
    }

    public void setSunrise(double sunrise) {
        this.sunrise = sunrise;
    }

    public double getSunset() {
        return sunset;
    }

    public void setSunset(double sunset) {
        this.sunset = sunset;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Sys(int type, int id, double sunrise, double sunset, String country) {
        this.type = type;
        this.id = id;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.country = country;
    }
}
