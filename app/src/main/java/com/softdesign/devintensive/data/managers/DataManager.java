package com.softdesign.devintensive.data.managers;

import android.content.Context;

import com.softdesign.devintensive.data.network.RestService;
import com.softdesign.devintensive.data.network.ServiceGenerator;
import com.softdesign.devintensive.data.network.req.UserLoginReq;
import com.softdesign.devintensive.data.network.res.UserModelRes;
import com.softdesign.devintensive.utils.DevintensiveApplication;
import retrofit2.Call;

/**
 * Created by Admin on 29.06.2016.
 */
public class DataManager {
    private static DataManager INSTANCE = null;

    private Context mContext;
    private PreferenceManager mPreferenceManager;
    private RestService mRestService;
    public DataManager(){
        this.mPreferenceManager= new PreferenceManager();
        this.mContext = DevintensiveApplication.getContext();
        this.mRestService = ServiceGenerator.createService(RestService.class);

    }
    public static DataManager getInstance(){
        if (INSTANCE == null){
            INSTANCE= new DataManager();
        }
        return INSTANCE;
    }
    public PreferenceManager getPreferenceManager(){
        return mPreferenceManager;
    }
    public Context getContext (){
        return mContext;
    }
    // region =============Network===============

    public Call<UserModelRes> loginUser(UserLoginReq userLoginReq){
        return mRestService.loginUser(userLoginReq);
    }

    //end region

    // region =============Database===============

}//end of class
