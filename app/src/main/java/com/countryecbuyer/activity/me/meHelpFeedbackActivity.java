package com.countryecbuyer.activity.me;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;

/**
 * 帮助与反馈
 */
public class meHelpFeedbackActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView top_title;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_me_help_feedback;
    }

    @Override
    protected void initView() {
        back = customFindViewById(R.id.my_top_actionbar_back);
        top_title = customFindViewById(R.id.my_top_actionbar_title);
        top_title.setText("客服咨询");
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
