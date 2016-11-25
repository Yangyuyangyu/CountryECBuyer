package com.countryecbuyer.activity.login;


import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.utils.ToastUtils;
import com.countryecbuyer.view.sweet_alert_dialog.SweetAlertDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户注册
 */
public class RegisterActivity extends BaseActivity {
    private SweetAlertDialog dialog;

    @Bind(R.id.my_top_actionbar_back)
    ImageView back;
    @Bind(R.id.my_top_actionbar_title)
    TextView title;
    @Bind(R.id.regiseter_next)
    Button next;
    @Bind(R.id.regiseter_phone)
    EditText phone;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        title.setText("注册");
        dialog = new SweetAlertDialog(this);


    }


    @OnClick({R.id.my_top_actionbar_back, R.id.regiseter_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_top_actionbar_back:
                finish();
                break;
            case R.id.regiseter_next:
                if (TextUtils.isEmpty(phone.getText())) {
                    new SweetAlertDialog(this)
                            .setTitleText("提示")
                            .setContentText("请输入您的手机号码")
                            .setConfirmText("好的")
                            .show();
                    return;
                }
                dialog.setTitleText("提示")
                        .setContentText("该手机号码已注册,请直接登录")
                        .setConfirmText("立即登录")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                dialog.dismiss();
                                ToastUtils.getInstance().showToastCenter("登录跳转");
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtils.getInstance().cancelToast();
    }
}
