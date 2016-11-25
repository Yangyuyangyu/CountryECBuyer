package com.countryecbuyer.activity.personalInfo;


import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.utils.CrazyClickUtils;
import com.countryecbuyer.utils.ToastUtils;

/**
 * 昵称修改
 */
public class nickNameActivity extends BaseActivity {
    private Toolbar toolbar;
    private EditText nickName;
    private Button save;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_nick_name;
    }

    @Override
    protected void initView() {
        toolbar = customFindViewById(R.id.toolbar);
        toolbar.setTitle("昵称");
        setSupportActionBar(toolbar);//调用返回获取焦点
        toolbar.setNavigationIcon(R.mipmap.icon_back_gray);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        nickName = customFindViewById(R.id.nick_name_edit);
        save = customFindViewById(R.id.nick_name_save);
        if (!TextUtils.isEmpty(nickName.getText().toString())) {
            nickName.setSelection(nickName.getText().length());
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CrazyClickUtils.isFastClick()) {
                    return;
                }
                if (TextUtils.isEmpty(nickName.getText().toString())) {
                    ToastUtils.getInstance().showToast("昵称不能为空");
                    return;
                }
                Intent intent = getIntent();
                intent.putExtra("nickname", nickName.getText().toString());
                nickNameActivity.this.setResult(3, intent);
                finish();
                ToastUtils.getInstance().showToastCenter("保存成功");
            }
        });

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtils.getInstance().cancelToast();
    }
}
