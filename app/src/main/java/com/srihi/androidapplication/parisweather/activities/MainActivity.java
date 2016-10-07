package com.srihi.androidapplication.parisweather.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.srihi.androidapplication.parisweather.R;
import com.srihi.androidapplication.parisweather.fragments.DetailFragment;
import com.srihi.androidapplication.parisweather.utils.KeybordUtils;
import com.srihi.androidapplication.parisweather.utils.SharedPreference;

import static com.srihi.androidapplication.parisweather.utils.KeybordUtils.hideKeyboard;

public class MainActivity extends AppCompatActivity {

    public FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPreference.getInstance(getApplicationContext()).getCITYID() != null && !SharedPreference.getInstance(getApplicationContext()).getCITYID().equals("")) {
                    long id = Long.valueOf(SharedPreference.getInstance(getApplicationContext()).getCITYID());
                    replaceFragment(DetailFragment.newInstance(id, 0));
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public int getBaseContainerResourceID() {
        return R.id.fragment;
    }

    public void replaceFragment(Fragment fragment) {
        hideKeyboard(this);

        if (!(fragment.getClass().isInstance(getLastFragment()))) {
            final String className = fragment.getClass().getName();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (!fragment.isAdded()) {
                ft.replace(getBaseContainerResourceID(), fragment, className);
                ft.addToBackStack(className);
                ft.commit();
            } else {
                ft.show(getFragment(fragment.getClass()));
            }
        }

    }

    public Fragment getFragment(Class Object) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return (Fragment) fragmentManager.findFragmentByTag(Object.getName());
    }

    public Fragment getLastFragment() {
        final FragmentManager fm = getSupportFragmentManager();

        if (fm.getBackStackEntryCount() > 0) {
            int lastIndex = fm.getBackStackEntryCount() - 1;
            if (lastIndex < 0)
                lastIndex = 0;
            final String lastTag = fm.getBackStackEntryAt(lastIndex).getName();
            return (Fragment) fm.findFragmentByTag(lastTag);
        }
        return null;
    }


    public void onBackClick() {
        KeybordUtils.hideKeyboard(this);
        if (canBack()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack();
        }
    }

    public boolean canBack() {
        return getBackStackEntryCount() > 0;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Fragment baseFragment = getLastFragment();
            if (baseFragment != null)
                onBackClick();
            else
                exitApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }


    public void exitApp() {
        // Exit PopUp

        final AlertDialog.Builder builder = new AlertDialog.Builder(
                this);
        builder.setMessage(getResources().getString(R.string.finish_confirm))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.YES),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                finish();
                            }
                        })
                .setNegativeButton(getResources().getString(R.string.NO),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    /**
     * Return the number of entries currently in the back stack.
     *
     * @return the number of entries.
     */
    public int getBackStackEntryCount() {
        final FragmentManager fm = getSupportFragmentManager();
        if (fm != null)
            return fm.getBackStackEntryCount();
        return 0;


    }
}
