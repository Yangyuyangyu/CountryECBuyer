package com.countryecbuyer.fragment.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.countryecbuyer.R;


/**
 * Created by _SOLID
 * Date:2016/3/30
 * Time:11:30
 */
public abstract class BaseFragment extends Fragment {

    private View mContentView;
    private Context mContext;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(setLayoutResourceID(), container, false);//setContentView(inflater, container);
        mContext = getContext();
        mProgressDialog = new ProgressDialog(getMContext());
        mProgressDialog.setCanceledOnTouchOutside(false);
        init();
        initView();
        initData();
        return mContentView;
    }

    protected abstract int setLayoutResourceID();

    protected void initData() {

    }

    protected void init() {

    }

    protected void initView() {
    }

    protected <T extends View> T customFindViewById(int id) {
        return (T) mContentView.findViewById(id);
    }

    protected void startActivityWithoutExtras(Class<?> clazz) {
        Intent intent = new Intent(mContext, clazz);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
    }

    protected void startActivityWithExtras(Class<?> clazz, Bundle extras) {
        Intent intent = new Intent(mContext, clazz);
        intent.putExtras(extras);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);

    }

    protected void startActivityForResultWithRequestCode(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
    }


    // protected abstract View setContentView(LayoutInflater inflater, ViewGroup container);

    protected View getContentView() {
        return mContentView;
    }

    public Context getMContext() {
        return mContext;
    }

    protected ProgressDialog getProgressDialog() {
        return mProgressDialog;
    }
}
