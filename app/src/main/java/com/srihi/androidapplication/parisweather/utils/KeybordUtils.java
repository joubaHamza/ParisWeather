package com.srihi.androidapplication.parisweather.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Hamza Srihi on 05/10/2016.
 */
public class KeybordUtils {

    public static void hideKeyboard(Activity mActivity) {
        if (mActivity != null) {
            try {
                InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mActivity.getWindow().getDecorView().getRootView().getWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
