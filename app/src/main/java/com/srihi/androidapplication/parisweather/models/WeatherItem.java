package com.srihi.androidapplication.parisweather.models;

/**
 * Created by Hamza Srihi on 05/10/2016.
 */


/*

{
        "id": 800,
        "main": "Clear",
        "description": "clear sky",
        "icon": "01d"
        }

*/
public class WeatherItem {

    int id;
    String main;
    String description;
    String icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
