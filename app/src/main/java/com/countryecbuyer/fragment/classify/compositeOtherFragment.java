package com.countryecbuyer.fragment.classify;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.adapter.base.SolidRVBaseAdapter;
import com.countryecbuyer.adapter.classify.compositeOtherAdapter;
import com.countryecbuyer.bean.classify.compositerOtherBean;
import com.countryecbuyer.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/3.其他分类
 */
public class compositeOtherFragment extends BaseFragment {
    private RecyclerView shopRecyclerView, classifyRecyclerView;
    private compositeOtherAdapter shopAdapter, classAdapter;
    private String getBundleStr;

    //测试
    private TextView testStr;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_classify_composite_other;
    }

    @Override
    protected void initData() {
        if (getBundleStr != null) {
            if (!getBundleStr.equals("")) {
                testStr.setText("fragment" + getBundleStr);
            } else {

            }
        }
        getData();
    }

    @Override
    protected void init() {
        getBundleStr = getArguments().getString("text");
    }

    @Override
    protected void initView() {
        testStr = customFindViewById(R.id.test_text);

        shopRecyclerView = customFindViewById(R.id.fragment_rank_other_shop_recycleView);
        classifyRecyclerView = customFindViewById(R.id.fragment_rank_other_classify_recycleView);
        shopAdapter = new compositeOtherAdapter(getActivity(), new ArrayList<compositerOtherBean>());
        classAdapter = new compositeOtherAdapter(getActivity(), new ArrayList<compositerOtherBean>());
        setLayoutManagerView(shopRecyclerView, shopAdapter);
        setLayoutManagerView(classifyRecyclerView, classAdapter);
    }

    private void setLayoutManagerView(RecyclerView mRecyclerView, SolidRVBaseAdapter adapter) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }


    private void getData() {
        List<compositerOtherBean> list = new ArrayList<compositerOtherBean>();
        List<compositerOtherBean> list_ = new ArrayList<compositerOtherBean>();
        compositerOtherBean c1 = new compositerOtherBean();
        compositerOtherBean c2 = new compositerOtherBean();
        compositerOtherBean c3 = new compositerOtherBean();
        compositerOtherBean c4 = new compositerOtherBean();
        compositerOtherBean c5 = new compositerOtherBean();
        compositerOtherBean c6 = new compositerOtherBean();
        compositerOtherBean c7 = new compositerOtherBean();
        list.add(c1);
        list.add(c2);
        list.add(c3);
        list.add(c4);
        list.add(c5);
        list.add(c6);
        list.add(c7);
        list_.add(c1);
        list_.add(c2);
        list_.add(c3);
        list_.add(c4);
        list_.add(c5);
        shopAdapter.addAll(list);
        classAdapter.addAll(list_);
    }
}
