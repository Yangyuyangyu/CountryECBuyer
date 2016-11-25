package com.countryecbuyer.adapter.me;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.countryecbuyer.fragment.me.me_hadbuy_goodsFragment;
import com.countryecbuyer.fragment.me.me_hadbuy_shopFragment;
import com.countryecbuyer.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/13.我买过的
 */
public class me_have_buyAdapter extends FragmentPagerAdapter {

    private final List<String> mTitleList;
    private final Context mContext;

    public me_have_buyAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context.getApplicationContext();
        mTitleList = new ArrayList<>();
        mTitleList.add("商品");
        mTitleList.add("店铺");
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = ViewUtils.createFragment(me_hadbuy_goodsFragment.class, false);
        switch (getPageTitle(position).toString()) {
            case "店铺":
                fragment = ViewUtils.createFragment(me_hadbuy_shopFragment.class, false);
                break;
            case "商品":
                fragment = ViewUtils.createFragment(me_hadbuy_goodsFragment.class, false);
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
