package com.countryecbuyer.activity.personalInfo;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.activity.receive_address.ReceiveAddrDetailActivity;
import com.countryecbuyer.adapter.goods_address.goodsAddrAdapter;
import com.countryecbuyer.bean.goods_address.goodsAddrBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 收货地址
 */
public class receiverAddressActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView top_title;

    private Button newAdd;
    private RecyclerView recyclerView;
    private goodsAddrAdapter adapter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_receiver_address;
    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    protected void initView() {
        back = customFindViewById(R.id.my_top_actionbar_back);
        top_title = customFindViewById(R.id.my_top_actionbar_title);
        top_title.setText("管理收货地址");

        newAdd = customFindViewById(R.id.help_receiver_address_btn);
        recyclerView = customFindViewById(R.id.help_receiver_address_recyclerview);
        adapter = new goodsAddrAdapter(this, new ArrayList<goodsAddrBean>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        back.setOnClickListener(this);
        newAdd.setOnClickListener(this);
    }

    private void getData() {
        List<goodsAddrBean> list = new ArrayList<goodsAddrBean>();
        goodsAddrBean g1 = new goodsAddrBean();
        goodsAddrBean g2 = new goodsAddrBean();
        goodsAddrBean g3 = new goodsAddrBean();
        list.add(g1);
        list.add(g2);
        list.add(g3);
        adapter.addAll(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_top_actionbar_back:
                finish();
                break;
            case R.id.help_receiver_address_btn:
                Bundle bundle = new Bundle();
                bundle.putInt("the_newAdd_addr", 0);
                startActivityWithExtras(ReceiveAddrDetailActivity.class, bundle);
                break;
        }
    }
}
