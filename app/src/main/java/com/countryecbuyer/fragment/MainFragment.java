package com.countryecbuyer.fragment;


import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.msg.PushMessageActivity;
import com.countryecbuyer.adapter.testAdapter;
import com.countryecbuyer.bean.mainBean;
import com.countryecbuyer.fragment.base.BaseFragment;
import com.countryecbuyer.utils.FileUtils;
import com.countryecbuyer.utils.HttpUtils;
import com.countryecbuyer.utils.NetworkUtils;
import com.countryecbuyer.utils.StringUtils;
import com.countryecbuyer.utils.ToastUtils;
import com.countryecbuyer.view.AutoSwipeRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by waycube-yyb on 2016/4/27. 首页
 */
public class MainFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final int REFRESH_COMPLETE = 0X110;
    private int index = 0;
    private RelativeLayout topRE;//顶部
    private RelativeLayout topMsg;

    DecimalFormat df = new DecimalFormat("0.00");

    private AutoSwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private testAdapter adapter;
    private LinearLayoutManager LayoutManager;
    private FloatingActionButton fb;//返回顶部按钮

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    adapter.clear();
                    getData();
                    swipeRefreshLayout.setEnabled(true);
                    swipeRefreshLayout.setRefreshing(false);
                    ToastUtils.getInstance().showToast("刷新完成");
                    recyclerView.setVisibility(View.VISIBLE);
                    index = 0;
                    break;
            }
        }
    };

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_main_test;
    }

    @Override
    protected void init() {
    }

    @Override
    protected void initData() {
        swipeRefreshLayout.autoRefresh();
    }

    @Override
    protected void initView() {
        topRE = customFindViewById(R.id.main_actionbar_reLL);
        topMsg = customFindViewById(R.id.view_message_relayout);
        swipeRefreshLayout = customFindViewById(R.id.fragment_main_swipeRefreshLayout_test);
        recyclerView = customFindViewById(R.id.fragment_main_recyclerview_test);
        fb = customFindViewById(R.id.fragment_main_fab_test);
        adapter = new testAdapter(getActivity(), new ArrayList<mainBean>());
        LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.addOnScrollListener(mOnScrollListener);
        swipeRefreshLayout.setColorSchemeResources(R.color.swipe_color_1,
                R.color.swipe_color_2,
                R.color.swipe_color_3,
                R.color.swipe_color_4);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setProgressBackgroundColor(R.color.swipe_background_color);
        swipeRefreshLayout.setOnRefreshListener(this);
        fb.setOnClickListener(this);
        topMsg.setOnClickListener(this);

    }

    private void getData() {
        List<mainBean> mbList = new ArrayList<mainBean>();
        mainBean mb1 = new mainBean();
        mainBean mb2 = new mainBean();
        mainBean mb3 = new mainBean();
        mainBean mb4 = new mainBean();
        mbList.add(mb1);
        mbList.add(mb2);
        mbList.add(mb3);
        mbList.add(mb4);
        adapter.addAll(mbList);
    }

    /**
     * 加载与缓存
     */
    private void loadData() {
        final String reqUrl = "";
        if (!NetworkUtils.isNetworkConnected(getMContext())) {//no network
            String result = obtainOfflineData(reqUrl);//读取缓存
            onDataSuccessReceived(result);
//            MySnackbar.getmInstance().showMessage(fab, "当前无网络连接");
        } else
            HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
                @Override
                public void onLoading() {
                }

                @Override
                public void onSuccess(String result) {
                    //刷新缓存数据
//                    if (mCurrentAction == ACTION_REFRESH) {
//                        storeOfflineData(reqUrl, result);
//                    }
                    onDataSuccessReceived(result);
                }

                @Override
                public void onError(Exception e) {
                    onDataErrorReceived();
                }
            });
    }

    /**
     * 获取数据失败
     */
    private void onDataErrorReceived() {
//        mLLReloadWarp.setVisibility(View.VISIBLE);
//        loadComplete();
    }

    /**
     * 获取数据成功
     */
    private void onDataSuccessReceived(String result) {
        if (!StringUtils.isNullOrEmpty(result)) {
            List<mainBean> list = parseData(result);
//            mAdapter.addAll(list);
//            loadComplete();
//            mLLReloadWarp.setVisibility(View.GONE);
        } else {
            onDataErrorReceived();
        }
    }

    /**
     * json解析返回List
     */
    private List parseData(String result) {
        List<mainBean> list = null;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
            if (jsonObject.getString("code").equals("0")) {
                Gson gson = new Gson();
                list = gson.fromJson(
                        jsonObject.getString("data"),
                        new TypeToken<List<mainBean>>() {
                        }.getType());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            list = new ArrayList<>();
        }
        return list;

    }

    /**
     * 缓存离线数据
     */
    public void storeOfflineData(String url, String result) {
        try {
            FileUtils.writeFile(getOfflineDir(url), result, "UTF-8", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取离线数据
     */
    public String obtainOfflineData(String url) {
        String result = null;
        try {
            result = FileUtils.readFile(getOfflineDir(url), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * get the dir of the offline data
     */
    protected String getOfflineDir(String url) {
        return FileUtils.getCacheDir(getMContext()) + File.separator + "waycube_main_ache" + File.separator + StringUtils.md5(url);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_main_fab_test:
                recyclerView.scrollToPosition(0);
                fb.setVisibility(View.INVISIBLE);
                topRE.setAlpha(0);
                break;
            case R.id.view_message_relayout:
                startActivityWithoutExtras(PushMessageActivity.class);
                break;
        }
    }


    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        private int lastVisibleItem;
        private int firstItemPositin;
        private float distance;
        private float distance_;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            /**
             * scrollState有三种状态，分别是SCROLL_STATE_IDLE、SCROLL_STATE_TOUCH_SCROLL、SCROLL_STATE_FLING
             * SCROLL_STATE_IDLE是当屏幕停止滚动时
             * SCROLL_STATE_TOUCH_SCROLL是当用户在以触屏方式滚动屏幕并且手指仍然还在屏幕上时
             * SCROLL_STATE_FLING是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
             */
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                if (index < 5) {
                    if (!swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setEnabled(false);
                        adapter.setMoreStatus(adapter.LOADING_MORE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getData();
                                swipeRefreshLayout.setEnabled(true);
                                adapter.setMoreStatus(adapter.PULLUP_LOAD_MORE);
                                adapter.notifyDataSetChanged();
                                index++;
                            }
                        }, 1000);
                    }
                } else {
                    adapter.setMoreStatus(adapter.LOADING_COMPLETE);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = LayoutManager.findLastVisibleItemPosition();
            firstItemPositin = LayoutManager.findFirstVisibleItemPosition();
            distance = (float) getScollYDistance() / 4000;
            distance_ = Float.valueOf(df.format(distance));
            if (firstItemPositin < 2) {
                if (distance_ > 1) {
                    if (fb.getVisibility() == View.INVISIBLE) fb.setVisibility(View.VISIBLE);
                } else {
                    if (fb.getVisibility() == View.VISIBLE) fb.setVisibility(View.INVISIBLE);
                }
                topRE.setAlpha(distance_);
            }
        }
    };

    /**
     * RecyclerView已滑动的距离
     */
    public long getScollYDistance() {
        int position = LayoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = LayoutManager.findViewByPosition(position);
        long itemHeight = firstVisiableChildView.getHeight();
        return (position) * itemHeight - firstVisiableChildView.getTop();
    }


}
