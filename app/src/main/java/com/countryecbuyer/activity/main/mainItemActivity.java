package com.countryecbuyer.activity.main;


import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.countryecbuyer.MainActivity;
import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.activity.msg.PushMessageActivity;
import com.countryecbuyer.adapter.main.mainItemAdapter;
import com.countryecbuyer.bean.main.mainItemBean;
import com.countryecbuyer.fragment.bottom_sheet.BS_WeiXinShareFragment;
import com.countryecbuyer.utils.ToastUtils;
import com.countryecbuyer.utils.ViewUtils;
import com.countryecbuyer.view.AutoSwipeRefreshLayout;
import com.countryecbuyer.view.MyScrollView;
import com.countryecbuyer.view.Pop_msg_View;

import java.util.ArrayList;
import java.util.List;

/**
 * 点击首页列表Item进入展示页,展示类型不同
 */
public class mainItemActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private static final int REFRESH_COMPLETE = 0X111;
    private int pageIndex = 0;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private mainItemAdapter adapter;
    private AutoSwipeRefreshLayout swipeRefreshLayout;
    private MyScrollView scrollView;
    private FloatingActionButton fb;
    private LinearLayout footLL;
    private Pop_msg_View popview;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    adapter.clear();
                    getData();
                    swipeRefreshLayout.setRefreshing(false);
                    scrollView.setVisibility(View.VISIBLE);
                    ToastUtils.getInstance().showToast("刷新完成");
                    break;


            }
        }
    };

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main_item;
    }

    @Override
    protected void init() {
    }

    @Override
    protected void initData() {
        swipeRefreshLayout.autoRefresh();
        recyclerView.setFocusable(true);
    }

    @Override
    protected void initView() {
        fb = customFindViewById(R.id.main_item_fab);
        toolbar = customFindViewById(R.id.toolbar);
        footLL = customFindViewById(R.id.main_item_foot_LL);
        scrollView = customFindViewById(R.id.main_item_scrollView);
        recyclerView = customFindViewById(R.id.main_item_recyclerview);
        adapter = new mainItemAdapter(this, new ArrayList<mainItemBean>());
        swipeRefreshLayout = customFindViewById(R.id.main_item_swipeRefreshLayout);
        setSupportActionBar(toolbar);
        toolbar.setTitle("欧洲杯 冠军球衣看过来");
        toolbar.setNavigationIcon(R.mipmap.icon_back_gray);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.swipe_color_1,
                R.color.swipe_color_2,
                R.color.swipe_color_3,
                R.color.swipe_color_4);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setProgressBackgroundColor(R.color.swipe_background_color);
        swipeRefreshLayout.setOnRefreshListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setFocusable(false);
        fb.setOnClickListener(this);
        scrollView.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onBottomArrived() {
                //滑倒底部了
                if (pageIndex >= 2) {
                    if (footLL.getVisibility() == View.GONE)
                        footLL.setVisibility(View.VISIBLE);
                } else {
                    getData();
                }
                pageIndex++;

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
                if (oldt / 100 > 8) {
                    if (fb.getVisibility() == View.INVISIBLE) fb.setVisibility(View.VISIBLE);
                } else {
                    if (fb.getVisibility() == View.VISIBLE) fb.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void getData() {
        List<mainItemBean> list = new ArrayList<mainItemBean>();
        mainItemBean b1 = new mainItemBean();
        mainItemBean b2 = new mainItemBean();
        mainItemBean b3 = new mainItemBean();
        mainItemBean b4 = new mainItemBean();
        mainItemBean b5 = new mainItemBean();
        mainItemBean b6 = new mainItemBean();
        list.add(b1);
        list.add(b2);
        list.add(b3);
        list.add(b4);
        list.add(b5);
        list.add(b6);
        adapter.addAll(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_item_fab:
                scrollView.scrollTo(0, 0);
                fb.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void showPop() {
        if (popview == null) {
            int ww = ViewGroup.LayoutParams.WRAP_CONTENT;
            int hh = ViewGroup.LayoutParams.WRAP_CONTENT;
            PopLintener paramOnClickListener = new PopLintener();
            popview = new Pop_msg_View(mainItemActivity.this, paramOnClickListener, ww, hh);
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
        popview.showAsDropDown(toolbar, ViewUtils.getScreenWidth(this), 0);
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
                    new BS_WeiXinShareFragment().show(getSupportFragmentManager(), R.id.main_item_bottomSheetLayout);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menue_toolbar_action_point, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.toolbar_action_point) {
            showPop();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        popview=null;
        toolbar=null;
        ToastUtils.getInstance().cancelToast();
    }
}
