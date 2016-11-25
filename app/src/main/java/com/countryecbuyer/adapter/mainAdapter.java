package com.countryecbuyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.main.mainItemActivity;
import com.countryecbuyer.adapter.base.SolidRVBaseAdapter;
import com.countryecbuyer.bean.mainBean;
import com.countryecbuyer.bean.mainBottomBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/1.首页
 */
public class mainAdapter extends SolidRVBaseAdapter<mainBean> {

    public mainAdapter(Context context, List<mainBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_main;
    }

    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, mainItemActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final mainBean bean) {
        final RecyclerView recyclerView = holder.getView(R.id.item_recycleView);
        final mainBottomImgAdapter adapter = new mainBottomImgAdapter(mContext, new ArrayList<mainBottomBean>());
        final List<mainBottomBean> mbList = new ArrayList<mainBottomBean>();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        mainBottomBean mb1 = new mainBottomBean();
        mainBottomBean mb2 = new mainBottomBean();
        mainBottomBean mb3 = new mainBottomBean();
        mainBottomBean mb4 = new mainBottomBean();
        mainBottomBean mb5 = new mainBottomBean();
        mainBottomBean mb6 = new mainBottomBean();
        mainBottomBean mb7 = new mainBottomBean();
        mbList.add(mb1);
        mbList.add(mb2);
        mbList.add(mb3);
        mbList.add(mb4);
        mbList.add(mb5);
        mbList.add(mb6);
        mbList.add(mb7);
        adapter.addAll(mbList);
    }


}
