package com.countryecbuyer.fragment.classify;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.countryecbuyer.R;
import com.countryecbuyer.adapter.base.SolidRVBaseAdapter;
import com.countryecbuyer.adapter.classify.compositeHotAdapter;
import com.countryecbuyer.adapter.classify.compositeHotWordAdapter;
import com.countryecbuyer.adapter.classify.compositeShopAdapter;
import com.countryecbuyer.bean.classify.compositeHotBean;
import com.countryecbuyer.bean.classify.compositeHotWordBean;
import com.countryecbuyer.bean.classify.compositeShopBean;
import com.countryecbuyer.fragment.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/2.综合排行
 */
public class compositeRankFragment extends BaseFragment {
    private XRecyclerView hot_sellRecyclerView, hot_shopRecyclerView;
    private RecyclerView hotwordRecyclerView;
    private compositeHotAdapter mAdapter;//热门
    private compositeShopAdapter mBdapter;//店铺
    private compositeHotWordAdapter mCdapter;//热搜

    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;
    private int mCurrentAction = ACTION_REFRESH;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_classify_composite_rank;
    }

    @Override
    protected void init() {
    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    protected void initView() {
        hot_sellRecyclerView = customFindViewById(R.id.fragment_rank_hot_sell_recycleView);
        hot_shopRecyclerView = customFindViewById(R.id.fragment_rank_hot_shop_recycleView);
        hotwordRecyclerView = customFindViewById(R.id.fragment_rank_hot_word_recycleView);
        mAdapter = new compositeHotAdapter(getActivity(), new ArrayList<compositeHotBean>());
        mBdapter = new compositeShopAdapter(getActivity(), new ArrayList<compositeShopBean>());
        mCdapter = new compositeHotWordAdapter(getActivity(), new ArrayList<compositeHotWordBean>());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        setLayoutManagerView(hot_sellRecyclerView, mAdapter);
        setLayoutManagerView(hot_shopRecyclerView, mBdapter);
        hotwordRecyclerView.setHasFixedSize(true);
        hotwordRecyclerView.setAdapter(mCdapter);
        hotwordRecyclerView.setLayoutManager(gridLayoutManager);

    }


    private void setLayoutManagerView(XRecyclerView mRecyclerView, SolidRVBaseAdapter adapter) {
        LinearLayoutManager LayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(LayoutManager);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                switchAction(ACTION_REFRESH);
            }

            @Override
            public void onLoadMore() {
                switchAction(ACTION_LOAD_MORE);
            }
        });
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotatePulse);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
    }


    private void getData() {
        List<compositeHotBean> list = new ArrayList<compositeHotBean>();
        compositeHotBean c1 = new compositeHotBean();
        compositeHotBean c2 = new compositeHotBean();
        compositeHotBean c3 = new compositeHotBean();
        compositeHotBean c4 = new compositeHotBean();
        compositeHotBean c5 = new compositeHotBean();
        list.add(c1);
        list.add(c2);
        list.add(c3);
        list.add(c4);
        list.add(c5);
        List<compositeShopBean> list_ = new ArrayList<compositeShopBean>();
        compositeShopBean s1 = new compositeShopBean();
        compositeShopBean s2 = new compositeShopBean();
        compositeShopBean s3 = new compositeShopBean();
        list_.add(s1);
        list_.add(s2);
        list_.add(s3);
        List<compositeHotWordBean> list_word = new ArrayList<compositeHotWordBean>();
        compositeHotWordBean h1 = new compositeHotWordBean();
        compositeHotWordBean h2 = new compositeHotWordBean();
        compositeHotWordBean h3 = new compositeHotWordBean();
        compositeHotWordBean h4 = new compositeHotWordBean();
        compositeHotWordBean h5 = new compositeHotWordBean();
        list_word.add(h1);
        list_word.add(h2);
        list_word.add(h3);
        list_word.add(h4);
        list_word.add(h5);
        mAdapter.addAll(list);
        mBdapter.addAll(list_);
        mCdapter.addAll(list_word);
        loadComplete();
        hot_sellRecyclerView.setLoadingMoreEnabled(false);
        hot_shopRecyclerView.setLoadingMoreEnabled(false);
    }

    private void loadComplete() {
        if (mCurrentAction == ACTION_REFRESH)
            hot_sellRecyclerView.refreshComplete();
        hot_shopRecyclerView.refreshComplete();
        if (mCurrentAction == ACTION_LOAD_MORE)
            hot_sellRecyclerView.loadMoreComplete();
        hot_shopRecyclerView.loadMoreComplete();
    }

    private void switchAction(int action) {
        mCurrentAction = action;
        switch (mCurrentAction) {
            case ACTION_REFRESH:
                mAdapter.clear();
                mBdapter.clear();
                break;
            case ACTION_LOAD_MORE:
                break;
        }
        getData();
    }
}
