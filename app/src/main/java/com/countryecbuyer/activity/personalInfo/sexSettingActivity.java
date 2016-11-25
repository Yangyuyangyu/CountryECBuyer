package com.countryecbuyer.activity.personalInfo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.utils.ToastUtils;

/**
 * 性别修改
 */
public class sexSettingActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private LinearLayout secrecy, boy, girl;
    private ImageView secrecyImg, boyImg, girlImg;
    private String getSex = null;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_sex_setting;
    }

    @Override
    protected void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) getSex = bundle.getString("the_sex");
    }

    @Override
    protected void initView() {
        toolbar = customFindViewById(R.id.toolbar);
        toolbar.setTitle("性别");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.icon_back_gray);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        secrecy = customFindViewById(R.id.sex_secrecyLL);
        boy = customFindViewById(R.id.sex_boyLL);
        girl = customFindViewById(R.id.sex_girlLL);
        secrecyImg = customFindViewById(R.id.sex_secrecyImg);
        boyImg = customFindViewById(R.id.sex_boyImg);
        girlImg = customFindViewById(R.id.sex_girlImg);
        secrecy.setOnClickListener(this);
        boy.setOnClickListener(this);
        girl.setOnClickListener(this);

        if (getSex.equals("保密")) {
            secrecyImg.setVisibility(View.VISIBLE);
            boyImg.setVisibility(View.INVISIBLE);
            girlImg.setVisibility(View.INVISIBLE);
        } else if (getSex.equals("男")) {
            secrecyImg.setVisibility(View.INVISIBLE);
            boyImg.setVisibility(View.VISIBLE);
            girlImg.setVisibility(View.INVISIBLE);
        } else if (getSex.equals("女")) {
            secrecyImg.setVisibility(View.INVISIBLE);
            boyImg.setVisibility(View.INVISIBLE);
            girlImg.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sex_secrecyLL:
                setIntentR("保密");
                break;
            case R.id.sex_boyLL:
                setIntentR("男");
                break;
            case R.id.sex_girlLL:
                setIntentR("女");
                break;
        }
    }

    private void setIntentR(String content) {
        Intent intent = getIntent();
        intent.putExtra("sex", content);
        sexSettingActivity.this.setResult(4, intent);
        finish();
        ToastUtils.getInstance().showToastCenter("保存成功");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtils.getInstance().cancelToast();
    }
}
