package com.countryecbuyer.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.countryecbuyer.R;
import com.countryecbuyer.utils.ToastUtils;

/**
 * 聚合会话列表
 */
public class SubConversationListActivtiy extends FragmentActivity {
    /**
     * 聚合类型
     */
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_sub_conversation_list_activtiy);
        getActionBarTitle();
    }

    /**
     * 通过 intent 中的数据，得到当前的 targetId 和 type
     */
    private void getActionBarTitle() {

        Intent intent = getIntent();

        type = intent.getData().getQueryParameter("type");

        if (type.equals("group")) {
            ToastUtils.getInstance().showToast("聚合群组");
        } else if (type.equals("private")) {
            ToastUtils.getInstance().showToast("聚合单聊");
        } else if (type.equals("discussion")) {
            ToastUtils.getInstance().showToast("聚合讨论组");
        } else if (type.equals("system")) {
            ToastUtils.getInstance().showToast("聚合系统会话");
        } else {
            ToastUtils.getInstance().showToast("聚合");
        }

    }
}
