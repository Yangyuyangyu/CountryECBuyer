package com.countryecbuyer.activity.me;


import android.support.v7.widget.Toolbar;
import android.view.View;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;

/**
 * 已完成订单
 */
public class OrderCompleteActivity extends BaseActivity {
    private Toolbar toolbar;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_order_complete;
    }

    @Override
    protected void initView() {
        toolbar = customFindViewById(R.id.toolbar);
        toolbar.setTitle("我的订单");
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
