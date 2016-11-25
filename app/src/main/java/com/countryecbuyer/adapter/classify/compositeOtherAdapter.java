package com.countryecbuyer.adapter.classify;

import android.content.Context;
import android.content.Intent;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.DetailGoodsActivity;
import com.countryecbuyer.adapter.base.SolidRVBaseAdapter;
import com.countryecbuyer.bean.classify.compositerOtherBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/3.分类其他排行
 */
public class compositeOtherAdapter extends SolidRVBaseAdapter<compositerOtherBean> {
    public compositeOtherAdapter(Context context, List<compositerOtherBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_classify_composite_other;
    }

    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, DetailGoodsActivity.class);
        mContext.startActivity(intent);

    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final compositerOtherBean bean) {
//        holder.setText(R.id.item_classify_other_name, bean.getName());
//        holder.setImageFromInternet(R.id.item_classify_other_img,bean.getImg());
    }
}
