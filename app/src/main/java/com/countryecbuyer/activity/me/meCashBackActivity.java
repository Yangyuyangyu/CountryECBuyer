package com.countryecbuyer.activity.me;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.countryecbuyer.MainActivity;
import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;

/**
 * 返现
 */
public class meCashBackActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView top_title;

    private LinearLayout empty_ll;
    private TextView empty_txt;
    private Button empty_btn;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_me_cash_back;
    }

    @Override
    protected void initView() {
        empty_ll = customFindViewById(R.id.ll_reload_wrap);
        empty_txt = customFindViewById(R.id.empty_goods_text);
        empty_btn = customFindViewById(R.id.empty_goods_btn);
        empty_txt.setText("哎呀,还没返现?" + "\n" + "不落单一起团");
        empty_ll.setVisibility(View.VISIBLE);

        back = customFindViewById(R.id.my_top_actionbar_back);
        top_title = customFindViewById(R.id.my_top_actionbar_title);
        top_title.setText("返现记录");
        empty_btn.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.empty_goods_btn:
                startActivityWithoutExtras(MainActivity.class);
                MainActivity.controller.setSelect("yh");
                break;
            case R.id.my_top_actionbar_back:
                finish();
                break;
        }
    }
}
