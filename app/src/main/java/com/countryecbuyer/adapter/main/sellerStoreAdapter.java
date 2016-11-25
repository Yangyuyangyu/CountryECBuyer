package com.countryecbuyer.adapter.main;

import android.content.Context;
import android.content.Intent;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.DetailGoodsActivity;
import com.countryecbuyer.adapter.base.SolidRVBaseAdapter;
import com.countryecbuyer.bean.main.sellerStoreBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/28.卖家店铺
 */
public class sellerStoreAdapter extends SolidRVBaseAdapter<sellerStoreBean> {

    public sellerStoreAdapter(Context context, List<sellerStoreBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_seller_store;
    }

    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, DetailGoodsActivity.class);
        mContext.startActivity(intent);

    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final sellerStoreBean bean) {

    }
}
