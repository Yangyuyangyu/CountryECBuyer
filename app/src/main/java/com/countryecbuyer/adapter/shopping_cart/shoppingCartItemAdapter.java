package com.countryecbuyer.adapter.shopping_cart;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.countryecbuyer.R;
import com.countryecbuyer.adapter.base.SolidRVBaseAdapter;
import com.countryecbuyer.bean.shopping_cart.shoppingCartBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.购物车商品列表
 */
public class shoppingCartItemAdapter extends SolidRVBaseAdapter<shoppingCartBean> {
    private boolean EDIT_SHOW = false;//是否编辑模式
    private int num = 1;

    public shoppingCartItemAdapter(Context context, List<shoppingCartBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_shopping_cart_item;
    }

    @Override
    protected void onItemClick(int position) {

    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final shoppingCartBean bean) {
        final CheckBox cb = holder.getView(R.id.item_shopcart_item_checkbox);
        if (cb.isChecked()) {
        } else {
        }
        if (EDIT_SHOW) {
            holder.setVisible(R.id.plus_minus_viewLL, true);
            holder.setVisible(R.id.item_shopcart_item_count, false);
        } else {
            holder.setVisible(R.id.plus_minus_viewLL, false);
            holder.setVisible(R.id.item_shopcart_item_count, true);
        }

        //加减操作
        holder.setOnItemClickListener(R.id.tv_reduce, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num <= 1) {
                    return;
                } else {
                    num--;
                }
                holder.setText(R.id.tv_num, num + "");
            }
        });
        holder.setOnItemClickListener(R.id.tv_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                holder.setText(R.id.tv_num, num + "");
            }
        });

    }

    /**
     * 编辑状态刷新
     */
    public void changeEditState() {
        EDIT_SHOW = !EDIT_SHOW;
        notifyDataSetChanged();
    }

}
