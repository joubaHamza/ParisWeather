package com.srihi.androidapplication.parisweather.network.requests;

import android.content.Context;

import com.srihi.androidapplication.parisweather.models.JsonResponse;
import com.srihi.androidapplication.parisweather.network.ParamsWebService;
import com.srihi.androidapplication.parisweather.network.interfaces.AbstractRetrofitSpiceRequest;
import com.srihi.androidapplication.parisweather.network.interfaces.IRequests;

/**
 * Created by Hamza Srihi on 06/10/2016.
 */
public class HomeRequest extends AbstractRetrofitSpiceRequest<JsonResponse, IRequests> {
    String id_Cache = "HomeRequest";
    String country;
    String units;
    int count;

    /**
     * get home objects
     *
     * @param country
     * @param units
     * @param count
     */

    public HomeRequest(Context context, String country, String units, int count) {
        super(JsonResponse.class, IRequests.class);
        this.country = country;
        this.units = units;
        this.count = count;

        id_Cache += ParamsWebService.API_KEY;
    }

    @Override
    public JsonResponse loadDataFromNetwork() throws Exception {


        return getService().getHomeResponse(country, units, count, ParamsWebService.API_KEY);
    }

    public String generateCacheKey() {
        return id_Cache;
    }
}
