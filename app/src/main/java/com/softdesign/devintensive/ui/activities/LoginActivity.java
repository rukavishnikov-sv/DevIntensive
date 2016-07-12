package com.softdesign.devintensive.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.data.managers.DataManager;
import com.softdesign.devintensive.data.managers.PreferenceManager;
import com.softdesign.devintensive.data.network.req.UserLoginReq;
import com.softdesign.devintensive.data.network.res.UserModelRes;
import com.softdesign.devintensive.utils.ConstantManager;
import com.softdesign.devintensive.utils.NetworkStatusCheker;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button mSignIn;
    private TextView mRememberPassword;
    private EditText mLogin, mPassword;
    private CoordinatorLayout mCoordinatorLayout;
    private DataManager mDataManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDataManager = DataManager.getInstance();

        mCoordinatorLayout=(CoordinatorLayout) findViewById(R.id.main_coordinator_container);
        mSignIn=(Button) findViewById(R.id.login_btn);
        mRememberPassword=(TextView) findViewById(R.id.remember_txt);
        mLogin=(EditText) findViewById(R.id.et_login_eaiail);
        mPassword=(EditText) findViewById(R.id.et_login_password);

        mRememberPassword.setOnClickListener(this);
        mSignIn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.login_btn :
                signIn();
                break;
            case R.id.remember_txt :
                rememberPassword();

                break;
        }
    }
    private void showSnackbar(String message){
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();

    }
    private void rememberPassword(){
        Intent rememberIntent = new Intent (Intent.ACTION_VIEW, Uri.parse("http://devintensive.softdesign-apps.ru/forgotpass"));
        startActivity(rememberIntent);
    }

    private void loginSuccsess(UserModelRes userModel){
        showSnackbar( userModel.getData().getToken());
        mDataManager.getPreferenceManager().saveAuthToken(userModel.getData().getToken());
        mDataManager.getPreferenceManager().saveUserId(userModel.getData().getUser().getId());
        saveUserValues(userModel);
        saveContentValues(userModel);

        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
    }

    private void signIn(){
        if(NetworkStatusCheker.isNetworkAvailable(this)) {
            Call<UserModelRes> call = mDataManager.loginUser(new UserLoginReq(mLogin.getText().toString(), mPassword.getText().toString()));
            call.enqueue(new Callback<UserModelRes>() {
                @Override
                public void onResponse(Call<UserModelRes> call, Response<UserModelRes> response) {
                    if (response.code() == 200) {
                        loginSuccsess(response.body());
                    } else if (response.code() == 404) {
                        showSnackbar("Неверный логин или пароль");
                    } else {
                        showSnackbar("Всё плохо !!!");
                    }
                }

                @Override
                public void onFailure(Call<UserModelRes> call, Throwable t) {
                    //TODO 12.07.2016 обработать ошибки retrofit

                }
            });
        } else showSnackbar("Сеть сейчас недоступна, попробуйте позже.");
    }
    private void saveUserValues(UserModelRes userModel){
        int[] userValues = {
                userModel.getData().getUser().getProfileValues().getRating(),
                userModel.getData().getUser().getProfileValues().getLinesCode(),
                userModel.getData().getUser().getProfileValues().getProjects()
        };
        mDataManager.getPreferenceManager().saveUserProfileValues(userValues);
    }
         private void saveContentValues(UserModelRes userModel){
                 String[] contentValues = {
                                 userModel.getData().getUser().getContacts().getPhone(),
                                 userModel.getData().getUser().getContacts().getEmail(),
                                 userModel.getData().getUser().getRepositories().getRepo().get(0).getGit(),
                                 userModel.getData().getUser().getContacts().getVk(),
                                 userModel.getData().getUser().getPublicInfo().getBio()

                                 };
                 mDataManager.getPreferenceManager().saveContentValue(contentValues);
             }
}//end of class
