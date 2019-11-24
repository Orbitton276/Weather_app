package com.example.Weather_app.Models;

import java.util.List;

public class OpenWeatherMap {
    private Coord coord;
    private List<Weather> weather;
    private String base;
    private String name;

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    private String dt_txt;
    private Main main;
    private Wind wind;
    private Sys sys;

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    private Clouds clouds;
    private int id;

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    private int visibility;

    @Override
    public String toString() {
        return "OpenWeatherMap{" +
                "coord=" + coord +
                ", weather=" + weather +
                ", base='" + base + '\'' +
                ", name='" + name + '\'' +
                ", main=" + main +
                ", wind=" + wind +
                ", clouds=" + clouds +
                ", id=" + id +
                ", dt=" + dt +
                ", cod=" + cod +
                '}';
    }

    private int dt;
    private int cod;

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public OpenWeatherMap() {

    }

    public OpenWeatherMap(Coord coord,int visibility, Sys sys,List<Weather> weatherList, String base, String name, Main main, Wind wind, Clouds clouds, int id, int dt, int cod) {
        this.coord = coord;
        this.weather = weatherList;
        this.base = base;
        this.name = name;
        this.main = main;
        this.sys = sys;
        this.wind = wind;
        this.clouds = clouds;
        this.id = id;
        this.visibility=visibility;
        this.dt = dt;
        this.cod = cod;
    }
}
