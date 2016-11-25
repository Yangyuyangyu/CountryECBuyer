package com.countryecbuyer.activity.main;


import android.net.Uri;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.countryecbuyer.MainActivity;
import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.activity.msg.PushMessageActivity;
import com.countryecbuyer.adapter.main.sellerStoreAdapter;
import com.countryecbuyer.bean.main.sellerStoreBean;
import com.countryecbuyer.fragment.bottom_sheet.BS_WeiXinShareFragment;
import com.countryecbuyer.utils.SystemShareUtils;
import com.countryecbuyer.utils.ToastUtils;
import com.countryecbuyer.view.MyScrollView;
import com.countryecbuyer.view.Pop_msg_View;

import java.util.ArrayList;
import java.util.List;

/**
 * 卖家店铺详情页
 */
public class SellerStoreActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final int REFRESH_COMPLETE = 0X109;
    private static final int REFRESH_LOAD = 0X107;
    private ImageView back, point;
    private TextView title;

    private Pop_msg_View popview;

    private RecyclerView recyclerView;
    private sellerStoreAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private MyScrollView myScrollView;

    private TextView shop_name;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    adapter.clear();
                    getData();
                    swipeRefreshLayout.setRefreshing(false);
                    ToastUtils.getInstance().showToast("刷新完成");
                    break;
                case REFRESH_LOAD:
                    getData();
                    break;
            }
        }
    };

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_seller_store;
    }

    @Override
    protected void initData() {
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 100);
    }

    @Override
    protected void initView() {
        back = customFindViewById(R.id.my_top_actionbar_back);
        title = customFindViewById(R.id.my_top_actionbar_title);
        point = customFindViewById(R.id.my_top_actionbar_point);
        shop_name = customFindViewById(R.id.view_head_name);//店铺名
        recyclerView = customFindViewById(R.id.seller_store_recyclerView);
        swipeRefreshLayout = customFindViewById(R.id.seller_store_swipeRefreshlayout);
        myScrollView = customFindViewById(R.id.seller_store_scrollview);
        adapter = new sellerStoreAdapter(this, new ArrayList<sellerStoreBean>());
        point.setVisibility(View.VISIBLE);
        title.setText("店铺精选");
        back.setOnClickListener(this);
        point.setOnClickListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(gridLayoutManager);

        swipeRefreshLayout.setColorSchemeResources(R.color.swipe_color_1,
                R.color.swipe_color_2,
                R.color.swipe_color_3,
                R.color.swipe_color_4);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setProgressBackgroundColor(R.color.swipe_background_color);
        swipeRefreshLayout.setOnRefreshListener(this);
        myScrollView.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onBottomArrived() {
                //滑倒底部了
                getData();
            }

            @Override
            public void onScrollStateChanged(MyScrollView view, int scrollState) {
                //滑动状态改变
                if (scrollState == MyScrollView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //mHandler.sendEmptyMessageDelayed(0, 3000);
                }
            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                //滑动位置改变
            }
        });
    }

    private void getData() {
        List<sellerStoreBean> list = new ArrayList<sellerStoreBean>();
        sellerStoreBean s1 = new sellerStoreBean();
        sellerStoreBean s2 = new sellerStoreBean();
        sellerStoreBean s3 = new sellerStoreBean();
        sellerStoreBean s4 = new sellerStoreBean();
        sellerStoreBean s5 = new sellerStoreBean();
        sellerStoreBean s6 = new sellerStoreBean();
        sellerStoreBean s7 = new sellerStoreBean();
        sellerStoreBean s8 = new sellerStoreBean();
        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);
        list.add(s5);
        list.add(s6);
        list.add(s7);
        list.add(s8);
        adapter.addAll(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_top_actionbar_back:
                finish();
                break;
            case R.id.my_top_actionbar_point:
                showPop();
                break;

        }
    }

    private void showPop() {
        if (popview == null) {
            int ww = ViewGroup.LayoutParams.WRAP_CONTENT;
            int hh = ViewGroup.LayoutParams.WRAP_CONTENT;
            PopLintener paramOnClickListener = new PopLintener();
            popview = new Pop_msg_View(SellerStoreActivity.this, paramOnClickListener, ww, hh);
            popview.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        popview.dismiss();
                    }
                }
            });
        }
        popview.setFocusable(true);
        popview.showAsDropDown(point);
        popview.update();
    }

    /**
     * popWindown监听
     */
    class PopLintener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pop_msg_view_msgLL:
                    startActivityWithoutExtras(PushMessageActivity.class);
                    popview.dismiss();
                    break;
                case R.id.pop_msg_view_mainLL:
                    startActivityWithoutExtras(MainActivity.class);
                    MainActivity.controller.setSelect("yh");
                    popview.dismiss();
                    finish();
                    break;
                case R.id.pop_msg_view_shareLL:
                    popview.dismiss();
                    new BS_WeiXinShareFragment().show(getSupportFragmentManager(), R.id.seller_store_bottomSheetLayout);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 1500);
    }

    @Override
    protected void onDestroy() {
        popview=null;
        ToastUtils.getInstance().cancelToast();
        super.onDestroy();
    }
}
