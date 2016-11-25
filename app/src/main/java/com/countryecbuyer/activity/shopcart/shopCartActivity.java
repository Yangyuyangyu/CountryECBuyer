package com.countryecbuyer.activity.shopcart;


import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.adapter.shopping_cart.shoppingCartAdapter;
import com.countryecbuyer.bean.shopping_cart.shoppingCartBean;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车 activity
 */
public class shopCartActivity extends BaseActivity implements View.OnClickListener {
    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;
    private int mCurrentAction = ACTION_REFRESH;

    private ImageView back;
    private TextView title;
    private XRecyclerView recyclerView;
    private shoppingCartAdapter adapter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_shop_cart;
    }

    @Override
    protected void initData() {
        recyclerView.setRefreshing(true);
    }

    @Override
    protected void initView() {
        back = customFindViewById(R.id.my_top_actionbar_back);
        title = customFindViewById(R.id.my_top_actionbar_title);
        title.setText("购物车");
        recyclerView = customFindViewById(R.id.activity_shop_cart_recyclerView);
        adapter = new shoppingCartAdapter(this, new ArrayList<shoppingCartBean>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
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

        //是否进入编辑模式
        adapter.setOnEditModeState(new shoppingCartAdapter.OnEditModeState() {
            @Override
            public void getEditState(boolean editmode) {
                if (editmode) {

                } else {

                }
            }
        });
        back.setOnClickListener(this);


    }

    private void getData() {
        List<shoppingCartBean> list = new ArrayList<shoppingCartBean>();
        shoppingCartBean c1 = new shoppingCartBean();
        shoppingCartBean c2 = new shoppingCartBean();
        shoppingCartBean c3 = new shoppingCartBean();
        list.add(c1);
        list.add(c2);
        list.add(c3);
        adapter.addAll(list);
        loadComplete();
        recyclerView.setLoadingMoreEnabled(false);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_top_actionbar_back:
                finish();
                break;

        }
    }
}
