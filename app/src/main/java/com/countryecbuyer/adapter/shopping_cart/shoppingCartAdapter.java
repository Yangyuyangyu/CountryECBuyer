package com.countryecbuyer.adapter.shopping_cart;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.adapter.base.SolidRVBaseAdapter;
import com.countryecbuyer.bean.shopping_cart.shoppingCartBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/21.购物车 全选 全取消 编辑  item单选
 */
public class shoppingCartAdapter extends SolidRVBaseAdapter<shoppingCartBean> {
    private int the_index = -1;//当前编辑下标位置
    private boolean ISEDIT = false;

    private OnEditModeState onEditModeState;

    public shoppingCartAdapter(Context context, List<shoppingCartBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_shopping_cart;
    }

    @Override
    protected void onItemClick(int position) {

    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final shoppingCartBean bean) {
        final TextView edit = holder.getView(R.id.item_shop_cart_edit);
        final RecyclerView recyclerView = holder.getView(R.id.item_shop_cart_recyclerview);
        final shoppingCartItemAdapter adapter = new shoppingCartItemAdapter(mContext, new ArrayList<shoppingCartBean>());
        final LinearLayoutManager linearLayout = new LinearLayoutManager(mContext);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayout);

        getData(adapter);

        holder.setOnItemClickListener(R.id.item_shop_cart_edit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                the_index = holder.getAdapterPosition();
                ISEDIT = !ISEDIT;
                if (edit.getText().toString().equals("编辑")) {
                    holder.setText(R.id.item_shop_cart_edit, "完成");
                } else {
                    holder.setText(R.id.item_shop_cart_edit, "编辑");
                }
                notifyDataSetChanged();
                onEditModeState.getEditState(ISEDIT);
            }
        });

        //是否进入编辑模式
        if (ISEDIT) {
            if (holder.getAdapterPosition() == the_index) {
                holder.setVisible(R.id.item_shop_cart_edit, true);
                adapter.changeEditState();
            } else {
                holder.setVisible(R.id.item_shop_cart_edit, false);
            }
        } else {
            holder.setVisible(R.id.item_shop_cart_edit, true);
        }

    }


    /**
     * 添加数据
     */
    private void getData(shoppingCartItemAdapter adapter) {
        List<shoppingCartBean> list = new ArrayList<shoppingCartBean>();
        shoppingCartBean c1 = new shoppingCartBean();
        shoppingCartBean c2 = new shoppingCartBean();
        list.add(c1);
        list.add(c2);
        adapter.addAll(list);
    }


    /**
     * 编辑模式下 结算和删除
     */
    public void setOnEditModeState(OnEditModeState listener) {
        this.onEditModeState = listener;
    }

    public interface OnEditModeState {
        void getEditState(boolean editmode);//是否为编辑模式
    }


}
