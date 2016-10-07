package com.srihi.androidapplication.parisweather.models;

import java.util.ArrayList;

/**
 * Created by Hamza Srihi on 05/10/2016.
 */
public class JsonResponse {

    City city;
    String code;
    double message;
    int cnt;
    ArrayList<Day> list;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public ArrayList<Day> getList() {
        return list;
    }

    public void setList(ArrayList<Day> list) {
        this.list = list;
    }
}
