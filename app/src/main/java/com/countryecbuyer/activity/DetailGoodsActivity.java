package com.countryecbuyer.activity;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.countryecbuyer.MainActivity;
import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.activity.main.SellerStoreActivity;
import com.countryecbuyer.activity.msg.PushMessageActivity;
import com.countryecbuyer.activity.shopcart.shopCartActivity;
import com.countryecbuyer.fragment.bottom_sheet.BS_JoinCartFragment;
import com.countryecbuyer.fragment.bottom_sheet.BS_WeiXinShareFragment;
import com.countryecbuyer.utils.CrazyClickUtils;

import com.countryecbuyer.utils.ToastUtils;
import com.countryecbuyer.view.Pop_msg_View;

/**
 * 商品详情页
 */
public class DetailGoodsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back, cart, share;//顶部
    private TextView kefu, shop, collect;
    private Button joinCart, buyNow;

    protected ProgressBar mProgressBar;
    private WebView webView;

    private Pop_msg_View popview;

    private BS_JoinCartFragment cartFragment;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_detail_goods;
    }

    @Override
    protected void initData() {
        cartFragment = new BS_JoinCartFragment();
    }

    @Override
    protected void initView() {
        mProgressBar = customFindViewById(R.id.progressbar);
        webView = customFindViewById(R.id.detail_goods_web);
        back = customFindViewById(R.id.detail_goods_back);
        cart = customFindViewById(R.id.detail_goods_shopCart);
        share = customFindViewById(R.id.detail_goods_pop);
        kefu = customFindViewById(R.id.detail_goods_kefu);
        shop = customFindViewById(R.id.detail_goods_shop);
        collect = customFindViewById(R.id.detail_goods_collect);
        joinCart = customFindViewById(R.id.detail_goods_joinCart);
        buyNow = customFindViewById(R.id.detail_goods_buyNow);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        back.setOnClickListener(this);
        cart.setOnClickListener(this);
        share.setOnClickListener(this);
        kefu.setOnClickListener(this);
        shop.setOnClickListener(this);
        collect.setOnClickListener(this);
        joinCart.setOnClickListener(this);
        buyNow.setOnClickListener(this);

        initWebViewSettings();
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        mProgressBar.setMax(100);
        webView.loadUrl("https://wap.koudaitong.com/v2/showcase/goods?alias=iq89i86r&source=yzapp&f_platform=yzapp&reft=1467341479539_1467341494176&spm=mars10000_mars10002");

    }

    private void initWebViewSettings() {
        WebSettings webSettings = webView.getSettings();

        //支持获取手势焦点，输入用户名、密码或其他
        webView.requestFocusFromTouch();

        webSettings.setJavaScriptEnabled(true);  //支持js

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小


        webSettings.setSupportZoom(true);  //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。
        //若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放。

        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //1、LayoutAlgorithm.NARROW_COLUMNS ： 适应内容大小
        // 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS); //支持内容重新布局
        webSettings.supportMultipleWindows();  //多窗口
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);  //webview本地缓存有就使用没有网络获取
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
    }

    public boolean canGoBack() {
        return webView != null && webView.canGoBack();
    }

    public void goBack() {
        if (webView != null) {
            webView.goBack();
        }
    }

    //WebViewClient就是帮助WebView处理各种通知、请求事件的。
    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

//        shouldOverrideUrlLoading(WebView view, String url)  最常用的，比如上面的。
//        //在网页上的所有加载都经过这个方法,这个函数我们可以做很多操作。
//        //比如获取url，查看url.contains(“add”)，进行添加操作
//
//        shouldOverrideKeyEvent(WebView view, KeyEvent event)
//        //重写此方法才能够处理在浏览器中的按键事件。
//
//        onPageStarted(WebView view, String url, Bitmap favicon)
//        //这个事件就是开始载入页面调用的，我们可以设定一个loading的页面，告诉用户程序在等待网络响应。
//
//        onPageFinished(WebView view, String url)
//        //在页面加载结束时调用。同样道理，我们可以关闭loading 条，切换程序动作。
//
//        onLoadResource(WebView view, String url)
//        // 在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
//
//        onReceivedError(WebView view, int errorCode, String description, String failingUrl)
//        // (报告错误信息)
//
//        doUpdateVisitedHistory(WebView view, String url, boolean isReload)
//        //(更新历史记录)
//
//        onFormResubmission(WebView view, Message dontResend, Message resend)
//        //(应用程序重新请求网页数据)
//
//        onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host,String realm)
//        //（获取返回信息授权请求）
//
//        onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
//        //重写此方法可以让webview处理https请求。
//
//        onScaleChanged(WebView view, float oldScale, float newScale)
//        // (WebView发生改变时调用)
//
//        onUnhandledKeyEvent(WebView view, KeyEvent event)
//        //（Key事件未被加载时调用）
    }

    //WebChromeClient是辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressBar.setProgress(newProgress);
            if (newProgress == 100) {
                mProgressBar.setVisibility(View.GONE);
            } else {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        }
