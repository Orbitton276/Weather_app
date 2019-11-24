package com.example.Weather_app.Models;

public class DateAndTime {

    private String month;
    private String day;
    private String year;
    private String hours;
    private String minutes;
    private String date;
    private String time;


    public DateAndTime() {
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }




    public DateAndTime(String DateTime) {
        //specified into format yyyy-mm-dd hh:mm:ss
        convertString(DateTime);

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void convertString(String dateAndtime) {
        String[] splitted = dateAndtime.split(" ");
        String[] date = splitted[0].split("-");
        year = date[0];
        month = date[1];
        day = date[2];
        String[] time = splitted[1].split(":");
        hours = time[0];
        minutes = time[1];

        this.date = year+"/"+month+"/"+day;
        this.time = hours+":"+minutes;

    }

}
