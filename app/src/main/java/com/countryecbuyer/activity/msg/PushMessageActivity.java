package com.countryecbuyer.activity.msg;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.adapter.push_message.pushMessageAdapter;
import com.countryecbuyer.bean.push_message.pushMessageBean;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息推送中心
 */
public class PushMessageActivity extends BaseActivity {
    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;
    private int mCurrentAction = ACTION_REFRESH;

    private Toolbar toolbar;
    private XRecyclerView recyclerView;
    private pushMessageAdapter adapter;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_push_message;
    }

    @Override
    protected void initData() {
        recyclerView.setRefreshing(true);
    }

    @Override
    protected void initView() {
        toolbar = customFindViewById(R.id.toolbar);
        toolbar.setTitle("消息中心");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.icon_back_gray);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = customFindViewById(R.id.push_msg_recyclerView);
        adapter = new pushMessageAdapter(this, new ArrayList<pushMessageBean>());
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
        List<pushMessageBean> list = new ArrayList<pushMessageBean>();
        pushMessageBean p1 = new pushMessageBean();
        pushMessageBean p2 = new pushMessageBean();
        pushMessageBean p3 = new pushMessageBean();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        adapter.addAll(list);
        loadComplete();
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

    @Override
    protected void onRestart() {
        switchAction(ACTION_REFRESH);
        super.onRestart();
    }
}
