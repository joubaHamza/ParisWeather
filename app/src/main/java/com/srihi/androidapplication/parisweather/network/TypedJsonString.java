package com.srihi.androidapplication.parisweather.network;

import retrofit.mime.TypedString;

/**
 * Created by Hamza Srihi on 05/10/2016.
 */
public class TypedJsonString extends TypedString {

    public TypedJsonString(String body) {
        super(body);
    }

    @Override
    public String mimeType() {
        return "application/json";
    }
}
