package com.countryecbuyer.adapter;

import android.content.Context;
import android.content.Intent;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.main.mainItemActivity;
import com.countryecbuyer.adapter.base.SolidRVBaseAdapter;
import com.countryecbuyer.bean.mainBottomBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/1.更多精彩回顾
 */
public class mainBottomImgAdapter extends SolidRVBaseAdapter<mainBottomBean> {

    public mainBottomImgAdapter(Context context, List<mainBottomBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_main_bottom_img;
    }

    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, mainItemActivity.class);
        mContext.startActivity(intent);

    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final mainBottomBean bean) {
//        holder.setImageFromInternet(R.id.item_main_bottom_img, bean.getImg());

    }
}
