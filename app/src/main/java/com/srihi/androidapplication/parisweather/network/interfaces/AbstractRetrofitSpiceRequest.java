package com.srihi.androidapplication.parisweather.network.interfaces;

import com.octo.android.robospice.retry.DefaultRetryPolicy;
import com.proxymit.robospice.retrofit.request.RetrofitSpiceRequest;

/**
 * Created by Hamza Srihi on 05/10/2016.
 */
public class AbstractRetrofitSpiceRequest <T, R> extends RetrofitSpiceRequest<T, R> implements
        IRetryPolicy {

    public final static int RETRY_COUNT = 1;

    public AbstractRetrofitSpiceRequest(Class<T> clazz, Class<R> interfaceClass) {
        super(clazz, interfaceClass);

        if (editPolicy()) {
            setRetryPolicy(new DefaultRetryPolicy(RETRY_COUNT, 0, RETRY_COUNT));
        }
    }

    @Override
    public T loadDataFromNetwork() throws Exception {
        return null;
    }

    @Override
    public boolean editPolicy() {
        return true;
    }

    public String generateCacheKey() {
        return "";
    }
}
