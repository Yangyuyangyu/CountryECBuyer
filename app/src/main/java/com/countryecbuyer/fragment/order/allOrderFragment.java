package com.countryecbuyer.fragment.order;

import android.support.v7.widget.LinearLayoutManager;

import com.countryecbuyer.R;
import com.countryecbuyer.adapter.order.orderAllAdapter;
import com.countryecbuyer.bean.order.orderBean;
import com.countryecbuyer.fragment.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.全部订单
 */
public class allOrderFragment extends BaseFragment {
    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;
    private int mCurrentAction = ACTION_REFRESH;

    private XRecyclerView recyclerView;
    private orderAllAdapter adapter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_order_all;
    }

    @Override
    protected void initData() {
        recyclerView.setRefreshing(true);
    }

    @Override
    protected void initView() {
        recyclerView = customFindViewById(R.id.order_all_recyclerView);
        adapter = new orderAllAdapter(getActivity(), new ArrayList<orderBean>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
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
        List<orderBean> list = new ArrayList<orderBean>();
        orderBean p1 = new orderBean();
        list.add(p1);
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
}
