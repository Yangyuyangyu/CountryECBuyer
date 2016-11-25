package com.countryecbuyer.wxapi;

import android.app.Activity;
import android.os.Bundle;


import com.countryecbuyer.apis.Apis;
import com.countryecbuyer.utils.ToastUtils;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 微信客户端回调activity(必需)
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        api = WXAPIFactory.createWXAPI(this, Apis.WX_APPID, false);
        api.handleIntent(getIntent(), this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onReq(BaseReq arg0) {
    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                //分享成功
                ToastUtils.getInstance().showToastCenter("分享成功了");
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                ToastUtils.getInstance().showToastCenter("您取消了分享");
                finish();
                //分享取消
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                ToastUtils.getInstance().showToastCenter("认证失败了");
                finish();
                //分享拒绝
                break;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtils.getInstance().cancelToast();
    }
}
