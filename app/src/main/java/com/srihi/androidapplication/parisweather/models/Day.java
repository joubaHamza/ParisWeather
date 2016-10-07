package com.srihi.androidapplication.parisweather.models;

import java.util.ArrayList;

/**
 * Created by Hamza Srihi on 05/10/2016.
 */

/*
{
        "dt": 1475665200,
        "temp": {
        "day": 12.91,
        "min": 7.04,
        "max": 12.91,
        "night": 7.04,
        "eve": 10.23,
        "morn": 12.91
        },
        "pressure": 1025.36,
        "humidity": 47,
        "weather": [
        {
        "id": 800,
        "main": "Clear",
        "description": "clear sky",
        "icon": "01d"
        }
        ],
        "speed": 8.27,
        "deg": 85,
        "clouds": 0
        }*/


public class Day {


    long dt;
    Temp temp;
    double pressure;
    int humidity;
    ArrayList<WeatherItem> weather;
    double speed;
    int deg;
    int clouds;

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public ArrayList<WeatherItem> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<WeatherItem> weather) {
        this.weather = weather;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }
}
