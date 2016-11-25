package com.countryecbuyer.activity.me;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.adapter.order.orderListAdapter;

/**
 * 卖家订单 全部 待付款 待接单 待发货 待收货 (不包括已完成)
 */
public class OrderActivity extends BaseActivity {
    private Toolbar toolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private int getOrderType = 0;//获取Bundle订单类型

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_order;
    }

    @Override
    protected void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            getOrderType = bundle.getInt("order_type");
        }
    }

    @Override
    protected void initData() {
        getData();
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
        mViewPager = customFindViewById(R.id.order_viewpager);
        mTabLayout = customFindViewById(R.id.order_tablayout);
        orderListAdapter adapter = new orderListAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(getOrderType).select();
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.red_color));

    }

    private void getData() {

    }

    /**
     * 保存选中状态
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("POSITION", mTabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mViewPager.setCurrentItem(savedInstanceState.getInt("POSITION"));
    }


}
