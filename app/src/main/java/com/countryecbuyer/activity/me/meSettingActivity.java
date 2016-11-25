package com.countryecbuyer.activity.me;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.activity.setting.aboutUsActivity;
import com.countryecbuyer.activity.setting.newsNotifyActivity;
import com.countryecbuyer.user_info.UserInfoSharePreference;
import com.countryecbuyer.utils.CrazyClickUtils;
import com.countryecbuyer.utils.DataCleanManagerUtils;
import com.countryecbuyer.utils.FileUtils;
import com.countryecbuyer.utils.ToastUtils;
import com.countryecbuyer.view.sweet_alert_dialog.SweetAlertDialog;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

public class meSettingActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private int i = -1;
    private static final int REQUESTCODE = 0;//请求码
    private static final int RESULTCODE = 0;//返回码

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private LinearLayout newsNotify, aboutUs, giveGrade, clearCache;
    private TextView cache;
    private Button exit;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_me_setting;
    }

    @Override
    protected void init() {
        mContext = this;
    }

    @Override
    protected void initData() {
        try {
            //DataCleanManagerUtils.getCacheSize(mContext.getCacheDir());
            cache.setText(DataCleanManagerUtils.getCacheSize(mContext.getCacheDir()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //如果已经登录
        if (!UserInfoSharePreference.getInstance().getTOKEN().equals("")) {
            exit.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.icon_back_gray);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        newsNotify = customFindViewById(R.id.me_setting_newsNotify);
        aboutUs = customFindViewById(R.id.me_setting_aboutUs);
        giveGrade = customFindViewById(R.id.me_setting_giveGrade);
        clearCache = customFindViewById(R.id.me_setting_clearCache);
        cache = customFindViewById(R.id.me_setting_cache);
        exit = customFindViewById(R.id.me_setting_exit);
        exit.setOnClickListener(this);
        newsNotify.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
        giveGrade.setOnClickListener(this);
        clearCache.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.me_setting_clearCache:
                try {
                    if (DataCleanManagerUtils.getCacheSize(mContext.getCacheDir()).equals("0.0Byte")) {
                        ToastUtils.getInstance().showToastCenter("已清理完毕!");
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new SweetAlertDialog(this)
                        .setTitleText("提示")
                        .setContentText("是否要清除缓存?")
                        .setConfirmText("确定")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                DataCleanManagerUtils.cleanInternalCache(mContext);
                                clearCache();
                                sweetAlertDialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.me_setting_aboutUs:
                startActivityWithoutExtras(aboutUsActivity.class);
                break;
            case R.id.me_setting_giveGrade:
                break;
            case R.id.me_setting_newsNotify:
                startActivityWithoutExtras(newsNotifyActivity.class);
                break;
            case R.id.me_setting_exit:
                if (CrazyClickUtils.isFastClick()) {
                    return;
                }
                new SweetAlertDialog(this)
                        .setTitleText("提示")
                        .setContentText("是否退出登录?")
                        .setConfirmText("确定")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                UserInfoSharePreference.getInstance().setTOKEN("");
                                File f = new File(FileUtils.SDPATH, "waycube_user_head.png");
                                if (f.exists()) {
                                    f.delete();
                                }
                                Intent intent = getIntent();
                                meSettingActivity.this.setResult(7, intent);
                                finish();
                            }
                        }).show();
                break;
        }
    }

    private void clearCache() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("正在清除   ");
        pDialog.show();
        pDialog.setCancelable(false);
        new CountDownTimer(800 * 7, 800) {  //总时间，间隔时间
            public void onTick(long millisUntilFinished) {
                i++;
                switch (i) {
                    case 0:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
                        pDialog.setTitleText("正在清除.  ");
                        break;
                    case 1:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
                        pDialog.setTitleText("正在清除.. ");
                        break;
                    case 2:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                        pDialog.setTitleText("正在清除...");
                        break;
                    case 3:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_20));
                        pDialog.setTitleText("正在清除   ");
                        break;
                    case 4:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_blue_grey_80));
                        pDialog.setTitleText("正在清除.  ");
                        break;
                    case 5:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.warning_stroke_color));
                        pDialog.setTitleText("正在清除.. ");
                        break;
                    case 6:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                        pDialog.setTitleText("正在清除...");
                        break;
                }
            }

            public void onFinish() {
                i = -1;
                pDialog.setTitleText("清除成功!")
                        .setConfirmText("关闭")
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                try {
                    //DataCleanManagerUtils.getCacheSize(mContext.getCacheDir());
                    cache.setText(DataCleanManagerUtils.getCacheSize(mContext.getCacheDir()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /**
     * 重写获取指定跳转Activity返回的结果
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUESTCODE && resultCode == RESULTCODE) {
            Bundle bundle = intent.getExtras();
            String data = bundle.getString("testResult");
            ToastUtils.getInstance().showToast("获取返回码结果:" + data);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtils.getInstance().cancelToast();
    }
}
