package com.countryecbuyer.adapter.order;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.order.order_PayActivity;
import com.countryecbuyer.adapter.base.SolidRVBaseAdapter;
import com.countryecbuyer.bean.order.orderBean;
import com.countryecbuyer.view.sweet_alert_dialog.SweetAlertDialog;

import java.util.List;

/**
 * Created by Administrator on 2016/6/30.全部订单
 */
public class orderAllAdapter extends SolidRVBaseAdapter<orderBean> {

    public orderAllAdapter(Context context, List<orderBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_order_cardview;
    }

    @Override
    protected void onItemClick(int position) {
    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final orderBean bean) {

        holder.setOnItemClickListener(R.id.item_order_card_midRe, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, order_PayActivity.class);
                mContext.startActivity(intent);
            }
        });

        holder.setOnItemClickListener(R.id.item_order_card_cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(mContext)
                        .setTitleText("提示")
                        .setContentText("订单还未付款，确定取消?")
                        .setConfirmText("确定")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }).show();
            }
        });
        holder.setOnItemClickListener(R.id.item_order_card_comfirm, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
