package com.countryecbuyer.adapter.push_message;

import android.content.Context;

import com.countryecbuyer.R;
import com.countryecbuyer.adapter.base.SolidRVBaseAdapter;
import com.countryecbuyer.bean.push_message.pushMessageBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/17.推送消息
 */
public class pushMessageAdapter extends SolidRVBaseAdapter<pushMessageBean> {

    public pushMessageAdapter(Context context, List<pushMessageBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_push_message;
    }

    @Override
    protected void onItemClick(int position) {

    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final pushMessageBean bean) {

    }
}
