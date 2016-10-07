package com.srihi.androidapplication.parisweather.models;

/**
 * Created by Hamza Srihi on 05/10/2016.
 */
/*
"temp": {
        "day": 12.91,
        "min": 7.04,
        "max": 12.91,
        "night": 7.04,
        "eve": 10.23,
        "morn": 12.91
        }*/

public class Temp {
    double day;
    double min;
    double max;
    double night;
    double eve;
    double morn;

    public double getDay() {
        return day;
    }

    public void setDay(double day) {
        this.day = day;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getNight() {
        return night;
    }

    public void setNight(double night) {
        this.night = night;
    }

    public double getEve() {
        return eve;
    }

    public void setEve(double eve) {
        this.eve = eve;
    }

    public double getMorn() {
        return morn;
    }

    public void setMorn(double morn) {
        this.morn = morn;
    }
}
