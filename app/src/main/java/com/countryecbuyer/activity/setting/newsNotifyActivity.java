package com.countryecbuyer.activity.setting;


import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.utils.ToastUtils;
import com.countryecbuyer.view.ToggleButton;

/**
 * 设置 新消息通知
 */
public class newsNotifyActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private ImageView back;
    private TextView title;

    ToggleButton toggleButton1, toggleButton2, toggleButton3;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_news_notify;
    }

    @Override
    protected void initView() {
        back = customFindViewById(R.id.my_top_actionbar_back);
        title = customFindViewById(R.id.my_top_actionbar_title);
        toggleButton1 = customFindViewById(R.id.toggleButton_1);
        toggleButton2 = customFindViewById(R.id.toggleButton_2);
        toggleButton3 = customFindViewById(R.id.toggleButton_3);
        title.setText("新消息通知");
        back.setOnClickListener(this);
        toggleButton1.setOnCheckedChangeListener(this);
        toggleButton2.setOnCheckedChangeListener(this);
        toggleButton3.setOnCheckedChangeListener(this);


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.toggleButton_1:
                toggleButton1.setChecked(isChecked);
                if (isChecked) {
                    ToastUtils.getInstance().showToast("按钮1 打开");
                } else {
                    ToastUtils.getInstance().showToast("按钮1 关闭");
                }
                break;
            case R.id.toggleButton_2:
                toggleButton2.setChecked(isChecked);
                break;
            case R.id.toggleButton_3:
                toggleButton3.setChecked(isChecked);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_top_actionbar_back:
                finish();
                break;

        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtils.getInstance().cancelToast();
    }
}
