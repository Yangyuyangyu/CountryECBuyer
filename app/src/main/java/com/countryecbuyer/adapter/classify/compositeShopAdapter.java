package com.countryecbuyer.adapter.classify;

import android.content.Context;
import android.content.Intent;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.main.SellerStoreActivity;
import com.countryecbuyer.adapter.base.SolidRVBaseAdapter;
import com.countryecbuyer.bean.classify.compositeShopBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/2. TOP店铺榜
 */
public class compositeShopAdapter extends SolidRVBaseAdapter<compositeShopBean> {
    public compositeShopAdapter(Context context, List<compositeShopBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_classify_composite_shop;
    }

    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, SellerStoreActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final compositeShopBean bean) {
    }
}
