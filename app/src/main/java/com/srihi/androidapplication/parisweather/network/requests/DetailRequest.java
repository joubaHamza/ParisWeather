package com.srihi.androidapplication.parisweather.network.requests;

import android.content.Context;

import com.srihi.androidapplication.parisweather.models.JsonResponse;
import com.srihi.androidapplication.parisweather.network.ParamsWebService;
import com.srihi.androidapplication.parisweather.network.interfaces.AbstractRetrofitSpiceRequest;
import com.srihi.androidapplication.parisweather.network.interfaces.IRequests;

/**
 * Created by Hamza Srihi on 06/10/2016.
 */
public class DetailRequest extends AbstractRetrofitSpiceRequest<JsonResponse, IRequests> {
    String id_Cache = "DetailRequest";
    long cityId;


    public DetailRequest(Context context, long cityId) {
        super(JsonResponse.class, IRequests.class);
        this.cityId = cityId;

        id_Cache += ParamsWebService.API_KEY;
    }

    @Override
    public JsonResponse loadDataFromNetwork() throws Exception {

        return getService().getDetailRequest(cityId, ParamsWebService.API_KEY);
    }

    public String generateCacheKey() {
        return id_Cache;
    }
}
