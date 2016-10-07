package com.srihi.androidapplication.parisweather.network.interfaces;

import com.srihi.androidapplication.parisweather.models.JsonResponse;
import com.srihi.androidapplication.parisweather.network.ParamsWebService;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Hamza Srihi on 06/10/2016.
 */
public interface IRequests {

    // q=Paris&units=metric&cnt=5&APPID=f2b04eb9dcd7d81ef872fae12384353d
    @GET(ParamsWebService.HOME_CALL)
    JsonResponse getHomeResponse(@Query("q") String country, @Query("units") String units, @Query("cnt") int count, @Query("APPID") String appid);

    //id=2988507&APPID=f2b04eb9dcd7d81ef872fae12384353d

    @GET(ParamsWebService.HOME_CALL)
    JsonResponse getDetailRequest(@Query("id") long id, @Query("APPID") String appId);
}
