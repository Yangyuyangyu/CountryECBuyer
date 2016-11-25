package com.countryecbuyer.activity.search;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.adapter.search.SearchDataAdapter;
import com.countryecbuyer.bean.search.SearchDataBean;
import com.countryecbuyer.utils.ToastUtils;
import com.countryecbuyer.view.Pop_Search_View;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索
 */
public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener, View.OnClickListener {
    private SearchView sv;
    private TextView back, type;
    private RecyclerView recyclerView;
    private SearchDataAdapter adapter;
    private Pop_Search_View popview;

    private String getStr;//获取热搜词


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_search;
    }

    @Override
    protected void initData() {
        getData();
        sv.setQuery(getStr, false);
    }

    @Override
    protected void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            getStr = bundle.getString("the_hot_word");
        }
    }

    @Override
    protected void initView() {
        sv = customFindViewById(R.id.search_sv);
        back = customFindViewById(R.id.search_back);
        type = customFindViewById(R.id.search_type);
        recyclerView = customFindViewById(R.id.search_recyclerView);
        sv.setIconifiedByDefault(true); // 搜索图标是否在输入框内
        sv.setIconified(false);
        sv.setOnQueryTextListener(this); // 设置事件监听器
        sv.setSubmitButtonEnabled(false); // 显示搜索按钮
        sv.setQueryHint("搜索商品或店铺");// 提示文本
        adapter = new SearchDataAdapter(this, new ArrayList<SearchDataBean>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.setOnItemlistener(new SearchDataAdapter.OnItemlistener() {
            @Override
            public void onItemClick(String name) {
                ToastUtils.getInstance().showToast(name);
            }
        });
        ImageView searchImg = (ImageView) sv.findViewById(R.id.search_button);
        searchImg.setVisibility(View.GONE);
        back.setOnClickListener(this);
        type.setOnClickListener(this);

    }

    private void getData() {
        List<SearchDataBean> list = new ArrayList<SearchDataBean>();
        SearchDataBean s1 = new SearchDataBean();
        SearchDataBean s2 = new SearchDataBean();
        SearchDataBean s3 = new SearchDataBean();
        SearchDataBean s4 = new SearchDataBean();
        SearchDataBean s5 = new SearchDataBean();
        s1.setName("搜索词语1");
        s2.setName("搜索词语2");
        s3.setName("搜索词语3");
        s4.setName("搜索词语4");
        s5.setName("搜索词语5");
        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);
        adapter.addAll(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_back:
                finish();
                break;
            case R.id.search_type:
                showPop();
                break;
        }
    }

    /**
     * 输入字符
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            if (recyclerView.getVisibility() == View.VISIBLE)
                recyclerView.setVisibility(View.INVISIBLE);
        } else {
            if (recyclerView.getVisibility() == View.INVISIBLE)
                recyclerView.setVisibility(View.VISIBLE);
        }
        return true;
    }

    /**
     * 点击搜索
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private void showPop() {
        if (popview == null) {
            int ww = ViewGroup.LayoutParams.WRAP_CONTENT;
            int hh = ViewGroup.LayoutParams.WRAP_CONTENT;
            PopLintener paramOnClickListener = new PopLintener();
            popview = new Pop_Search_View(SearchActivity.this, paramOnClickListener, ww, hh);
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
        popview.showAsDropDown(type);
        popview.update();
    }

    /**
     * popWindown监听
     */
    class PopLintener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pop_search_view_goodsLL:
                    type.setText("商品");
                    popview.dismiss();
                    break;
                case R.id.pop_search_view_shopLL:
                    type.setText("店铺");
                    popview.dismiss();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        popview=null;
        ToastUtils.getInstance().cancelToast();
    }
}
