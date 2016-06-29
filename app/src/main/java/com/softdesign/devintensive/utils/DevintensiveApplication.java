package com.softdesign.devintensive.utils;

/**
 * Created by Admin on 29.06.2016.
 *
 */
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DevintensiveApplication extends Application{
    public static SharedPreferences sSharedPreferences;

    @Override
    public void onCreate(){
        super.onCreate();
        sSharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
    }
    public static SharedPreferences getSharedPreferences(){
        return sSharedPreferences;
    }
}
