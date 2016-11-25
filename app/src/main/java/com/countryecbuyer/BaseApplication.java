package com.countryecbuyer;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.countryecbuyer.user_info.CustomerInfomaition;
import com.countryecbuyer.utils.MySnackbar;
import com.countryecbuyer.utils.ToastUtils;

import io.rong.imkit.RongIM;

/**
 * Created by waycube-yyb on 2016/5/30.基本Application
 */
public class BaseApplication extends Application {
    private static BaseApplication mInstance;
    public static SharedPreferences preferences;
    public static CustomerInfomaition user_info;

    public static BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        ToastUtils.init(this);
        MySnackbar.init(this);
        preferences = getSharedPreferences("cecb_userinfo", Context.MODE_PRIVATE);
        /**
         * 初始化融云
         */
        RongIM.init(this);
    }


    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context.getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }
}
