package com.countryecbuyer.adapter.order;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.countryecbuyer.fragment.order.allOrderFragment;
import com.countryecbuyer.fragment.order.waitDeliverGoodsFragment;
import com.countryecbuyer.fragment.order.waitOrderFragment;
import com.countryecbuyer.fragment.order.waitPayFragment;
import com.countryecbuyer.fragment.order.waitReceivingFragment;
import com.countryecbuyer.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.订单list
 */
public class orderListAdapter extends FragmentPagerAdapter {

    private final List<String> mTitleList;
    private final Context mContext;

    public orderListAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        mTitleList = new ArrayList<>();
        mTitleList.add("全部");
        mTitleList.add("待付款");
        mTitleList.add("待接单");
        mTitleList.add("待发货");
        mTitleList.add("待收货");
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = ViewUtils.createFragment(allOrderFragment.class, false);
        switch (getPageTitle(position).toString()) {
            case "全部":
//                if (mBean != null) {
//                    if (mBean.getWeek() != null) {
//                        bundle.putString("text", mBean.getWeek());
//                    } else {
//                        bundle.putString("text", "");
//                    }
//                }
                fragment = ViewUtils.createFragment(allOrderFragment.class, false);
                break;
            case "待付款":
                fragment = ViewUtils.createFragment(waitPayFragment.class, false);
                break;
            case "待接单":
                fragment = ViewUtils.createFragment(waitOrderFragment.class, false);
                break;
            case "待发货":
                fragment = ViewUtils.createFragment(waitDeliverGoodsFragment.class, false);
                break;
            case "待收货":
                fragment = ViewUtils.createFragment(waitReceivingFragment.class, false);
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
