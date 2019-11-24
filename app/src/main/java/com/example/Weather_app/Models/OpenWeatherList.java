package com.example.Weather_app.Models;

public class OpenWeatherList extends OpenWeatherMap {

    private int dt;
    private String dt_txt;

    public OpenWeatherList() {
    }

    @Override
    public int getDt() {
        return dt;
    }

    @Override
    public void setDt(int dt) {
        this.dt = dt;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public OpenWeatherList(int dt, String dt_txt) {
        this.dt = dt;
        this.dt_txt = dt_txt;
    }
}
