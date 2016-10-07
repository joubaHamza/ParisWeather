package com.proxymit.robospice.retrofit;

import android.util.Log;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.request.CachedSpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proxymit.robospice.retrofit.request.RetrofitSpiceRequest;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.converter.Converter;

public abstract class RetrofitSpiceService extends SpiceService {
    protected List<Class<?>> retrofitInterfaceList = new ArrayList<>();
    private Map<Class<?>, Object> retrofitInterfaceToServiceMap = new HashMap<>();
    private RestAdapter.Builder builder;
    private RestAdapter restAdapter;
    private Converter mConverter;
    private OkHttpClient okHttpClient;

    protected abstract String getServerUrl();

    protected abstract Converter createConverter();

    @Override
    public void onCreate() {
        super.onCreate();

        builder = createRestAdapterBuilder();
        // TODO disable LOG
        roboguice.util.temp.Ln.getConfig().setLoggingLevel(Log.VERBOSE);
        builder.setLogLevel(RestAdapter.LogLevel.FULL);
        restAdapter = builder.build();
    }

    protected RestAdapter.Builder createRestAdapterBuilder() {
        okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(60000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(60000, TimeUnit.MILLISECONDS);
        okHttpClient.setWriteTimeout(60000, TimeUnit.MILLISECONDS);
        RestAdapter.Builder restAdapterBuilder = new RestAdapter.Builder();
        restAdapterBuilder.setClient(new InterceptingOkClient(okHttpClient));
        restAdapterBuilder.setEndpoint(getServerUrl());
        restAdapterBuilder.setConverter(getConverter());
        restAdapterBuilder.setLogLevel(RestAdapter.LogLevel.FULL);
        return restAdapterBuilder;
    }

    protected final Converter getConverter() {
        if (mConverter == null)
            mConverter = createConverter();
        return mConverter;
    }

    protected <T> T getRetrofitService(Class<T> serviceClass) {
        T service = (T) retrofitInterfaceToServiceMap.get(serviceClass);
        if (service == null) {
            service = restAdapter.create(serviceClass);
            retrofitInterfaceToServiceMap.put(serviceClass, service);
        }
        return service;
    }

    @Override
    public void addRequest(CachedSpiceRequest<?> request, Set<RequestListener<?>> listRequestListener) {
        if (request.getSpiceRequest() instanceof RetrofitSpiceRequest) {
            RetrofitSpiceRequest retrofitSpiceRequest = (RetrofitSpiceRequest) request.getSpiceRequest();
            retrofitSpiceRequest.setService(getRetrofitService(retrofitSpiceRequest.getRetrofitedInterfaceClass()));
            retrofitSpiceRequest.setOkHttpClient(okHttpClient);
        }
        super.addRequest(request, listRequestListener);
    }

    public final List<Class<?>> getRetrofitInterfaceList() {
        return retrofitInterfaceList;
    }

    protected void addRetrofitInterface(Class<?> serviceClass) {
        retrofitInterfaceList.add(serviceClass);
    }
}
