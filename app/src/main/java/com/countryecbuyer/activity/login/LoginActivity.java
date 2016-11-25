package com.countryecbuyer.activity.login;


import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.user_info.UserInfoSharePreference;
import com.countryecbuyer.utils.CrazyClickUtils;
import com.countryecbuyer.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity {
    @Bind(R.id.my_top_actionbar_back)
    ImageView back;
    @Bind(R.id.my_top_actionbar_title)
    TextView title;
    @Bind(R.id.login_user_phone)
    EditText user_phone;
    @Bind(R.id.login_user_password)
    EditText user_pwd;
    @Bind(R.id.login_user_ok)
    Button login;
    @Bind(R.id.login_user_forget)
    TextView forget;
    @Bind(R.id.login_user_register)
    Button register;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        title.setText("登录");

    }

    @OnClick({R.id.my_top_actionbar_back, R.id.login_user_ok, R.id.login_user_forget, R.id.login_user_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_top_actionbar_back:
                finish();
                break;
            case R.id.login_user_ok:
                if (CrazyClickUtils.isFastClick()) {
                    return;
                }
                if (TextUtils.isEmpty(user_phone.getText())) {
                    ToastUtils.getInstance().showToastCenter("用户名不能为空");
                    return;
                } else if (TextUtils.isEmpty(user_pwd.getText())) {
                    ToastUtils.getInstance().showToastCenter("用户密码不能为空");
                    return;
                }
                UserInfoSharePreference.getInstance().setTOKEN("这是测试token");
//                Intent intent = getIntent();
//                intent.putExtra("nickname", nickName.getText().toString());
//                LoginActivity.this.setResult(3, intent);
                finish();
                ToastUtils.getInstance().showToastCenter("保存成功");
                break;
            case R.id.login_user_forget:
                if (CrazyClickUtils.isFastClick()) {
                    return;
                }
                startActivityWithoutExtras(ForgetPasswordActivity.class);
                break;
            case R.id.login_user_register:
                if (CrazyClickUtils.isFastClick()) {
                    return;
                }
                startActivityWithoutExtras(RegisterActivity.class);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ToastUtils.getInstance().cancelToast();
    }
}
