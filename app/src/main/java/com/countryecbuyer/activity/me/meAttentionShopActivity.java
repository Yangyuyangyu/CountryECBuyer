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
 * 我关注的店铺
 */
public class meAttentionShopActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView top_title;


    private LinearLayout empty_ll;
    private TextView empty_txt;
    private Button empty_btn;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_me_attention_shop;
    }

    @Override
    protected void initView() {
        empty_ll = customFindViewById(R.id.ll_reload_wrap);
        empty_txt = customFindViewById(R.id.empty_goods_text);
        empty_btn = customFindViewById(R.id.empty_goods_btn);
        empty_txt.setText("你暂无关注的店铺哦" + "\n" + "逛一逛，发现更多好店吧");
        empty_ll.setVisibility(View.VISIBLE);

        back = customFindViewById(R.id.my_top_actionbar_back);
        top_title = customFindViewById(R.id.my_top_actionbar_title);
        top_title.setText("关注的店铺");
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
