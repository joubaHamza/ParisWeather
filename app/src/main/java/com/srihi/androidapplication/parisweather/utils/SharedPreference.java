package com.srihi.androidapplication.parisweather.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Hamza Srihi on 06/10/2016.
 */
public class SharedPreference {


    private static SharedPreferences sharedPref;
    private static String CITYID = "cityId";
    private static final String APP_PREF = "AppPref";

    private static SharedPreference instance;
    private SharedPreferences.Editor editor;


    public SharedPreference(Context context) {
        sharedPref = context.getApplicationContext().getSharedPreferences(APP_PREF,
                context.getApplicationContext().MODE_WORLD_WRITEABLE);

        editor = sharedPref.edit();
    }

    public static synchronized SharedPreference getInstance(Context context) {
        if (sharedPref == null)
            instance = new SharedPreference(context);

        return instance;
    }

    public SharedPreferences.Editor edit() {
        if (editor == null)
            if (sharedPref != null)
                editor = sharedPref.edit();
        return editor;
    }

    public String getCITYID() {
        return sharedPref.getString(CITYID, "");
    }

    public void setCITYID(String cityId) {
        editor.putString(CITYID, cityId);
        editor.commit();
    }


    public void deleteAllPref() {
        editor.clear();
        editor.commit();
    }


}
