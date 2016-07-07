package com.softdesign.devintensive.ui.activities;


import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.data.managers.DataManager;
import com.softdesign.devintensive.utils.ConstantManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.Manifest;
//import butterknife.BindView;
//import butterknife.ButterKnife;

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
    private RelativeLayout mPrifilePlaceholder;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private AppBarLayout mAppBarLayout;
    private ImageView mProfileImage;

    private EditText mUserPhone, mUserMail, mUserVk, mUserGit, mUserBio;
    private ImageView mPhoneCall, mEmailSend, mVkOpen, mGitOpen;
    private List<EditText> mUserInfoViews;

    private AppBarLayout.LayoutParams mAppBarParams = null;
    private File mPhotoFile = null;
    private Uri mSelectedImage = null;

    //@BindView(R.id.phone_call_img) ImageView mPhoneCall;
    //@BindView(R.id.email_send_img)ImageView mEmailSend;
    //@BindView(R.id.vk_open_img)ImageView mVkOpen;
    //@BindView(R.id.git_open_img) ImageView mGitOpen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButterKnife.bind(this);
        Log.d(TAG,"onCreate");
		//init();

        mDataManager=mDataManager.getInstance();
        mCallImg=(ImageView)findViewById(R.id.call_img);
        mCoordinatorLayout=(CoordinatorLayout)findViewById(R.id.main_coordinator_container) ;
        mToolbar=(Toolbar)findViewById(R.id.toolbar);
        //mNavigationDrawer=(NavigationDrawer) findViewById(R.id.navigation_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
        mFab=(FloatingActionButton) findViewById(R.id.fab);
        mPrifilePlaceholder=(RelativeLayout) findViewById(R.id.profile_placeholder);
        mCollapsingToolbar=(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        mProfileImage=(ImageView) findViewById(R.id.user_photo_img);
        mUserPhone=(EditText) findViewById(R.id.phone_et);
        mUserMail=(EditText) findViewById(R.id.email_et);
        mUserVk=(EditText) findViewById(R.id.vk_et);
        mUserGit=(EditText) findViewById(R.id.github_et);
        mUserBio=(EditText) findViewById(R.id.about_et);
        mPhoneCall=(ImageView)findViewById(R.id.phone_call_img);
        mEmailSend=(ImageView)findViewById(R.id.email_send_img);
        mVkOpen=(ImageView)findViewById(R.id.vk_open_img);
        mGitOpen=(ImageView)findViewById(R.id.github_open_img);
        mUserInfoViews=new ArrayList<>();
        mUserInfoViews.add(mUserPhone);
        mUserInfoViews.add(mUserMail);
        mUserInfoViews.add(mUserVk);
        mUserInfoViews.add(mUserGit);
        mUserInfoViews.add(mUserBio);

        mFab.setOnClickListener(this);
        mPrifilePlaceholder.setOnClickListener(this);

        mPhoneCall.setOnClickListener(this);
        mEmailSend.setOnClickListener(this);
        mVkOpen.setOnClickListener(this);
        mGitOpen.setOnClickListener(this);

        setupToolBar();
        setupDrawer();
        loadUserInfoValue();
        Picasso.with(this)
                .load(mDataManager.getPreferenceManager().loadUserPhoto())
                .placeholder(R.drawable.r)////TODO: сделать плейсхолдер и transform + crop
                .into(mProfileImage);
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

    //Обработка нажатий
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //Режим редактирования
			case R.id.fab:
                if(mCurrentEditMode == 0){
                    changeEditMode(1);
                    mCurrentEditMode = 1;
					showSnackBar("Pежим реактирования");
					
                }
                //Режим просмотра
                else {
                    changeEditMode(0);
                    mCurrentEditMode=0;

                }
                break;
            case R.id.profile_placeholder:
                //// TODO 27.06.2016 сделать выбор откуда загружать фото
                showDialog(ConstantManager.LOAD_PROFILE_PHOTO);

                break;
            case R.id.phone_call_img:
                // сделать звонок по введеному телефону
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mUserPhone.getText().toString()));
                    startActivity(callIntent);

                }
                else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                            ConstantManager.PHONE_CALL_REQUEST_PERMISSION_CODE);
                }
                break;
            case R.id.email_send_img:
                //  отправить письмо по указаннму email
                Intent intent = new Intent(Intent.ACTION_SEND);
                String adress= mUserMail.getText().toString();
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_EMAIL,adress);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "ТЕМА");
                        intent.putExtra(Intent.EXTRA_TEXT, "ТЕКСТ");
                        startActivity(Intent.createChooser(intent, "Send Email"));
                break;
            case R.id.vk_open_img:
                // открыть страницу VK
                Uri uriVk = Uri.parse("https://"+mUserVk.getText().toString());//vk.com/id157346726 https://github.com/rukavishnikov-sv/
                Intent vkIntent = new Intent(Intent.ACTION_VIEW, uriVk);
                startActivity(vkIntent);
                break;
            case R.id.github_open_img:
                // открыть страницу GitHub
                Uri uriGithub = Uri.parse("https://"+mUserGit.getText().toString());//vk.com/id157346726 https://github.com/rukavishnikov-sv/
                Intent githubIntent = new Intent(Intent.ACTION_VIEW, uriGithub);
                startActivity(githubIntent);
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
        // определение метода setupToolBar
        setSupportActionBar(mToolbar);

        ActionBar actionBar=getSupportActionBar();

        mAppBarParams = (AppBarLayout.LayoutParams) mCollapsingToolbar.getLayoutParams();
        if(actionBar!=null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
   private void setupDrawer(){
       // определение метода NavigationDrawer
        NavigationView navigationView= (NavigationView) findViewById(R.id.navigation_view);
       assert navigationView != null;
        navigationView.setNavigationItemSelectedListener (new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                showSnackBar(item.getTitle().toString());
                item.setChecked(true);
                if (item.getItemId() == R.id.drawer_team_menu)
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));//вызов интента LoginActivity при нажатии
                if (item.getItemId() == R.id.drawer_user_profile_menu)
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));//вызов интента LoginActivity при нажатии
                mNavigationDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // получение фото из камеры или из галереи
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ConstantManager.REQUEST_GALERY_PICTURE:
                if (resultCode==RESULT_OK && data!= null){
                    mSelectedImage  = data.getData();
                    insertProfileImage(mSelectedImage);
                }
            break;
            case ConstantManager.REQUEST_CAMERA_PICTURE:
                if (resultCode == RESULT_OK && mPhotoFile != null){
                    mSelectedImage = Uri.fromFile(mPhotoFile);
                    insertProfileImage(mSelectedImage);
                }
        }
    }



    private void changeEditMode(int mode){
        //Изменеить EditMode
        // 1 - редактирование
        //0 - просмотр
        if (mode==1) {

            mFab.setImageResource(R.drawable.ic_done_black_24dp);
            for (EditText userValue : mUserInfoViews) {
                userValue.setEnabled(true);
                userValue.setFocusable(true);
                userValue.setFocusableInTouchMode(true);
                showProfilePlaceholder();
                lockToolbar();
                mCollapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);
            }
            mPhoneCall.setEnabled(false);//запрет звонка
            mEmailSend.setEnabled(false);//запрет отправки почты
            mVkOpen.setEnabled(false);// запрет открытия VK
            mGitOpen.setEnabled(false);// запрет открытия GitHub
            mUserPhone.requestFocus(); // при режиме редактировадия установить фокус ввода на поле телефона

        }
        else{
            mFab.setImageResource(R.drawable.ic_create_black_24dp);
            for (EditText userValue : mUserInfoViews){
                userValue.setEnabled(false);
                userValue.setFocusable(false);
                userValue.setFocusableInTouchMode(true);
                hideProfilePlaceholder();
                unlockToolbar();
                mCollapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.white));
                saveUserInfoValue();
            }
            mPhoneCall.setEnabled(true);//разрешение звонка
            mEmailSend.setEnabled(true);//разрешение отправки почты
            mVkOpen.setEnabled(true);// разрешение открытия VK
            mGitOpen.setEnabled(true);// разрешение открытия GitHub
        }

    }
    private void loadUserInfoValue(){
        //Загрузка данных
        List<String> userData = mDataManager.getPreferenceManager().loadUserProfileData();
        for (int i=0; i<userData.size(); i++) {
            mUserInfoViews.get(i).setText(userData.get(i));
        }

    }
    private void saveUserInfoValue(){
        // Сохранение данных
        List<String> userData =new ArrayList<>();
        for (EditText userFieldView : mUserInfoViews ) {
            userData.add(userFieldView.getText().toString());
        }
        mDataManager.getPreferenceManager().saveUserProfileData(userData);

    }
    private void loadPhotoFromGalery(){
        Intent takeGaleryIntent = new Intent(Intent.ACTION_PICK_ACTIVITY, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        takeGaleryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(takeGaleryIntent, getString(R.string.user_profile_choose_message)), ConstantManager.REQUEST_GALERY_PICTURE);

    }
    private void loadPhotoFromCamera(){
        //Загрузить с камеры
        //File photoFile=null;
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Intent takeCaptureIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
            try{
                mPhotoFile = createImageFile();
            }
            catch (IOException e) {
                e.printStackTrace();
                //// TODO: 04.07.2016  обработать ошибку
                if (mPhotoFile != null) { //todo передать файл в интент}
                    takeCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
                    startActivityForResult(takeCaptureIntent, ConstantManager.REQUEST_CAMERA_PICTURE);
                }
            }
        }
        else {
            ActivityCompat.requestPermissions(this, new String [] {
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },ConstantManager.CAMERA_REQUEST_PERMISSION_CODE);
            Snackbar.make(mCoordinatorLayout, "Для корректной работы приложения необходимо дать требуемые разрешениz", Snackbar.LENGTH_LONG)
                    .setAction("Разрешить", new View.OnClickListener(){
                        @Override
                        public  void  onClick(View v) {
                            openApplicationSettings();
                        }
                    }).show();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int [] grantResults) {
        if (requestCode == ConstantManager.CAMERA_REQUEST_PERMISSION_CODE && grantResults.length==2) {
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                //todo обработать разрешение (оазрешение ролучено
                // например вывести сообщение или обработать какой-то логикой если нужно)
            }

        }
        if(grantResults[1]== PackageManager.PERMISSION_GRANTED){
            //todo обработать разрешение (оазрешение ролучено
            // например вывести сообщение или обработать какой-то логикой если нужно)
        }

    }
    private void hideProfilePlaceholder(){
        mPrifilePlaceholder.setVisibility(View.GONE);
    }
    private void showProfilePlaceholder(){
        mPrifilePlaceholder.setVisibility(View.VISIBLE);
    }
    private void lockToolbar(){
        mAppBarLayout.setExpanded(true,true);
        mAppBarParams.setScrollFlags(0);
        mCollapsingToolbar.setLayoutParams(mAppBarParams);

    }
    private void unlockToolbar(){
        mAppBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        mCollapsingToolbar.setLayoutParams(mAppBarParams);
    }
    @Override
    protected Dialog onCreateDialog (int id){
        switch (id){
            case ConstantManager.LOAD_PROFILE_PHOTO :
                String [] selectItems = {getString(R.string.user_profile_dialog_galery),getString(R.string.user_profile_dialog_camera),getString(R.string.user_profile_dialog_cancel)};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.user_profile_dialog_title));
                builder.setItems(selectItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int choiseItem) {
                        switch (choiseItem) {
                            case 0:
                                //TODO 27/06/2016 загрузить из галереи
                                loadPhotoFromGalery();
                                //showSnackBar("загрузить из галереи");
                                break;
                            case 1:
                                //TODO 27/06/2016 загрузить из камеры
                                loadPhotoFromCamera();
                                //showSnackBar("загрузить из камеры");
                                break;
                            case 2:
                                //TODO 27/06/2016 отмена
                                dialog.cancel();
                                showSnackBar("Отмена");
                                break;
                        }

                    }
                });
             return builder.create();

            default: return null;
        }
    }
    private File createImageFile() throws IOException{
        //Создать файл фото
        String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); //(String.format("yyyyMMdd_HHmmss", ))
        String imageFileName = "JPEG_"+timeStamp+"_";
        File storageDir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image= File.createTempFile(imageFileName,".jpg", storageDir);

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, image.getAbsolutePath());

        this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        return  image;
    }
    private void insertProfileImage(Uri selectedImage) {
        Picasso.with(this)
                .load(selectedImage)
                .placeholder(R.drawable.r)//TODO: сделать плейсхолдер и transform + crop
                .into(mProfileImage);
        mDataManager.getPreferenceManager().SaveUserPhoto(selectedImage);
    }
    private void openApplicationSettings(){
        // Открыть настройки приложения
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
        startActivityForResult(appSettingsIntent,ConstantManager.PERMISSION_REQUEST_SETTINGS_CODE);
    }

}
