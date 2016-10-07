package com.proxymit.robospice.retrofit.request;

import com.octo.android.robospice.request.SpiceRequest;

/**
 * A simplified {@link SpiceRequest} that makes it even easier to use a
 * retrofited REST service.
 *
 * @param <T> the result type of this request.
 * @param <R> the retrofited interface used by this request.
 * @author SNI
 */
public abstract class RetrofitSpiceRequest<T, R> extends OkHttpSpiceRequest<T> {
    private Class<R> retrofitedInterfaceClass;
    private R service;

    public RetrofitSpiceRequest(Class<T> clazz, Class<R> retrofitedInterfaceClass) {
        super(clazz);
        this.retrofitedInterfaceClass = retrofitedInterfaceClass;
    }

    public Class<R> getRetrofitedInterfaceClass() {
        return retrofitedInterfaceClass;
    }

    public R getService() {
        return service;
    }

    public void setService(R service) {
        this.service = service;
    }

}
