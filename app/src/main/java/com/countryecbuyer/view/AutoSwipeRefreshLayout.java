package com.countryecbuyer.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/6/15.支持首次加载自动刷新
 */
public class AutoSwipeRefreshLayout extends SwipeRefreshLayout {

    public AutoSwipeRefreshLayout(Context context) {
        super(context.getApplicationContext());
    }

    public AutoSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context.getApplicationContext(), attrs);
    }

    /**
     * 自动刷新
     */
    public void autoRefresh() {
        try {
            Field mCircleView = SwipeRefreshLayout.class.getDeclaredField("mCircleView");
            mCircleView.setAccessible(true);
            View progress = (View) mCircleView.get(this);
            progress.setVisibility(VISIBLE);
            Method setRefreshing = SwipeRefreshLayout.class.getDeclaredMethod("setRefreshing", boolean.class, boolean.class);
            setRefreshing.setAccessible(true);
            setRefreshing.invoke(this, true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
