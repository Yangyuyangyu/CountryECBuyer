package com.countryecbuyer.view;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.countryecbuyer.R;

/**
 * Created by Administrator on 2016/7/5.搜索选择类型 商品或店铺
 */
public class Pop_Search_View extends PopupWindow {
    private View mainView;
    private LinearLayout goodsLL, shopLL;

    public Pop_Search_View(Activity paramActivity, View.OnClickListener paramOnClickListener, int paramInt1, int paramInt2) {
        super(paramActivity);
        //窗口布局
        mainView = LayoutInflater.from(paramActivity).inflate(R.layout.pop_search_view, null);
        goodsLL = (LinearLayout) mainView.findViewById(R.id.pop_search_view_goodsLL);
        shopLL = (LinearLayout) mainView.findViewById(R.id.pop_search_view_shopLL);
        if (paramOnClickListener != null) {
            goodsLL.setOnClickListener(paramOnClickListener);
            shopLL.setOnClickListener(paramOnClickListener);
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
