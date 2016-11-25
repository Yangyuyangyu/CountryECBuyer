package com.countryecbuyer.adapter.main;

import android.content.Context;
import android.content.Intent;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.main.mainItemActivity;
import com.countryecbuyer.adapter.base.SolidRVBaseAdapter;
import com.countryecbuyer.bean.main.mainFootBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/6.首页底部最热商品推荐
 */
public class mainFootAdapter extends SolidRVBaseAdapter<mainFootBean> {

    public mainFootAdapter(Context context, List<mainFootBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_main_foot;
    }

    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, mainItemActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final mainFootBean bean) {

    }
}
