package com.countryecbuyer.activity;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.activity.personalInfo.nickNameActivity;
import com.countryecbuyer.activity.personalInfo.passwordChangeActivity;
import com.countryecbuyer.activity.personalInfo.receiverAddressActivity;
import com.countryecbuyer.activity.personalInfo.sexSettingActivity;
import com.countryecbuyer.fragment.bottom_sheet.BS_HeadFragment;
import com.countryecbuyer.utils.FileUtils;
import com.countryecbuyer.utils.ToastUtils;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人信息
 */
public class PersonalInformationActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_STORAGE = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;//拍照
    private static final int REQUEST_IMAGE_PICKER = 2;//从相册选取
    private static final int REQUEST_NICKNAME = 3;//修改昵称
    private static final int REQUEST_SEX = 4;//修改性别
    private static final String HEADNAME = "waycube_user_head";//保存本地的头像

    private int i = -1;
    private Toolbar toolbar;
    private LinearLayout headLL, nickLL, sexLL, pwdChangeLL, addrLL;
    private CircleImageView headImg;
    private TextView nickname, sex;

    private BottomSheetLayout bottomSheetLayout;
    private BS_HeadFragment headFragment;//头像布局


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_personal_information;
    }

    @Override
    protected void init() {
        headFragment = new BS_HeadFragment();
    }

    @Override
    protected void initData() {
        File f = new File(FileUtils.SDPATH, "waycube_user_head.png");
        if (f.exists()) {
            Picasso.with(this)
                    .load(f)
                    .centerCrop()
                    .fit()
                    .into(headImg);
        }
    }

    @Override
    protected void initView() {
        toolbar = customFindViewById(R.id.toolbar);
        toolbar.setTitle("个人信息");
        setSupportActionBar(toolbar);//调用返回获取焦点
        toolbar.setNavigationIcon(R.mipmap.icon_back_gray);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                PersonalInformationActivity.this.setResult(6, intent);
                finish();
            }
        });
        nickname = customFindViewById(R.id.personal_info_nickname);
        sex = customFindViewById(R.id.personal_info_sex);
        headLL = customFindViewById(R.id.personal_info_headLL);
        headImg = customFindViewById(R.id.personal_info_head);
        nickLL = customFindViewById(R.id.personal_info_nicknameLL);
        sexLL = customFindViewById(R.id.personal_info_sexLL);
        addrLL = customFindViewById(R.id.personal_info_addrLL);
        pwdChangeLL = customFindViewById(R.id.personal_info_changePwdLL);
        bottomSheetLayout = customFindViewById(R.id.bottomsheet_personal);

        headFragment.setSheetviewClick(new BS_HeadFragment.sheetViewClick() {
            @Override
            public void sheetView_photoPick() {
                startActivityForResult(createPickIntent(), REQUEST_IMAGE_PICKER);
            }

            @Override
            public void sheetView_take_capture() {
                dispatchTakePictureIntent();
            }
        });

        bindListener();
    }

    private void bindListener() {
        nickLL.setOnClickListener(this);
        headLL.setOnClickListener(this);
        sexLL.setOnClickListener(this);
        addrLL.setOnClickListener(this);
        pwdChangeLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 修改头像
             */
            case R.id.personal_info_headLL:
//                if (checkNeedsPermission()) {
//                    requestStoragePermission();
//                } else {
//                }
                headFragment.show(getSupportFragmentManager(), R.id.bottomsheet_personal);
                break;

            /**
             * 修改昵称
             */
            case R.id.personal_info_nicknameLL:
                startActivityForResultWithRequestCode(nickNameActivity.class, REQUEST_NICKNAME);
                break;
            /**
             * 修改性别
             */
            case R.id.personal_info_sexLL:
                Bundle bundle = new Bundle();
                bundle.putString("the_sex", sex.getText().toString());
                startActivityForResultWithReqCodeBundle(sexSettingActivity.class, REQUEST_SEX, bundle);
                break;
            /**
             * 修改密码
             */
            case R.id.personal_info_changePwdLL:
                startActivityWithoutExtras(passwordChangeActivity.class);
                break;
            /**
             * 收货地址
             */
            case R.id.personal_info_addrLL:
                startActivityWithoutExtras(receiverAddressActivity.class);
                break;


            //dialog样式
//            case R.id.basic_test:
//                // default title "Here's a message!"
//                SweetAlertDialog sd = new SweetAlertDialog(this);
//                sd.setCancelable(true);
//                sd.setCanceledOnTouchOutside(true);
//                sd.show();
//                break;
//            case R.id.under_text_test:
//                new SweetAlertDialog(this)
//                        .setContentText("It's pretty, isn't it?")
//                        .show();
//                break;
//            case R.id.error_text_test:
//                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
//                        .setTitleText("Oops...")
//                        .setContentText("Something went wrong!")
//                        .show();
//                break;
//            case R.id.success_text_test:
//                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
//                        .setTitleText("Good job!")
//                        .setContentText("You clicked the button!")
//                        .show();
//                break;
//            case R.id.warning_confirm_test:
//                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
//                        .setTitleText("Are you sure?")
//                        .setContentText("Won't be able to recover this file!")
//                        .setConfirmText("Yes,delete it!")
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sDialog) {
//                                // reuse previous dialog instance
//                                sDialog.setTitleText("Deleted!")
//                                        .setContentText("Your imaginary file has been deleted!")
//                                        .setConfirmText("OK")
//                                        .setConfirmClickListener(null)
//                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//                            }
//                        }).show();
//                break;
//            case R.id.warning_cancel_test:
//                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
//                        .setTitleText("Are you sure?")
//                        .setContentText("Won't be able to recover this file!")
//                        .setCancelText("No,cancel plx!")
//                        .setConfirmText("Yes,delete it!")
//                        .showCancelButton(true)
//                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sDialog) {
//                                // reuse previous dialog instance, keep widget user state, reset them if you need
//                                sDialog.setTitleText("Cancelled!")
//                                        .setContentText("Your imaginary file is safe :)")
//                                        .setConfirmText("OK")
//                                        .showCancelButton(false)
//                                        .setCancelClickListener(null)
//                                        .setConfirmClickListener(null)
//                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
//                            }
//                        })
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sDialog) {
//                                sDialog.setTitleText("Deleted!")
//                                        .setContentText("Your imaginary file has been deleted!")
//                                        .setConfirmText("OK")
//                                        .showCancelButton(false)
//                                        .setCancelClickListener(null)
//                                        .setConfirmClickListener(null)
//                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//                            }
//                        })
//                        .show();
//                break;
//            case R.id.custom_img_test:
//                new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
//                        .setTitleText("Sweet!")
//                        .setContentText("Here's a custom image.")
//                        .setCustomImage(R.drawable.default_load_img)
//                        .show();
//                break;
//            case R.id.progress_dialog:
//                final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
//                        .setTitleText("Loading");
//                pDialog.show();
//                pDialog.setCancelable(false);
//                new CountDownTimer(800 * 7, 800) {  //总时间，间隔时间
//                    public void onTick(long millisUntilFinished) {
//                        // you can change the progress bar color by ProgressHelper every 800 millis
//                        i++;
//                        switch (i) {
//                            case 0:
//                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
//                                break;
//                            case 1:
//                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
//                                break;
//                            case 2:
//                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
//                                break;
//                            case 3:
//                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_20));
//                                break;
//                            case 4:
//                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_blue_grey_80));
//                                break;
//                            case 5:
//                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.warning_stroke_color));
//                                break;
//                            case 6:
//                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
//                                break;
//                        }
//                    }
//
//                    public void onFinish() {
//                        i = -1;
//                        pDialog.setTitleText("Success!")
//                                .setConfirmText("OK")
//                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//                    }
//                }.start();
//                break;
        }
    }


    //.JELLY_BEAN安卓4.2版本  ,检查权限
    private boolean checkNeedsPermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && ActivityCompat.checkSelfPermission(PersonalInformationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
    }

    //请求权限
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE);
        } else {
            // Eh, prompt anyway
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                headFragment.show(getSupportFragmentManager(), R.id.bottomsheet_personal);
            } else {
                // Permission denied没有权限提示
                ToastUtils.getInstance().showToast("Sheet is useless without access to external storage :/");
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 调用相机
     */
    @Nullable
    private Intent createCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            return takePictureIntent;
        } else {
            return null;
        }
    }

    /**
     * 相册选取
     */
    @Nullable
    private Intent createPickIntent() {
        Intent picImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (picImageIntent.resolveActivity(getPackageManager()) != null) {
            return picImageIntent;
        } else {
            return null;
        }
    }

    /**
     * 调用相机拍照
     */
    private void dispatchTakePictureIntent() {
        String state = Environment.getExternalStorageState(); // 判断是否存在sd卡
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent takePictureIntent = createCameraIntent();
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            ToastUtils.getInstance().showToast("请检查手机SD卡");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImage = null;
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {   //拍照
                Bitmap bm = (Bitmap) data.getExtras().get("data");
                selectedImage = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bm, null, null));
            } else if (requestCode == REQUEST_IMAGE_PICKER && data != null) { //相册选取
                selectedImage = data.getData();
            }
            showHead(selectedImage);
        } else if (requestCode == REQUEST_NICKNAME && resultCode == REQUEST_NICKNAME) {  //修改昵称
            Bundle bundle = data.getExtras();
            String result = bundle.getString("nickname");
            nickname.setText(result);
        } else if (requestCode == REQUEST_SEX && resultCode == REQUEST_SEX) {   //修改性别
            Bundle bundle = data.getExtras();
            String result = bundle.getString("sex");
            sex.setText(result);
        }
    }

    private void showHead(Uri selectedImageUri) {
        if (selectedImageUri == null) {
            return;
        }
        Picasso.with(this)
                .load(selectedImageUri)
                .centerCrop()
                .fit()
                .into(headImg);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
            FileUtils.saveBitmap(bitmap, HEADNAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtils.getInstance().cancelToast();
    }
}
