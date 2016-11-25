package com.countryecbuyer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.search.SearchActivity;
import com.countryecbuyer.adapter.classifyNameAdapter;
import com.countryecbuyer.bean.classifyNameBean;
import com.countryecbuyer.fragment.base.BaseFragment;
import com.countryecbuyer.fragment.classify.compositeOtherFragment;
import com.countryecbuyer.fragment.classify.compositeRankFragment;
import com.countryecbuyer.utils.ViewUtils;
import com.countryecbuyer.view.OverScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waycube-yyb on 2016/6/1.分类
 */
public class ClassifyFragment extends BaseFragment implements View.OnClickListener {
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    private RecyclerView recyclerView;
    private classifyNameAdapter mAdapter;
    private LinearLayout searchLL;
    private OverScrollView scrollView1;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_classify;
    }

    @Override
    protected void init() {
        mFragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    protected void initView() {
        searchLL = customFindViewById(R.id.include_search_unonclickLL);
        recyclerView = customFindViewById(R.id.fragment_classify_recyclerView);
        scrollView1 = customFindViewById(R.id.fragment_classify_scrollview1);
        mAdapter = new classifyNameAdapter(getActivity(), new ArrayList<classifyNameBean>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        initDefaultFragment();
        mAdapter.setOnIteNameClick(new classifyNameAdapter.onItemNameClick() {
            @Override
            public void onclickListener(String name) {
                switch (name) {
                    case "综合排行":
                        switchFragment(null);
                        break;
                    case "食品酒水":
                        switchFragment("食品酒水");
                        break;
                    case "女士穿着":
                        switchFragment("女士穿着");
                        break;
                    case "男士穿着":
                        switchFragment("男士穿着");
                        break;
                    case "箱包配饰":
                        switchFragment("箱包配饰");
                        break;
                    case "数码电器":
                        switchFragment("数码电器");
                        break;
                    case "护肤彩妆":
                        switchFragment("护肤彩妆");
                        break;
                    case "母婴用品":
                        switchFragment("母婴用品");
                        break;
                    case "运动户外":
                        switchFragment("运动户外");
                        break;
                    case "居家百货":
                        switchFragment("居家百货");
                        break;
                    case "玩家必备":
                        switchFragment("玩家必备");
                        break;
                    case "电影购票":
                        switchFragment("电影购票");
                        break;
                    case "精选小吃":
                        switchFragment("精选小吃");
                        break;


                }
            }
        });
        searchLL.setOnClickListener(this);
    }

    private void initDefaultFragment() {
        mCurrentFragment = ViewUtils.createFragment(compositeRankFragment.class, true);
        mFragmentManager.beginTransaction().add(R.id.fragment_classify_frameLayout, mCurrentFragment).commit();
    }

    /**
     * fragment切换
     */
    private void switchFragment(String content) {
        Fragment to;
        if (content == null) {
            to = ViewUtils.createFragment(compositeRankFragment.class, true);
            if (to.isAdded()) {
                mFragmentManager.beginTransaction().hide(mCurrentFragment).show(to).commitAllowingStateLoss();
            } else {
                mFragmentManager.beginTransaction().hide(mCurrentFragment).setCustomAnimations(R.anim.push_up_in, R.anim.push_up_out).add(R.id.fragment_classify_frameLayout, to).commitAllowingStateLoss();
            }
        } else {
            to = ViewUtils.createFragment(compositeOtherFragment.class, false);
            Bundle bundle = new Bundle();
            bundle.putString("text", "测试" + content);
            to.setArguments(bundle);
            if (to.isAdded()) {
                mFragmentManager.beginTransaction().hide(mCurrentFragment).show(to).commitAllowingStateLoss();
            } else {
                mFragmentManager.beginTransaction().hide(mCurrentFragment).setCustomAnimations(R.anim.push_up_in, R.anim.push_up_out).add(R.id.fragment_classify_frameLayout, to).commitAllowingStateLoss();
            }
        }
        mCurrentFragment = to;
    }

    private void getData() {
        List<classifyNameBean> list = new ArrayList<classifyNameBean>();
        classifyNameBean c1 = new classifyNameBean();
        classifyNameBean c2 = new classifyNameBean();
        classifyNameBean c3 = new classifyNameBean();
        classifyNameBean c4 = new classifyNameBean();
        classifyNameBean c5 = new classifyNameBean();
        classifyNameBean c6 = new classifyNameBean();
        classifyNameBean c7 = new classifyNameBean();
        classifyNameBean c8 = new classifyNameBean();
        classifyNameBean c9 = new classifyNameBean();
        classifyNameBean c10 = new classifyNameBean();
        classifyNameBean c11 = new classifyNameBean();
        classifyNameBean c12 = new classifyNameBean();
        classifyNameBean c13 = new classifyNameBean();
        c1.setName("综合排行");
        c2.setName("食品酒水");
        c3.setName("女士穿着");
        c4.setName("男士穿着");
        c5.setName("箱包配饰");
        c6.setName("数码电器");
        c7.setName("护肤彩妆");
        c8.setName("母婴用品");
        c9.setName("运动户外");
        c10.setName("居家百货");
        c11.setName("玩家必备");
        c12.setName("电影购票");
        c13.setName("精选小吃");
        list.add(c1);
        list.add(c2);
        list.add(c3);
        list.add(c4);
        list.add(c5);
        list.add(c6);
        list.add(c7);
        list.add(c8);
        list.add(c9);
        list.add(c10);
        list.add(c11);
        list.add(c12);
        list.add(c13);
        mAdapter.addAll(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.include_search_unonclickLL:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
}
