package com.countryecbuyer;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.fragment.ClassifyFragment;
import com.countryecbuyer.fragment.MainFragment;
import com.countryecbuyer.fragment.MeFragment;
import com.countryecbuyer.fragment.ShoppingCartFragment;
import com.countryecbuyer.utils.ViewUtils;

import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.TabItemBuilder;
import me.majiajie.pagerbottomtabstrip.TabLayoutMode;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

public class MainActivity extends BaseActivity {
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    public static Controller controller;
    private PagerBottomTabLayout pagerBottomTabLayout;
    private TabItemBuilder builder1, builder2, builder3, builder4;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void initView() {
        initDefaultFragment();
        BottomTab();
    }

    /**
     * 默认
     */
    private void initDefaultFragment() {
        mCurrentFragment = ViewUtils.createFragment(MainFragment.class);
        mFragmentManager.beginTransaction().add(R.id.main_frame_content, mCurrentFragment).commit();

    }

    /**
     * fragment切换
     */
    private void switchFragment(Class<?> clazz) {
        Fragment to = ViewUtils.createFragment(clazz);
        if (to.isAdded()) {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(to).commitAllowingStateLoss();
        } else {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).setCustomAnimations(R.anim.push_up_in, R.anim.push_up_out).add(R.id.main_frame_content, to).commitAllowingStateLoss();
        }
        mCurrentFragment = to;
    }

    private void BottomTab() {
        pagerBottomTabLayout = customFindViewById(R.id.main_tab);
        builder1 = new TabItemBuilder(this);
        builder2 = new TabItemBuilder(this);
        builder3 = new TabItemBuilder(this);
        builder4 = new TabItemBuilder(this);
        tabItemBuilder(builder1, getResources().getDrawable(R.drawable.icon_test_m1_n), getResources().getDrawable(R.drawable.icon_test_m1_s), "有货", "yh");
        tabItemBuilder(builder2, getResources().getDrawable(R.drawable.icon_test_m2_n), getResources().getDrawable(R.drawable.icon_test_m2_s), "分类", "fl");
        tabItemBuilder(builder3, getResources().getDrawable(R.drawable.icon_test_m3_n), getResources().getDrawable(R.drawable.icon_test_m3_s), "购物车", "gwc");
        tabItemBuilder(builder4, getResources().getDrawable(R.drawable.icon_test_m4_n), getResources().getDrawable(R.drawable.icon_test_m4_s), "我", "me");
        controller = pagerBottomTabLayout.builder()
                .addTabItem(builder1)
                .addTabItem(builder2)
                .addTabItem(builder3)
                .addTabItem(builder4)
//                .setMode(TabLayoutMode.HIDE_TEXT)
                .build();
        controller.setMessageNumber("gwc", 10);
        controller.addTabItemClickListener(listener);
    }

    private void tabItemBuilder(TabItemBuilder builder, Drawable default_drawable, Drawable select_drawable, String text, String tag) {
        builder.create()
                .setDefaultIcon(default_drawable)
                .setSelectedIcon(select_drawable)
                .setText(text)
                .setSelectedColor(getResources().getColor(R.color.red_color))
                .setTag(tag)
                .build();
    }

    private OnTabItemSelectListener listener = new OnTabItemSelectListener() {
        @Override
        public void onSelected(int index, Object tag) {
            switch (tag.toString()) {
                case "yh":
                    switchFragment(MainFragment.class);
                    break;
                case "fl":
                    switchFragment(ClassifyFragment.class);
                    break;
                case "gwc":
                    switchFragment(ShoppingCartFragment.class);
                    break;
                case "me":
                    switchFragment(MeFragment.class);
                    break;
            }
        }

        @Override
        public void onRepeatClick(int index, Object tag) {
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        controller = null;
        super.onDestroy();
    }
}
