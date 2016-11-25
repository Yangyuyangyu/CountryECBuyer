package com.countryecbuyer.fragment.bottom_sheet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.countryecbuyer.R;
import com.countryecbuyer.adapter.goods_address.goodsAddrEditAdapter;
import com.countryecbuyer.bean.goods_address.goodsAddrBean;
import com.countryecbuyer.fragment.bottom_sheet.goods_addr.BS_GoodsAddr_EditFragment;
import com.countryecbuyer.fragment.bottom_sheet.goods_addr.BS_GoodsAddr_NewFragment;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.BottomSheetFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/1.收货地址
 */
public class BS_GoodAddrFragment extends BottomSheetFragment implements View.OnClickListener {
    private ImageView cancel;
    private LinearLayout newLL;
    private BottomSheetLayout bottomSheetLayout;
    private BS_GoodsAddr_EditFragment bs_editFragment;
    private BS_GoodsAddr_NewFragment bs_newFragment;

    private RecyclerView recyclerView;
    private goodsAddrEditAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_good_addr, container, false);
        init(view);
        initData();
        return view;
    }

    private void init(View v) {
        cancel = (ImageView) v.findViewById(R.id.bs_goods_addr_cancel);
        newLL = (LinearLayout) v.findViewById(R.id.item_goods_addr_newLL);
        recyclerView = (RecyclerView) v.findViewById(R.id.bs_goods_addr_recyclerView);
        adapter = new goodsAddrEditAdapter(getActivity(), new ArrayList<goodsAddrBean>());
        bottomSheetLayout = (BottomSheetLayout) v.findViewById(R.id.bs_test_goods_layout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        cancel.setOnClickListener(this);
        newLL.setOnClickListener(this);

        bs_editFragment = new BS_GoodsAddr_EditFragment();
        bs_newFragment = new BS_GoodsAddr_NewFragment();
        adapter.setshowEditFragment(new goodsAddrEditAdapter.showEditFragment() {
            @Override
            public void showEdit() {
                Bundle bundle = new Bundle();
                //传递参数
                bs_editFragment.setArguments(bundle);
                bs_editFragment.show(getActivity().getSupportFragmentManager(), R.id.bs_test_goods_layout);
            }
        });
        bs_newFragment.setOnNewAddr(new BS_GoodsAddr_NewFragment.onNewAddr() {
            @Override
            public void setOnNewAddress() {
                goodsAddrBean gg = new goodsAddrBean();
                adapter.add(gg);
            }
        });
    }

    private void initData() {
        List<goodsAddrBean> list = new ArrayList<goodsAddrBean>();
        goodsAddrBean g1 = new goodsAddrBean();
        goodsAddrBean g2 = new goodsAddrBean();
        list.add(g1);
        list.add(g2);
        adapter.addAll(list);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bs_goods_addr_cancel:
                dismiss();
                break;
            //新增
            case R.id.item_goods_addr_newLL:
                bs_newFragment.show(getActivity().getSupportFragmentManager(), R.id.bs_test_goods_layout);
                break;


        }
    }

}
