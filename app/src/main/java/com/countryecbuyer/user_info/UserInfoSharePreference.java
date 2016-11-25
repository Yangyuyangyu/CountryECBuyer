package com.countryecbuyer.user_info;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import com.countryecbuyer.BaseApplication;

/**
 * Created by Administrator on 2016/5/30.用户信息SharePreference持久化保存，避免静态变量回收风险
 */
public class UserInfoSharePreference {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private static final UserInfoSharePreference userInforSp = new UserInfoSharePreference();

    public static final String CUSTOMERID = "CustomerID";
    public static final String TOKEN = "token";

    public static UserInfoSharePreference getInstance() {
        return userInforSp;
    }

    @SuppressLint("CommitPrefEdits")
    private UserInfoSharePreference() {
        sp = BaseApplication.preferences;
        editor = sp.edit();
    }

    public String getCUSTOMERID() {
        return sp.getString(CUSTOMERID, "");
    }

    public void setCUSTOMERID(String customerid) {
        editor.putString(CUSTOMERID, customerid);
        editor.commit();
    }

    public String getTOKEN() {
        return sp.getString(TOKEN, "");
    }

    public void setTOKEN(String token) {
        editor.putString(TOKEN, token);
        editor.commit();
    }
}
