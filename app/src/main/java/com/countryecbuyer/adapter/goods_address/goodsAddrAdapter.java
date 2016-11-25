package com.countryecbuyer.adapter.goods_address;

import android.content.Context;
import android.content.Intent;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.receive_address.ReceiveAddrDetailActivity;
import com.countryecbuyer.adapter.base.SolidRVBaseAdapter;
import com.countryecbuyer.bean.goods_address.goodsAddrBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/24.收货地址
 */
public class goodsAddrAdapter extends SolidRVBaseAdapter<goodsAddrBean> {

    public goodsAddrAdapter(Context context, List<goodsAddrBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_goods_address;
    }

    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, ReceiveAddrDetailActivity.class);
        mContext.startActivity(intent);

    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final goodsAddrBean bean) {

    }
}
