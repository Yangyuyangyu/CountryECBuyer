package com.countryecbuyer;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.user_info.UserInfoSharePreference;
import com.countryecbuyer.utils.HttpUtils;
import com.countryecbuyer.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChatActivity extends BaseActivity {
    @Bind(R.id.main_btn)
    Button mainBtn;
    @Bind(R.id.main_getToken)
    Button mainGetToken;
    @Bind(R.id.main_conversation)
    Button mainConversation;
    @Bind(R.id.main_conversationlist)
    Button mainConversationlist;
    @Bind(R.id.main_refresh)
    Button mainre;

    private Context mContext;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initView() {
        mContext = this;
        ButterKnife.bind(this);

    }

    /**
     * 融云连接服务器方法
     *
     * @param token
     */
    private void connect(String token) {
        if (getApplicationInfo().packageName.equals(BaseApplication.getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {

                    Log.d("LoginActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {
                    ToastUtils.getInstance().showToast("--onSuccess" + userid);
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                    Log.d("LoginActivity", "--onError" + errorCode);
                }
            });
        }
    }


    private void getToken(String timestamp) {
        String reqUrl = "https://api.cn.ronghub.com/user/getToken.json";
        HashMap<Integer, String> mapKey = new HashMap<Integer, String>();
        HashMap<Integer, String> mapValue = new HashMap<Integer, String>();
        mapKey.put(0, "userId");
        mapKey.put(1, "name");
        mapKey.put(2, "portraitUri");
        mapValue.put(0, "010");
        mapValue.put(1, "yangyubo2222");
        mapValue.put(2, "");
        HttpUtils.getInstance().postForm(reqUrl, mapKey, mapValue, timestamp, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getString("code").equals("200")) {
                        Log.e("获取token", "用户id:" + jsonObject.getString("userId") + "用户token:" + jsonObject.getString("token"));
                        UserInfoSharePreference.getInstance().setTOKEN(jsonObject.getString("token"));
                    } else {
                        Log.e("获取token错误code", jsonObject.getString("code"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @OnClick({R.id.main_btn, R.id.main_getToken, R.id.main_conversation, R.id.main_conversationlist, R.id.main_refresh})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_btn:
                connect(UserInfoSharePreference.getInstance().getTOKEN());
                break;
            case R.id.main_getToken:
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                getToken(timestamp.toString());
                break;
            case R.id.main_conversation:
                //启动会话界面
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startPrivateChat(mContext, "009", "测标题22");
                break;
            case R.id.main_conversationlist:
                //启动会话列表界面
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startConversationList(mContext);
                break;
            case R.id.main_refresh:
                refreshHead();
                break;
        }
    }

    private void refreshHead() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String reqUrl = "https://api.cn.ronghub.com/user/refresh.json";
        HashMap<Integer, String> mapKey = new HashMap<Integer, String>();
        HashMap<Integer, String> mapValue = new HashMap<Integer, String>();
        mapKey.put(0, "userId");
        mapKey.put(1, "name");
        mapKey.put(2, "portraitUri");
        mapValue.put(0, "009");
        mapValue.put(1, "yangyubo");
        mapValue.put(2, "");
        HttpUtils.getInstance().postForm(reqUrl, mapKey, mapValue, timestamp.toString(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getString("code").equals("200")) {
                        Log.e("获取token", "刷新成功");

                    } else {
                        Log.e("获取token错误code", jsonObject.getString("code"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtils.getInstance().cancelToast();
    }

}
