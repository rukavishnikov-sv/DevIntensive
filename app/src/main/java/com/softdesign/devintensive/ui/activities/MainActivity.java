package com.softdesign.devintensive.ui.activities;


import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.data.managers.DataManager;
import com.softdesign.devintensive.utils.ConstantManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG= ConstantManager.TAG_PREFIX +"Main Activity";
    private DataManager mDataManager;
    private int mCurrentEditMode=0;
    private ImageView mCallImg;
    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private DrawerLayout mNavigationDrawer;

    private DrawerLayout mDrawerLayout;
    private FloatingActionButton mFab;
    private EditText mUserPhone, mUserMail, mUserVk, mUserGit, mUserBio;
    private List<EditText> mUserInfoViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate");
		//init();

        mDataManager=mDataManager.getInstance();
        mCallImg=(ImageView)findViewById(R.id.call_img);
        mCoordinatorLayout=(CoordinatorLayout)findViewById(R.id.main_coordinator_container) ;
        mToolbar=(Toolbar)findViewById(R.id.toolbar);
        ////mNavigationDrawer=(DrawerLayout) findViewById(R.id.navigation_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
        mFab=(FloatingActionButton) findViewById(R.id.fab);
        mUserPhone=(EditText) findViewById(R.id.phone_et);
        mUserMail=(EditText) findViewById(R.id.email_et);
        mUserVk=(EditText) findViewById(R.id.vk_et);
        mUserGit=(EditText) findViewById(R.id.github_et);
        mUserBio=(EditText) findViewById(R.id.about_et);
        mUserInfoViews=new ArrayList<>();
        mUserInfoViews.add(mUserPhone);
        mUserInfoViews.add(mUserMail);
        mUserInfoViews.add(mUserVk);
        mUserInfoViews.add(mUserGit);
        mUserInfoViews.add(mUserBio);


        //mFab.setOnClickListener(this);
        setupToolBar();
        setupDrawer();
        loadUserInfoValue();
        //List<String> test

        if(savedInstanceState==null){
            //showSnackBar("Activity запускается впервые");
        }
        else{
            mCurrentEditMode=savedInstanceState.getInt(ConstantManager.EDIT_MODE_KEY,0);
            changeEditMode(mCurrentEditMode);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId( )== android.R.id.home){
            //mNavigationDrawer.openDrawer(GravityCompat.START);
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return (super.onOptionsItemSelected(item));

    }
   /* private void init (){
        mCallImg = (ImageView) findViewById(R.id.call_img);
        mCoordinatorLayout=(CoordinatorLayout)findViewById(R.id.main_coordinator_container);
        mToolbar=(Toolbar)findViewById(R.id.toolbar);
        mDataManager=DataManager.getInstance();
        mNavigationDrawer =(DrawerLayout) findViewById(R.id.navigation_drawer);
        mFab=(FloatingActionButton) findViewById(R.id.fab);
        mCallImg.setOnClickListener(this);
        mFab.setOnClickListener(this);
        mUserPhone=(EditText)findViewById(R.id.phone_et) ;
        mUserMail=(EditText)findViewById(R.id.);
        mUserVk=(EditText)findViewById(R.id.vk_et);
        mUserGit=(EditText)findViewById(R.id.github_et);
        mUserBio=(EditText)findViewById(R.id.about_et);
        mUserInfoViews =new ArrayList<>();
        mUserInfoViews.add(mUserPhone);
        mUserInfoViews.add(mUserMail);
        mUserInfoViews.add(mUserVk);
        mUserInfoViews.add(mUserGit);
        mUserInfoViews.add(mUserBio);
    }*/
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.call_img:
                /*showProgress();
                runWithDelay();*/
				break;
			case R.id.fab:
                if(mCurrentEditMode ==0){
                    changeEditMode(1);
                    mCurrentEditMode=1;
					showSnackBar("Pежим реактирования");
					
                }
                else {
                    changeEditMode(0);
                    mCurrentEditMode=0;

                }
                break;

        }

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ConstantManager.EDIT_MODE_KEY,mCurrentEditMode);
    }
    private void showSnackBar(String message){
        Snackbar.make(mCoordinatorLayout,message,Snackbar.LENGTH_LONG).show();

    }
    /*private void runWithDelay(){
        final Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //выполнить с задержкой
                hideProgress();
            }

        },5000);
    }*/
    private void setupToolBar(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
   private void setupDrawer(){
        NavigationView navigationView=(NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener
                (new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                //showSnackBar(item.getTitle().toString());
                item.setChecked(true);
                mNavigationDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }
    private void changeEditMode(int mode){
        // 1 - редактирование
        //0 - просмотр
        if (mode==1) {

            mFab.setImageResource(R.drawable.ic_done_black_24dp);
            for (EditText userValue : mUserInfoViews) {
                userValue.setEnabled(true);
                userValue.setFocusable(true);
                userValue.setFocusableInTouchMode(true);
            }
        }
        else{
            mFab.setImageResource(R.drawable.ic_create_black_24dp);
            for (EditText userValue : mUserInfoViews){
                userValue.setEnabled(false);
                userValue.setFocusable(false);
                userValue.setFocusableInTouchMode(true);
                saveUserInfoValue();
            }
        }

    }
    private void loadUserInfoValue(){
        List<String> userData = mDataManager.getPreferenceManager().loadUserProfileData();
        for (int i=0; i<userData.size(); i++) {
            mUserInfoViews.get(i).setText(userData.get(i));
        }

    }
    private void saveUserInfoValue(){
        List<String> userData =new ArrayList<>();
        for (EditText userFieldView : mUserInfoViews ) {
            userData.add(userFieldView.getText().toString());
        }
        mDataManager.getPreferenceManager().saveUserProfileData(userData);

    }

}