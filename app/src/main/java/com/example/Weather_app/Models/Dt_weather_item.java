package com.example.Weather_app.Models;

public class Dt_weather_item {

    private String time;
    private String location;
    private String state;
    private String description;
    private String iconUrl;
    private double temp;
    private double humidity;
    private double windSpeed;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Dt_weather_item(String time, String location, String state, String description, String iconUrl, double temp, double humidity, double windSpeed) {
        this.time = time;
        this.location = location;
        this.state = state;
        this.description = description;
        this.iconUrl = iconUrl;
        this.temp = temp;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }
}
