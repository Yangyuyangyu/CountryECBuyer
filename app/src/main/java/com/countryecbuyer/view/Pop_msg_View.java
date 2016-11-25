package com.countryecbuyer.view;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.countryecbuyer.R;

/**
 * Created by Administrator on 2016/6/17.消息首页分享
 */
public class Pop_msg_View extends PopupWindow {
    private View mainView;
    private LinearLayout msgLL, mainLL, shareLL;

    public Pop_msg_View(Activity paramActivity, View.OnClickListener paramOnClickListener, int paramInt1, int paramInt2) {
        super(paramActivity);
        //窗口布局
        mainView = LayoutInflater.from(paramActivity).inflate(R.layout.pop_msg_view, null);
        msgLL = (LinearLayout) mainView.findViewById(R.id.pop_msg_view_msgLL);
        mainLL = (LinearLayout) mainView.findViewById(R.id.pop_msg_view_mainLL);
        shareLL = (LinearLayout) mainView.findViewById(R.id.pop_msg_view_shareLL);
        if (paramOnClickListener != null) {
            msgLL.setOnClickListener(paramOnClickListener);
            mainLL.setOnClickListener(paramOnClickListener);
            shareLL.setOnClickListener(paramOnClickListener);
        }
        setContentView(mainView);
        //设置宽度
        setWidth(paramInt1);
        //设置高度
        setHeight(paramInt2);
        //设置显示隐藏动画
        setAnimationStyle(R.style.pop_anim);
        //设置背景透明
        setBackgroundDrawable(new ColorDrawable(0));
    }


}
