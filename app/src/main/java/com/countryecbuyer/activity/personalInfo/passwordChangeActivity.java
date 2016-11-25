package com.countryecbuyer.activity.personalInfo;


import android.support.v7.widget.Toolbar;
import android.view.View;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;

/**
 * 修改密码
 */
public class passwordChangeActivity extends BaseActivity {
    private Toolbar toolbar;

    //注意检测密码 8到20字符

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_password_change;
    }

    @Override
    protected void initView() {
        toolbar = customFindViewById(R.id.toolbar);
        toolbar.setTitle("修改密码");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.icon_back_gray);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
