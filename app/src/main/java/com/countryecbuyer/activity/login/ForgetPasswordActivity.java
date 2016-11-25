package com.countryecbuyer.activity.login;


import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.utils.CrazyClickUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 忘记密码
 */
public class ForgetPasswordActivity extends BaseActivity {
    @Bind(R.id.my_top_actionbar_back)
    ImageView back;
    @Bind(R.id.my_top_actionbar_title)
    TextView title;
    @Bind(R.id.forget_user_phone)
    LinearLayout phone;
    @Bind(R.id.forget_code)
    EditText code;
    @Bind(R.id.forget_getCode)
    Button getCode;
    @Bind(R.id.forget_user_pwd)
    EditText password;
    @Bind(R.id.forget_user_ok)
    Button ok;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        title.setText("忘记密码");
    }

    @OnClick({R.id.my_top_actionbar_back, R.id.forget_getCode, R.id.forget_user_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_top_actionbar_back:
                finish();
                break;
            //获取验证码
            case R.id.forget_getCode:
                if (CrazyClickUtils.isFastClick()) {
                    return;
                }
                getCode.setEnabled(false);
                getCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_gray_solid));
                getCode.setTextColor(Color.WHITE);
                timer.start();
                break;
            //提交
            case R.id.forget_user_ok:

                break;
        }
    }

    /**
     * 验证码发送倒计时
     */
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            getCode.setText(" " + (millisUntilFinished / 1000) + "秒后重新获取 ");
        }

        @Override
        public void onFinish() {
            getCode.setEnabled(true);
            getCode.setText(" 重新获取验证码 ");
            getCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_green_frame));
            getCode.setTextColor(getResources().getColor(android.R.color.holo_green_light));
        }
    };

    @Override
    protected void onDestroy() {
        timer.cancel();
        timer.onFinish();
        super.onDestroy();
    }
}
