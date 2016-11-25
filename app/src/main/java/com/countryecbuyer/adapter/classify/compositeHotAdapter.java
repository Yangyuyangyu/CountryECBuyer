package com.countryecbuyer.adapter.classify;

import android.content.Context;
import android.content.Intent;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.DetailGoodsActivity;
import com.countryecbuyer.adapter.base.SolidRVBaseAdapter;
import com.countryecbuyer.bean.classify.compositeHotBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/2.综合 热销商品总榜
 */
public class compositeHotAdapter extends SolidRVBaseAdapter<compositeHotBean> {
    public compositeHotAdapter(Context context, List<compositeHotBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_classify_composite_hot;
    }

    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, DetailGoodsActivity.class);
        mContext.startActivity(intent);

    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final compositeHotBean bean) {
    }
}
