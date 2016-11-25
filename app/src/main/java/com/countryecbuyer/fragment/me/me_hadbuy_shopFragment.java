package com.countryecbuyer.fragment.me;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.countryecbuyer.MainActivity;
import com.countryecbuyer.R;
import com.countryecbuyer.fragment.base.BaseFragment;

/**
 * Created by Administrator on 2016/7/13.我买过的 店铺
 */
public class me_hadbuy_shopFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout emptyLL;
    private TextView empty_text;
    private Button empty_btn;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.me_hadbuy_shop;
    }

    @Override
    protected void initView() {
        emptyLL = customFindViewById(R.id.ll_reload_wrap);
        empty_text = customFindViewById(R.id.empty_goods_text);
        empty_btn = customFindViewById(R.id.empty_goods_btn);
        empty_text.setText("额哦,你啥都没买过~" + "\n" + "逛一逛,发现喜欢的商品");
        emptyLL.setVisibility(View.VISIBLE);
        empty_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.empty_goods_btn:
                startActivityWithoutExtras(MainActivity.class);
                MainActivity.controller.setSelect("yh");
                getActivity().finish();
                break;
        }
    }
}
