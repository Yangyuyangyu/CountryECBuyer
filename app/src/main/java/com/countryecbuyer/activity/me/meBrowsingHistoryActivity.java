package com.countryecbuyer.activity.me;


import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.adapter.me.me_browsingHistoryAdapter;
import com.countryecbuyer.bean.me.BrowsingHistoryBean;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 浏览记录
 */
public class meBrowsingHistoryActivity extends BaseActivity implements View.OnClickListener {
    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;
    private int mCurrentAction = ACTION_REFRESH;

    private ImageView back;
    private TextView top_title;

    private XRecyclerView recyclerView;
    private me_browsingHistoryAdapter adapter;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_me_browsing_history;
    }

    @Override
    protected void initData() {
        recyclerView.setRefreshing(true);
    }

    @Override
    protected void initView() {
        back = customFindViewById(R.id.my_top_actionbar_back);
        top_title = customFindViewById(R.id.my_top_actionbar_title);
        top_title.setText("浏览记录");

        recyclerView = customFindViewById(R.id.me_browsingHistory_recyclerview);
        back.setOnClickListener(this);

        adapter = new me_browsingHistoryAdapter(this, new ArrayList<BrowsingHistoryBean>());
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                switchAction(ACTION_REFRESH);
            }

            @Override
            public void onLoadMore() {
                switchAction(ACTION_LOAD_MORE);
            }
        });
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotatePulse);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
    }

    private void getData() {
        List<BrowsingHistoryBean> list = new ArrayList<BrowsingHistoryBean>();
        BrowsingHistoryBean b1 = new BrowsingHistoryBean();
        BrowsingHistoryBean b2 = new BrowsingHistoryBean();
        BrowsingHistoryBean b3 = new BrowsingHistoryBean();
        BrowsingHistoryBean b4 = new BrowsingHistoryBean();
        BrowsingHistoryBean b5 = new BrowsingHistoryBean();
        list.add(b1);
        list.add(b2);
        list.add(b3);
        list.add(b4);
        list.add(b5);
        adapter.addAll(list);
        loadComplete();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_top_actionbar_back:
                finish();
                break;
        }
    }

    private void loadComplete() {
        if (mCurrentAction == ACTION_REFRESH)
            recyclerView.refreshComplete();
        if (mCurrentAction == ACTION_LOAD_MORE)
            recyclerView.loadMoreComplete();
    }

    private void switchAction(int action) {
        mCurrentAction = action;
        switch (mCurrentAction) {
            case ACTION_REFRESH:
                adapter.clear();
                break;
            case ACTION_LOAD_MORE:
                break;
        }
        getData();
    }

}