//        //获取Web页中的title用来设置自己界面中的title
//        //当加载出错的时候，比如无网络，这时onReceiveTitle中获取的标题为 找不到该网页,
//        //因此建议当触发onReceiveError时，不要使用获取到的title
//        @Override
//        public void onReceivedTitle(WebView view, String title) {
//            MainActivity.this.setTitle(title);
//        }
//
//        @Override
//        public void onReceivedIcon(WebView view, Bitmap icon) {
//            //
//        }
//
//        @Override
//        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
//            //
//            return true;
//        }
//
//        @Override
//        public void onCloseWindow(WebView window) {
//        }
//
//        //处理alert弹出框，html 弹框的一种方式
//        @Override
//        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//            //
//            return true;
//        }
//
//        //处理confirm弹出框
//        @Override
//        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult
//                result) {
//            //
//            return true;
//        }
//
//        //处理prompt弹出框
//        @Override
//        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
//            //
//            return true;
//        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.detail_goods_back:
                finish();
                break;
            //购物车
            case R.id.detail_goods_shopCart:
                startActivityWithoutExtras(shopCartActivity.class);
                break;
            //消息分享
            case R.id.detail_goods_pop:
                showPop();
                break;
            //客服
            case R.id.detail_goods_kefu:
                break;
            //店铺
            case R.id.detail_goods_shop:
                startActivityWithoutExtras(SellerStoreActivity.class);
                break;
            //收藏
            case R.id.detail_goods_collect:
                if (CrazyClickUtils.isFastClick()) {
                    return;
                }
                ToastUtils.getInstance().showToastCenter("收藏成功!");
                break;
            //加入购物车
            case R.id.detail_goods_joinCart:
                if (CrazyClickUtils.isFastClick()) {
                    return;
                }
//                ToastUtils.getInstance().showToastCenter("加入购物车成功!");
                Bundle bundle = new Bundle();
                bundle.putInt("cart_type", 0);
                cartFragment.setArguments(bundle);
                cartFragment.show(getSupportFragmentManager(), R.id.detail_goods_bottomSheetLayout);
                break;
            //立即购买
            case R.id.detail_goods_buyNow:
                if (CrazyClickUtils.isFastClick()) {
                    return;
                }
                Bundle bundle2 = new Bundle();
                bundle2.putInt("cart_type", 1);
                cartFragment.setArguments(bundle2);
                cartFragment.show(getSupportFragmentManager(), R.id.detail_goods_bottomSheetLayout);
                break;
        }
    }

    private void showPop() {
        if (popview == null) {
            int ww = ViewGroup.LayoutParams.WRAP_CONTENT;
            int hh = ViewGroup.LayoutParams.WRAP_CONTENT;
            PopLintener paramOnClickListener = new PopLintener();
            popview = new Pop_msg_View(DetailGoodsActivity.this, paramOnClickListener, ww, hh);
            popview.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        popview.dismiss();
                    }
                }
            });
        }
        popview.setFocusable(true);
        popview.showAsDropDown(share);
        popview.update();
    }

    /**
     * popWindown监听
     */
    class PopLintener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pop_msg_view_msgLL:
                    startActivityWithoutExtras(PushMessageActivity.class);
                    popview.dismiss();
                    break;
                case R.id.pop_msg_view_mainLL:
                    startActivityWithoutExtras(MainActivity.class);
                    MainActivity.controller.setSelect("yh");
                    popview.dismiss();
                    finish();
                    break;
                case R.id.pop_msg_view_shareLL:
                    popview.dismiss();
                    new BS_WeiXinShareFragment().show(getSupportFragmentManager(), R.id.detail_goods_bottomSheetLayout);
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (webView != null)
            webView.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (webView != null)
            webView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        popview = null;
        ToastUtils.getInstance().cancelToast();
        if (webView != null)
            webView.destroy();
    }

    @Override
    public void onBackPressed() {
        if (canGoBack()) {
            goBack();
        } else {
            finish();
        }
    }
}
