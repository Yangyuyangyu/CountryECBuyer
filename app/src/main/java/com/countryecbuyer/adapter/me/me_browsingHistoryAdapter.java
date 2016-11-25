package com.countryecbuyer.adapter.me;

import android.content.Context;
import android.content.Intent;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.main.mainItemActivity;
import com.countryecbuyer.adapter.base.SolidRVBaseAdapter;
import com.countryecbuyer.bean.me.BrowsingHistoryBean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/13.浏览记录
 */
public class me_browsingHistoryAdapter extends SolidRVBaseAdapter<BrowsingHistoryBean> {

    public me_browsingHistoryAdapter(Context context, List<BrowsingHistoryBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_me_browshistory;
    }

    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, mainItemActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final BrowsingHistoryBean bean) {

    }
}
