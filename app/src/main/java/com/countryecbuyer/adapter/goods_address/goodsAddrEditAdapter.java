package com.countryecbuyer.adapter.goods_address;

import android.content.Context;
import android.view.View;

import com.countryecbuyer.R;
import com.countryecbuyer.adapter.base.SolidRVBaseAdapter;
import com.countryecbuyer.bean.goods_address.goodsAddrBean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/6.bottoSheet收货地址
 */
public class goodsAddrEditAdapter extends SolidRVBaseAdapter<goodsAddrBean> {
    private showEditFragment showeditfragment;

    public goodsAddrEditAdapter(Context context, List<goodsAddrBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_bs_goods_addr;
    }

    @Override
    protected void onItemClick(int position) {


    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final goodsAddrBean bean) {

        holder.setOnItemClickListener(R.id.item_goods_addr_edit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showeditfragment.showEdit();
            }
        });
    }

    public void setshowEditFragment(showEditFragment listener) {
        this.showeditfragment = listener;
    }

    public interface showEditFragment {
        void showEdit();
    }
}
