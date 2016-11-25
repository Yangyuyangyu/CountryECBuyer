package com.countryecbuyer.activity;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.adapter.mainBottomImgAdapter;
import com.countryecbuyer.adapter.wonderful_review.wonderfulReviewAdapter;
import com.countryecbuyer.bean.mainBean;
import com.countryecbuyer.bean.mainBottomBean;
import com.countryecbuyer.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 精彩回顾
 */
public class WonderfulReviewActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private Toolbar toolbar;
    private boolean styleIndex = true;//toolbar排版样式
    private LinearLayoutManager LayoutManager;
    private GridLayoutManager gridLayoutManager;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private wonderfulReviewAdapter adapter;
    private mainBottomImgAdapter adapter_bottom;//grid样式排版


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_wonderful_review;
    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    protected void initView() {
        toolbar = customFindViewById(R.id.toolbar);
        toolbar.setTitle("精彩回顾");
        setSupportActionBar(toolbar);//调用返回获取焦点
        toolbar.setNavigationIcon(R.mipmap.icon_back_gray);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        swipeRefreshLayout = customFindViewById(R.id.wonderful_review_swipeLayout);
        mRecyclerView = customFindViewById(R.id.wonderful_review_recyclerview);
        adapter = new wonderfulReviewAdapter(this, new ArrayList<mainBean>());
        adapter_bottom = new mainBottomImgAdapter(this, new ArrayList<mainBottomBean>());
        LayoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(LayoutManager);
        swipeRefreshLayout.setColorSchemeResources(R.color.swipe_color_1,
                R.color.swipe_color_2,
                R.color.swipe_color_3,
                R.color.swipe_color_4);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setProgressBackgroundColor(R.color.swipe_background_color);
        swipeRefreshLayout.setOnRefreshListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menue_wonderful_review, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.toolbar_wdf_review_style:
                //样式排版
                if (!styleIndex) {
                    item.setIcon(R.drawable.icon_card);
                    styleIndex = !styleIndex;
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setAdapter(adapter);
                    mRecyclerView.setLayoutManager(LayoutManager);
                } else {
                    item.setIcon(R.drawable.icon_list);
                    styleIndex = !styleIndex;
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setAdapter(adapter_bottom);
                    mRecyclerView.setLayoutManager(gridLayoutManager);
                    adapter_bottom.clear();
                    gridData();
                }
                break;
            case R.id.toolbar_wdf_review_today:
                mRecyclerView.scrollToPosition(0);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getData() {
        List<mainBean> mbList = new ArrayList<mainBean>();
        mainBean mb1 = new mainBean();
        mainBean mb2 = new mainBean();
        mainBean mb3 = new mainBean();
        mainBean mb4 = new mainBean();
        mainBean mb5 = new mainBean();
        mbList.add(mb1);
        mbList.add(mb2);
        mbList.add(mb3);
        mbList.add(mb4);
        mbList.add(mb5);
        adapter.addAll(mbList);
    }

    private void gridData() {
        List<mainBottomBean> mbList = new ArrayList<mainBottomBean>();
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
        adapter_bottom.addAll(mbList);
    }

    @Override
    public void onRefresh() {
        if (styleIndex) {   //list样式刷新
            adapter.clear();
            getData();
            swipeRefreshLayout.setEnabled(true);
            swipeRefreshLayout.setRefreshing(false);
            ToastUtils.getInstance().showToast("列表版式刷新完成");
        } else {   //grid样式刷新
            adapter_bottom.clear();
            gridData();
            swipeRefreshLayout.setEnabled(true);
            swipeRefreshLayout.setRefreshing(false);
            ToastUtils.getInstance().showToast("网格版式刷新完成");

        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtils.getInstance().cancelToast();
    }

}
