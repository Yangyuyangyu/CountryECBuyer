package com.countryecbuyer.fragment.bottom_sheet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.apis.Apis;
import com.countryecbuyer.utils.ClipboardUtils;
import com.countryecbuyer.utils.ToastUtils;
import com.flipboard.bottomsheet.commons.BottomSheetFragment;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2016/7/5.分享界面
 */
public class BS_WeiXinShareFragment extends BottomSheetFragment implements View.OnClickListener {
    private TextView cancel;
    private ImageView weixin, friends, message, copy;
    /**
     * 微信
     */
    private IWXAPI api;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_share, container, false);
        init(view);
        regToWx();
        return view;
    }

    private void init(View v) {
        cancel = (TextView) v.findViewById(R.id.bs_share_cancel);
        weixin = (ImageView) v.findViewById(R.id.bs_share_weixinImg);
        friends = (ImageView) v.findViewById(R.id.bs_share_friendImg);
        message = (ImageView) v.findViewById(R.id.bs_share_messageImg);
        copy = (ImageView) v.findViewById(R.id.bs_share_copyImg);
        copy.setOnClickListener(this);
        cancel.setOnClickListener(this);
        message.setOnClickListener(this);
        friends.setOnClickListener(this);
        weixin.setOnClickListener(this);

    }

    /**
     * 注册
     */
    private void regToWx() {
        api = WXAPIFactory.createWXAPI(getActivity(), Apis.WX_APPID);
        api.registerApp(Apis.WX_APPID);
    }

    /**
     * 分享给朋友或朋友圈
     */
    private void shareText(String content, boolean isSession) {
        WXTextObject textObject = new WXTextObject();
        textObject.text = content;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.description = content;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = ((isSession == Boolean.TRUE) ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline);//发送到聊天 或 朋友圈
        api.sendReq(req);
    }

    /**
     * 发短信
     */
    private void sendSMS(String title, String webUrl) {
        String smsBody = title + webUrl;
        Uri smsToUri = Uri.parse("smsto:");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
        sendIntent.putExtra("sms_body", smsBody);
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivityForResult(sendIntent, 1002);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //取消
            case R.id.bs_share_cancel:
                dismiss();
                break;
            //微信分享
            case R.id.bs_share_weixinImg:
                if (!api.isWXAppInstalled()) {
                    ToastUtils.getInstance().showToastCenter("您还未安装微信客户端");
                    dismiss();
                    return;
                }
                shareText("这只是一只白鼠", true);
                break;
            //朋友圈
            case R.id.bs_share_friendImg:
                if (!api.isWXAppInstalled()) {
                    ToastUtils.getInstance().showToastCenter("您还未安装微信客户端");
                    dismiss();
                    return;
                }
                shareText("这只是一只灰鼠", false);
                break;
            //短信
            case R.id.bs_share_messageImg:
                dismiss();
                sendSMS("测试标题", "测试网址www.baidu.com");
                break;
            //复制链接
            case R.id.bs_share_copyImg:
                dismiss();
                ClipboardUtils.setText(getActivity(), "复制链接Url");
                ToastUtils.getInstance().showToastCenter("已复制到剪切板");
                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtils.getInstance().cancelToast();
    }
}
