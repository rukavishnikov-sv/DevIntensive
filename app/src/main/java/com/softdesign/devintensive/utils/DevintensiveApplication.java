package com.softdesign.devintensive.utils;

/**
 * Created by Admin on 29.06.2016.
 *
 */
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class DevintensiveApplication extends Application{
    public static SharedPreferences sSharedPreferences;
    private static Context mContext;

    @Override
    public void onCreate(){
        super.onCreate();
        sSharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        mContext = this;
        super.onCreate();

    }
    public static SharedPreferences getSharedPreferences(){
        return sSharedPreferences;
    }

    public static Context getContext() {
        return mContext;
    }
}

