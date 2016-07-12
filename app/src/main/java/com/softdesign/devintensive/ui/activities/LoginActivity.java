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

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button mSignIn;
    private TextView mRememberPassword;
    private EditText mLogin, mPassword;
    private CoordinatorLayout mCoordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                showSnackbar("Вход");

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

    private void loginSuccsess(){}
}
