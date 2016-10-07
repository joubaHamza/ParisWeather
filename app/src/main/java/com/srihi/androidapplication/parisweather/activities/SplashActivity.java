package com.srihi.androidapplication.parisweather.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.srihi.androidapplication.parisweather.R;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {


    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    boolean isSplashDoneInBG = false;

    // Declare the Handler as a member variable
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // 5. Pass a new instance of StartMainActivityRunnable with reference to 'this'.
          mHandler.postDelayed(new StartMainActivityRunnable(this), SPLASH_DISPLAY_LENGTH);
    }


    // 6. Override onDestroy()
    @Override
    public void onDestroy() {
        // 7. Remove any delayed Runnable(s) and prevent them from executing.
        mHandler.removeCallbacksAndMessages(null);
        // 8. Eagerly clear mHandler allocated memory
        mHandler = null;

        super.onDestroy();
    }

    // 1. Create a static nested class that implements Runnable to start the main Activity
    private class StartMainActivityRunnable implements Runnable {
        // 2. Make sure we keep the source Activity as a WeakReference (more on that later)
        private WeakReference<Activity> mActivity;

        private StartMainActivityRunnable(Activity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            // 3. Check that the reference is valid and execute the code
            if (mActivity.get() != null) {
                Activity activity = mActivity.get();
                isSplashDoneInBG = false;
                Intent mainIntent;
                mainIntent = new Intent(activity, MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                activity.startActivity(mainIntent);
                activity.finish();

            }
        }
    }
}
