package com.softdesign.devintensive.data.managers;

/**
 * Created by Admin on 29.06.2016.
 */
import android.content.SharedPreferences;
import android.net.Uri;

import com.softdesign.devintensive.utils.ConstantManager;
import com.softdesign.devintensive.utils.DevintensiveApplication;

import java.util.ArrayList;
import java.util.List;

public class PreferenceManager {
    private SharedPreferences mSharedPreferences;
    private static final String[] USER_FIELDS={
            ConstantManager.USER_PHONE_KEY,
            ConstantManager.USER_MAIL_KEY,
            ConstantManager.USER_VK_KEY,
            ConstantManager.USER_GIT_KEY,
            ConstantManager.USER_BIO_KEY
    };

    private static final String[] USER_VALUES = {
            ConstantManager.USER_RAITING_VALUE,
            ConstantManager.USER_CODE_LINES_VALUE,
            ConstantManager.USER_PROJECT_VALUE,
    };
    private static final String[] CONTENT_VALUES = {
            ConstantManager.CONTENT_PHONE_VALUE,
            ConstantManager.CONTENT_EMAIL_VALUE,
            ConstantManager.CONTENT_GIT_VALUE,
            ConstantManager.CONTENT_VK_VALUE,
            ConstantManager.CONTENT_BIO_VALUE
    };
    public PreferenceManager(){
        this.mSharedPreferences= DevintensiveApplication.getSharedPreferences();
    }
    public void saveUserProfileData(List<String> userFields) {
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        for(int i=0; i< USER_FIELDS.length;i++ ){
            editor.putString(USER_FIELDS[i], userFields.get(i));
        }
        editor.apply();
    }
    public List<String> loadUserProfileData(){
        List<String> userFields = new ArrayList<>();
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_PHONE_KEY, "null"));
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_MAIL_KEY, "null"));
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_VK_KEY, "null"));
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_GIT_KEY, "null"));
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_BIO_KEY, "null"));
        return userFields;
    }
    public void SaveUserPhoto(Uri uri){
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        editor.putString(ConstantManager.USER_PHOTO_KEY, uri.toString());
        editor.apply();
    }

    public List<String> loadUserProfileValues(){
        List<String> userValues = new ArrayList<>();
        userValues.add(mSharedPreferences.getString(ConstantManager.USER_RAITING_VALUE,"0"));
        userValues.add(mSharedPreferences.getString(ConstantManager.USER_CODE_LINES_VALUE,"0"));
        userValues.add(mSharedPreferences.getString(ConstantManager.USER_PROJECT_VALUE,"0"));
        return userValues;
    }
    public void saveUserProfileValues(int[] userValues){
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        for(int i=0; i< USER_VALUES.length;i++ ){
            editor.putString(USER_VALUES[i], String.valueOf(userValues[i]));
        }
        editor.apply();

    }
    public Uri loadUserPhoto(){
        return Uri.parse(mSharedPreferences.getString(ConstantManager.USER_PHOTO_KEY,"android.resource://com.softdesign.devintensive/drawable/r"));
    }
    public void saveAuthToken(String authToken){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.AUTH_TOKEN_KEY, authToken);
        editor.apply();
    }
    public String getAuthToken(){
        return mSharedPreferences.getString(ConstantManager.AUTH_TOKEN_KEY, "null");
    }

    public void saveUserId (String userId) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.USER_ID_KEY, userId);
        editor.apply();
    }
    public String getUserId() {
        return mSharedPreferences.getString(ConstantManager.USER_ID_KEY, "null");
    }
    public void saveContentValue(String[] contentValues){
                 SharedPreferences.Editor editor = mSharedPreferences.edit();
                 for (int i = 0; i < CONTENT_VALUES.length; i++) {
                         if (CONTENT_VALUES[i].equals(ConstantManager.CONTENT_VK_VALUE) ||
                                         CONTENT_VALUES[i].equals(ConstantManager.CONTENT_GIT_VALUE)) {
                                 editor.putString(CONTENT_VALUES[i], contentValues[i].substring(7));
                             }else{
                                 editor.putString(CONTENT_VALUES[i], contentValues[i]);
                             }
                     }
                 editor.apply();
             }
    public List<String> loadContentValue(){
                 List<String> userValues = new ArrayList<>();
                 for (int i = 0; i < CONTENT_VALUES.length; i++) {
                         userValues.add(mSharedPreferences.getString(CONTENT_VALUES[i], "null"));
                     }
                 return userValues;
             }

}// end of class
