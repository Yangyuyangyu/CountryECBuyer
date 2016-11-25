package com.countryecbuyer.activity.me;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.adapter.me.me_have_buyAdapter;

/**
 * 我购买过
 */
public class meHaveBuyActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView top_title;
    private TabLayout mTabLayout;
    private ViewPager viewPager;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_me_have_buy;
    }

    @Override
    protected void initView() {
        mTabLayout = customFindViewById(R.id.have_buy_tablayout);
        viewPager = customFindViewById(R.id.have_buy_viewPager);
        mTabLayout.addTab(mTabLayout.newTab().setText("商品"));
        mTabLayout.addTab(mTabLayout.newTab().setText("店铺"));
        back = customFindViewById(R.id.my_top_actionbar_back);
        top_title = customFindViewById(R.id.my_top_actionbar_title);
        top_title.setText("我买过的");
        me_have_buyAdapter adapter = new me_have_buyAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(viewPager);
        mTabLayout.getTabAt(0).select();
        back.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_top_actionbar_back:
                finish();
                break;
        }
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
        viewPager.setCurrentItem(savedInstanceState.getInt("POSITION"));
    }
}
