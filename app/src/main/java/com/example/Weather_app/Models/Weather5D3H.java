package com.example.Weather_app.Models;

import java.util.List;

public class Weather5D3H {

    private List<OpenWeatherMap> list;
    private City city;

    public Weather5D3H() {
    }

    public List<OpenWeatherMap> getList() {
        return list;
    }

    public void setList(List<OpenWeatherMap> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Weather5D3H(List<OpenWeatherMap> list, City city) {
        this.list = list;
        this.city = city;
    }
}
