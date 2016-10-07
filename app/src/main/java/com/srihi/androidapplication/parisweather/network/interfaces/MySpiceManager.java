package com.srihi.androidapplication.parisweather.network.interfaces;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.exception.CacheSavingException;
import com.srihi.androidapplication.parisweather.network.WebServiceRetrofitSpiceService;
import com.srihi.androidapplication.parisweather.utils.NetworkUtils;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Created by Hamza Srihi on 05/10/2016.
 */
public class MySpiceManager {

    protected static SpiceManager mSpiceManager = new SpiceManager(WebServiceRetrofitSpiceService.class);
    protected static MySpiceManager mMySpiceManager;
    protected Object oldData;
    protected AbstractRetrofitSpiceRequest mAbstractRetrofitSpiceRequest;
    protected Context mContext;

    public static SpiceManager getmSpiceManager() {
        return mSpiceManager;
    }

    public static void startMySpaceManager(Context mContext) {
        if (mSpiceManager.isStarted())
            mSpiceManager.shouldStop();
        mSpiceManager.start(mContext);
    }

    public static void shouldStopMySpaceManager() {
        if (mSpiceManager.isStarted())
            mSpiceManager.shouldStop();
    }

    public static MySpiceManager getInstance(Context mContext) {
        if (mMySpiceManager == null)
            mMySpiceManager = new MySpiceManager();
        mMySpiceManager.setmContext(mContext);
        return mMySpiceManager;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public synchronized void executeFromSavedData(Activity context, AbstractRetrofitSpiceRequest mAbstractRetrofitSpiceRequest, RequestListener mRequestListener, Class<?> clazz) {

        this.mAbstractRetrofitSpiceRequest = mAbstractRetrofitSpiceRequest;
        if (NetworkUtils.isInternetExist(mContext)) {
            try {
                this.oldData = MySpiceManager.getmSpiceManager().getDataFromCache(clazz, mAbstractRetrofitSpiceRequest.generateCacheKey()).get();

            } catch (Exception e) {
                e.printStackTrace();
            }
            MySpiceManager.getmSpiceManager().execute(mAbstractRetrofitSpiceRequest, mAbstractRetrofitSpiceRequest.generateCacheKey(),
                    DurationInMillis.ALWAYS_EXPIRED, mRequestListener);
        } else {

            MySpiceManager.getmSpiceManager().execute(mAbstractRetrofitSpiceRequest, mAbstractRetrofitSpiceRequest.generateCacheKey(),
                    DurationInMillis.ALWAYS_RETURNED, mRequestListener);
        }
    }

    public Object getOldData() {
        if (this.oldData != null) {

            try {
                MySpiceManager.getmSpiceManager().putDataInCache(mAbstractRetrofitSpiceRequest.generateCacheKey(), this.oldData);
            } catch (CacheSavingException e) {
                e.printStackTrace();
            } catch (CacheCreationException e) {
                e.printStackTrace();
            }

        } else {
            Log.e("Date", "showOldData oldData == null");
        }
        return this.oldData;
    }

    public void removeSpiceManagerCaches() {
        mSpiceManager.removeAllDataFromCache();
    }
}
