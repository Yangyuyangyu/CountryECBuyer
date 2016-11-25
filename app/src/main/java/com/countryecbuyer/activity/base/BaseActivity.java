package com.countryecbuyer.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.countryecbuyer.R;


/**
 * Created by _SOLID
 * Date:2016/3/30
 * Time:9:40
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResourceID());
        init();
        initView();
        initData();

    }

    protected void initData() {
    }

    /***
     * 用于在初始化View之前做一些事
     */
    protected void init() {

    }

    protected abstract void initView();

    protected abstract int setLayoutResourceID();

    protected <T extends View> T customFindViewById(int id) {
        return (T) super.findViewById(id);
    }


    protected void startActivityWithoutExtras(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
    }

    protected void startActivityWithExtras(Class<?> clazz, Bundle extras) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(extras);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);

    }

    protected void startActivityForResultWithRequestCode(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
    }

    protected void startActivityForResultWithReqCodeBundle(Class<?> clazz, int requestCode, Bundle extras) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(extras);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
    }


}
