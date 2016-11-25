package com.countryecbuyer.activity.me;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;

/**
 * 优惠券
 */
public class meYouHuiTicketActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView top_title;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_me_you_hui_ticket;
    }

    @Override
    protected void initView() {
        back = customFindViewById(R.id.my_top_actionbar_back);
        top_title = customFindViewById(R.id.my_top_actionbar_title);
        top_title.setText("我的优惠券");
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_top_actionbar_back:
                finish();
                break;
        }
    }
}
