package com.countryecbuyer.fragment.bottom_sheet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.order.order_PayActivity;
import com.countryecbuyer.utils.CrazyClickUtils;
import com.countryecbuyer.utils.ToastUtils;
import com.flipboard.bottomsheet.commons.BottomSheetFragment;

/**
 * Created by Administrator on 2016/6/30.购物车底部弹窗 加入、立即购买
 */
public class BS_JoinCartFragment extends BottomSheetFragment implements View.OnClickListener {
    private int getBundleType;//判断是 0加入购物车或 1立即购买

    private LinearLayout plus_minusLL;//加减
    private TextView reduce, add, num;//减少、增加
    private int intNum = 1;

    private ImageView cancel;
    private Button bottom_btn;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_cart, container, false);
        init(view);
        return view;
    }

    private void init(View v) {
        getBundleType = getArguments().getInt("cart_type");


        cancel = (ImageView) v.findViewById(R.id.bs_cart_cancel);
        plus_minusLL = (LinearLayout) v.findViewById(R.id.plus_minus_viewLL);
        plus_minusLL.setVisibility(View.VISIBLE);
        reduce = (TextView) v.findViewById(R.id.tv_reduce);
        add = (TextView) v.findViewById(R.id.tv_num);
        num = (TextView) v.findViewById(R.id.tv_add);
        bottom_btn = (Button) v.findViewById(R.id.bs_cart_bottom_btn);
        add.setOnClickListener(this);
        cancel.setOnClickListener(this);
        reduce.setOnClickListener(this);
        bottom_btn.setOnClickListener(this);
        if (getBundleType == 0) {  //加入购物车
            bottom_btn.setText("加入购物车");
        } else if (getBundleType == 1) {  //立即购买
            bottom_btn.setText("下一步");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bs_cart_cancel:
                dismiss();
                break;
            case R.id.bs_cart_bottom_btn:
//                if (CrazyClickUtils.isFastClick()) {
//                    return;
//                }
                if (getBundleType == 0) {  //加入购物车
                    ToastUtils.getInstance().showToastCenter("加入购物车成功");
                } else if (getBundleType == 1) {  //立即购买
                    bottom_btn.setText("正在跳转...");
                    //请求接口,显示正在下单
                    Intent intent = new Intent(getActivity(), order_PayActivity.class);
                    startActivity(intent);
                }
                dismiss();
                break;
            case R.id.tv_reduce:
                if (intNum <= 1) {
                    return;
                }
                intNum--;
                num.setText(String.valueOf(intNum));
                break;
            case R.id.tv_add:
                intNum++;
                num.setText(String.valueOf(intNum));
                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtils.getInstance().cancelToast();
    }
}
