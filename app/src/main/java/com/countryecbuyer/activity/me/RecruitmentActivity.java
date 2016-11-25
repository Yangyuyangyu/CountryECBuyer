package com.countryecbuyer.activity.me;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.countryecbuyer.MainActivity;
import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.activity.msg.PushMessageActivity;
import com.countryecbuyer.fragment.bottom_sheet.BS_WeiXinShareFragment;
import com.countryecbuyer.view.Pop_msg_View;

/**
 * 商家入驻
 */
public class RecruitmentActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private ImageView point;
    private TextView top_title;

    private Pop_msg_View popview;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_recruitment;
    }

    @Override
    protected void initView() {
        back = customFindViewById(R.id.my_top_actionbar_back);
        top_title = customFindViewById(R.id.my_top_actionbar_title);
        point = customFindViewById(R.id.my_top_actionbar_point);
        point.setVisibility(View.VISIBLE);
        top_title.setText("商家入驻");
        back.setOnClickListener(this);
        point.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.my_top_actionbar_back:
                finish();
                break;
            case R.id.my_top_actionbar_point:
                showPop();
                break;
        }
    }


    private void showPop() {
        if (popview == null) {
            int ww = ViewGroup.LayoutParams.WRAP_CONTENT;
            int hh = ViewGroup.LayoutParams.WRAP_CONTENT;
            PopLintener paramOnClickListener = new PopLintener();
            popview = new Pop_msg_View(RecruitmentActivity.this, paramOnClickListener, ww, hh);
            popview.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        popview.dismiss();
                    }
                }
            });
        }
        popview.setFocusable(true);
        popview.showAsDropDown(point);
        popview.update();
    }

    /**
     * popWindown监听
     */
    class PopLintener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pop_msg_view_msgLL:
                    startActivityWithoutExtras(PushMessageActivity.class);
                    popview.dismiss();
                    break;
                case R.id.pop_msg_view_mainLL:
                    startActivityWithoutExtras(MainActivity.class);
                    MainActivity.controller.setSelect("yh");
                    popview.dismiss();
                    finish();
                    break;
                case R.id.pop_msg_view_shareLL:
                    popview.dismiss();
                    new BS_WeiXinShareFragment().show(getSupportFragmentManager(), R.id.recruitment_bottomSheetLayout);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        popview=null;
    }
}
