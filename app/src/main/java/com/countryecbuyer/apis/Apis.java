package com.countryecbuyer.apis;

import com.countryecbuyer.utils.SHA1Utils;


/**
 * Created by waycube-yyb on 2016/5/30.API
 */
public class Apis {
    /**
     * 融云聊天服务使用API需提供header:  App-Key  Nonce  Timestamp  Signature
     */
    public static final String RongCloudAppKey = "mgb7ka1nbz20g";
    public static final String RongCloudAppSecret = "IHEZMcpagyNs";
    public static final String RongCloudNonce = "1839508833";

    public static String RongCloudSignature(String timestamp) {
        StringBuilder signature = new StringBuilder();
        signature.append(RongCloudAppSecret);
        signature.append(RongCloudNonce);
        signature.append(timestamp);
        return SHA1Utils.hex_sha1(signature.toString());
    }

    /**
     * 微信 APPID
     */
    public static final String WX_APPID = "wx0eaf4ccbcfc4191d";
}
