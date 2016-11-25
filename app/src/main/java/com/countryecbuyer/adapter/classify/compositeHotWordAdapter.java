package com.countryecbuyer.adapter.classify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.search.SearchActivity;
import com.countryecbuyer.adapter.base.SolidRVBaseAdapter;
import com.countryecbuyer.bean.classify.compositeHotWordBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/15.综合排行热搜
 */
public class compositeHotWordAdapter extends SolidRVBaseAdapter<compositeHotWordBean> {
    public compositeHotWordAdapter(Context context, List<compositeHotWordBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_classify_hot_word;
    }

    @Override
    protected void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("the_hot_word", "热搜词测试");
        Intent intent = new Intent(mContext, SearchActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);

    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final compositeHotWordBean bean) {
    }
}
