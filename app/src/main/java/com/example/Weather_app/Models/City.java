package com.example.Weather_app.Models;

public class City {
    Coord coord;
    String country;
    int id;
    String name;

    @Override
    public String toString() {
        return "City{" +
                "coord=" + coord +
                ", country='" + country + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City() {
    }

    public City(Coord coord, String country, int id, String name) {
        this.coord = coord;
        this.country = country;
        this.id = id;
        this.name = name;
    }
}
