package com.countryecbuyer.utils;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.countryecbuyer.BaseApplication;
import com.countryecbuyer.R;
import com.countryecbuyer.apis.Apis;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListenerV1;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by _SOLID
 * Date:2016/3/30
 * Time:15:02
 */
public class HttpUtils {

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");

    private Handler mHandler;
    private static volatile HttpUtils mInstance;   //JVM 的即时编译器中存在指令重排序的优化,volatile特性之一：禁止指令重排序优化
    private static final OkHttpClient mOkHttpClient;

    static {
        mOkHttpClient = new OkHttpClient().newBuilder().connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build();
    }

    private HttpUtils() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static HttpUtils getInstance() {
        if (mInstance == null)    //第一次检查
            synchronized (HttpUtils.class) {  //加锁
                if (mInstance == null) {   //第二次检查
                    mInstance = new HttpUtils();
                }
            }
        return mInstance;
    }

    /**
     * 不会开启异步线程
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException {
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 开启异步线程访问网络
     *
     * @param request
     * @param responseCallback
     */
    public static void enqueue(Request request, okhttp3.Callback responseCallback) {
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }


    public void loadString(final String url, final HttpCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                try {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onLoading();
                        }
                    });

                    final Response response = execute(request);

                    if (response.isSuccessful()) {//请求成功
                        final String result = response.body().string();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess(result);
                            }
                        });
                    } else {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onError(new Exception("请求失败"));
                            }
                        });
                    }

                } catch (final IOException e) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(e);
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * 同步 post json
     *
     * @param url
     * @param json
     * @param callBack
     */
    public void post(String url, String json, final HttpCallBack callBack) {

        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = null;
        try {
            response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                callBack.onSuccess(response.body().string());
            } else {
                callBack.onError(new Exception("Unexpected code " + response));
            }
        } catch (IOException e) {
            e.printStackTrace();
            callBack.onError(e);
        }

    }

    /**
     * 融云post 表单(需要提供header)
     */
    public void postForm(String url, HashMap<Integer, String> mapKey, HashMap<Integer, String> mapValue, String timestamp, final Callback callback) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if (mapValue.size() == mapKey.size()) {
            int mapSize = mapValue.size();
            for (int i = 0; i < mapSize; i++) {
                builder.addFormDataPart(mapKey.get(i), mapValue.get(i));
            }
        }
        MultipartBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("App-Key", Apis.RongCloudAppKey)
                .addHeader("Nonce", Apis.RongCloudNonce)
                .addHeader("Timestamp", timestamp)
                .addHeader("Signature", Apis.RongCloudSignature(timestamp))
                .post(formBody)
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }


    /**
     * 上传多张图片
     */
    private void uploadImg(String url, HashMap<Integer, String> mImgUrls, final Callback callback) {
        // mImgUrls为存放图片的url集合
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        int imgSize = mImgUrls.size();
        for (int i = 0; i < imgSize; i++) {
            File f = new File(mImgUrls.get(i));
            if (f != null) {
                if (mImgUrls.get(i).contains(".png"))
                    builder.addFormDataPart("pic", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
                else if (mImgUrls.get(i).contains(".jpg"))
                    builder.addFormDataPart("pic", f.getName(), RequestBody.create(MEDIA_TYPE_JPG, f));
            }
        }
        //添加图片信息
        //builder.addFormDataPart("time",takePicTime);
        //builder.addFormDataPart("name",name);
        MultipartBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 加载图片
     *
     * @param url the url of image
     * @param iv  ImageView
     */
    public void loadImage(String url, ImageView iv) {
        loadImage(url, iv, false);
    }


    public void loadImage(String url, ImageView iv, boolean isCenterCrop) {
        loadImageWithHolder(url, iv, R.drawable.default_load_img, isCenterCrop);
    }

    /**
     * 加载图片
     *
     * @param url              the url of image
     * @param iv               ImageView
     * @param placeholderResID default image
     */
    public void loadImageWithHolder(String url, ImageView iv, int placeholderResID, boolean isCenterCrop) {
        RequestCreator creator = Picasso.with(BaseApplication.getInstance()).load(url).placeholder(placeholderResID);
        if (isCenterCrop) {
            creator.centerCrop();
        }
        creator.fit().into(iv);
    }


    public static void downloadFile(String downloadUrl, String savePath, DownloadStatusListenerV1 listener) {
        Uri downloadUri = Uri.parse(downloadUrl);
        Uri destinationUri = Uri.parse(savePath);
        DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
                // .addCustomHeader("Auth-Token", "YourTokenApiKey")
                .setRetryPolicy(new DefaultRetryPolicy())
                .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH);
        if (listener != null) {
            downloadRequest.setStatusListener(listener);
        }

        ThinDownloadManager thinDownloadManager = new ThinDownloadManager(1);
        thinDownloadManager.add(downloadRequest);
    }


    public interface HttpCallBack {
        void onLoading();

        void onSuccess(String result);

        void onError(Exception e);
    }


}
