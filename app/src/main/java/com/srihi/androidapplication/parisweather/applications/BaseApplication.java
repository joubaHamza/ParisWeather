package com.srihi.androidapplication.parisweather.applications;

import android.support.multidex.MultiDexApplication;

import com.srihi.androidapplication.parisweather.R;
import com.srihi.androidapplication.parisweather.network.interfaces.MySpiceManager;

/**
 * Created by Hamza Srihi on 05/10/2016.
 */
public class BaseApplication extends MultiDexApplication {




    @Override
    public void onCreate() {
        super.onCreate();

        //intialize SpiceManager instance
        MySpiceManager.startMySpaceManager(this);

    }

    @Override
    public void onTerminate() {
        MySpiceManager.shouldStopMySpaceManager();
        super.onTerminate();
    }

}
