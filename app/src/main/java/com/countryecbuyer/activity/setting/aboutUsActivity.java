package com.countryecbuyer.activity.setting;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;

public class aboutUsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView title;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView() {
        back = customFindViewById(R.id.my_top_actionbar_back);
        title = customFindViewById(R.id.my_top_actionbar_title);
        title.setText("关于我们");
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
