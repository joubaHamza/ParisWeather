package com.srihi.androidapplication.parisweather.network;

import com.proxymit.robospice.retrofit.RetrofitGsonSpiceService;
import com.srihi.androidapplication.parisweather.network.interfaces.IRequests;

import retrofit.converter.Converter;

/**
 * Created by Hamza Srihi on 05/10/2016.
 */
public class WebServiceRetrofitSpiceService extends RetrofitGsonSpiceService {
    @Override
    protected String getServerUrl() {
        return ParamsWebService.SERVER_NAME;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(IRequests.class);
    }

    @Override
    protected Converter createConverter() {
        return super.createConverter();
    }
}
