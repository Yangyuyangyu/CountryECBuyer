package com.countryecbuyer.activity.order;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.fragment.bottom_sheet.BS_ExpressFragment;
import com.countryecbuyer.fragment.bottom_sheet.BS_GoodAddrFragment;
import com.countryecbuyer.fragment.bottom_sheet.BS_YouHuiFragment;
import com.flipboard.bottomsheet.BottomSheetLayout;

/**
 * 立即购买后跳转订单 待支付界面
 */
public class order_PayActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView title;
    private BottomSheetLayout bottomSheetLayout;

    private RelativeLayout receiveAddrRe;//收货地址
    private TextView shopName;//店铺名
    private RelativeLayout contentRe;//商品详情
    private LinearLayout youhuiLL;//优惠
    private RelativeLayout expressRe;//配送方式

    private BS_GoodAddrFragment bs_goodAddrFragment;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_order_pay;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        back = customFindViewById(R.id.my_top_actionbar_back);
        title = customFindViewById(R.id.my_top_actionbar_title);
        title.setText("待付款的订单");
        youhuiLL = customFindViewById(R.id.order_pay_youhuiLL);
        expressRe = customFindViewById(R.id.order_pay_expressRe);
        contentRe = customFindViewById(R.id.order_pay_midRe);
        shopName = customFindViewById(R.id.order_pay_shopName);
        receiveAddrRe = customFindViewById(R.id.order_pay_receive_addressRe);
        bottomSheetLayout = customFindViewById(R.id.order_pay_bottomSheetLayout);
        back.setOnClickListener(this);
        receiveAddrRe.setOnClickListener(this);
        youhuiLL.setOnClickListener(this);
        expressRe.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_top_actionbar_back:
                finish();
                break;
            //配送方式
            case R.id.order_pay_expressRe:
                new BS_ExpressFragment().show(getSupportFragmentManager(), R.id.order_pay_bottomSheetLayout);
                break;
            //优惠
            case R.id.order_pay_youhuiLL:
                new BS_YouHuiFragment().show(getSupportFragmentManager(), R.id.order_pay_bottomSheetLayout);
                break;
            //收货地址
            case R.id.order_pay_receive_addressRe:

                new BS_GoodAddrFragment().show(getSupportFragmentManager(), R.id.order_pay_bottomSheetLayout);
                break;
        }
    }
}
