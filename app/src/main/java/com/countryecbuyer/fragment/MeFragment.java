package com.countryecbuyer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.countryecbuyer.ChatActivity;
import com.countryecbuyer.R;
import com.countryecbuyer.activity.PersonalInformationActivity;
import com.countryecbuyer.activity.login.LoginActivity;
import com.countryecbuyer.activity.me.OrderActivity;
import com.countryecbuyer.activity.me.OrderCompleteActivity;
import com.countryecbuyer.activity.me.RecruitmentActivity;
import com.countryecbuyer.activity.me.meAttentionShopActivity;
import com.countryecbuyer.activity.me.meBrowsingHistoryActivity;
import com.countryecbuyer.activity.me.meCashBackActivity;
import com.countryecbuyer.activity.me.meCollectActivity;
import com.countryecbuyer.activity.me.meHaveBuyActivity;
import com.countryecbuyer.activity.me.meHelpFeedbackActivity;
import com.countryecbuyer.activity.me.meSettingActivity;
import com.countryecbuyer.activity.me.meYouHuiTicketActivity;
import com.countryecbuyer.fragment.base.BaseFragment;
import com.countryecbuyer.user_info.UserInfoSharePreference;
import com.countryecbuyer.utils.FileUtils;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/6/1.我的个人信息
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {
    private static final int REQUEST_PERSONALCODE = 6;//已经登录跳转到个人信息
    private static final int REQUEST_EXITCODE = 7;//退出登录

    private ImageView setting;
    private RelativeLayout message;
    private CircleImageView headImg;
    private TextView recruitment;
    private LinearLayout headLL, allOrderLL, collectLL, attentionShopLL, haveBuyLL,
            browsHistoryLL, youHuiTicketLL, crashBackLL, helpFeedBackLL;//登录,全部订单,收藏商品，关注店铺，我买过，浏览记录，优惠券，返现，帮助反馈
    private TextView waitPay, waitOrder, waitDeliver, waitReceiving;//待付款，待接单，待发货，待收货
    private TextView orderFinish;//已完成

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initData() {
        //如果用户登录过则加载头像
        File f = new File(FileUtils.SDPATH, "waycube_user_head.png");
        if (f.exists()) {
            Picasso.with(getActivity())
                    .load(f)
                    .centerCrop()
                    .fit()
                    .into(headImg);
        }
    }

    @Override
    protected void initView() {
        message = customFindViewById(R.id.view_message_relayout);
        setting = customFindViewById(R.id.fragment_me_setting);
        headImg = customFindViewById(R.id.fragment_me_headImg);
        allOrderLL = customFindViewById(R.id.fragment_me_allOrderLL);
        recruitment = customFindViewById(R.id.fragment_me_recruitment);
        headLL = customFindViewById(R.id.fragment_me_headLL);
        waitPay = customFindViewById(R.id.fragment_me_order_waitPay);
        waitOrder = customFindViewById(R.id.fragment_me_order_waitOrder);
        waitDeliver = customFindViewById(R.id.fragment_me_order_waitDeliver);
        waitReceiving = customFindViewById(R.id.fragment_me_order_waitReceiving);
        orderFinish = customFindViewById(R.id.fragment_me_order_finish);

        collectLL = customFindViewById(R.id.fragment_me_collectLL);
        attentionShopLL = customFindViewById(R.id.fragment_me_attentionShopLL);
        haveBuyLL = customFindViewById(R.id.fragment_me_haveBuyLL);
        browsHistoryLL = customFindViewById(R.id.fragment_me_browsingHistoryLL);
        youHuiTicketLL = customFindViewById(R.id.fragment_me_youHuiTicketLL);
        crashBackLL = customFindViewById(R.id.fragment_me_crashBackLL);
        helpFeedBackLL = customFindViewById(R.id.fragment_me_helpFeedBackLL);

        bindListener();
    }

    private void bindListener() {
        message.setOnClickListener(this);
        setting.setOnClickListener(this);
        headLL.setOnClickListener(this);
        allOrderLL.setOnClickListener(this);
        recruitment.setOnClickListener(this);
        waitPay.setOnClickListener(this);
        waitOrder.setOnClickListener(this);
        waitDeliver.setOnClickListener(this);
        waitReceiving.setOnClickListener(this);
        orderFinish.setOnClickListener(this);

        collectLL.setOnClickListener(this);
        attentionShopLL.setOnClickListener(this);
        haveBuyLL.setOnClickListener(this);
        browsHistoryLL.setOnClickListener(this);
        youHuiTicketLL.setOnClickListener(this);
        crashBackLL.setOnClickListener(this);
        helpFeedBackLL.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //消息中心
            case R.id.view_message_relayout:
                startActivityWithoutExtras(ChatActivity.class);
                break;
            //个人设置
            case R.id.fragment_me_setting:
                startActivityForResultWithRequestCode(meSettingActivity.class, REQUEST_EXITCODE);
                break;
            //商家入驻
            case R.id.fragment_me_recruitment:
                startActivityWithoutExtras(RecruitmentActivity.class);
                break;
            //个人信息
            case R.id.fragment_me_headLL:
                //用户没有登录token为空
                if (UserInfoSharePreference.getInstance().getTOKEN().equals("")) {
                    startActivityWithoutExtras(LoginActivity.class);
//                    startActivityForResultWithRequestCode(nickNameActivity.class, REQUEST_NICKNAME);
                } else {
                    startActivityForResultWithRequestCode(PersonalInformationActivity.class, REQUEST_PERSONALCODE);
                }
                break;
            //全部订单
            case R.id.fragment_me_allOrderLL:
                startActivityWithoutExtras(OrderActivity.class);
                break;
            //待付款
            case R.id.fragment_me_order_waitPay:
                Bundle bundle = new Bundle();
                bundle.putInt("order_type", 1);
                startActivityWithExtras(OrderActivity.class, bundle);
                break;
            //待接单
            case R.id.fragment_me_order_waitOrder:
                Bundle bundle2 = new Bundle();
                bundle2.putInt("order_type", 2);
                startActivityWithExtras(OrderActivity.class, bundle2);
                break;
            //待发货
            case R.id.fragment_me_order_waitDeliver:
                Bundle bundle3 = new Bundle();
                bundle3.putInt("order_type", 3);
                startActivityWithExtras(OrderActivity.class, bundle3);
                break;
            //待收货
            case R.id.fragment_me_order_waitReceiving:
                Bundle bundle4 = new Bundle();
                bundle4.putInt("order_type", 4);
                startActivityWithExtras(OrderActivity.class, bundle4);
                break;
            //已完成
            case R.id.fragment_me_order_finish:
                startActivityWithoutExtras(OrderCompleteActivity.class);
                break;
            //收藏商品
            case R.id.fragment_me_collectLL:
                startActivityWithoutExtras(meCollectActivity.class);
                break;
            //关注店铺
            case R.id.fragment_me_attentionShopLL:
                startActivityWithoutExtras(meAttentionShopActivity.class);
                break;
            //我买过的
            case R.id.fragment_me_haveBuyLL:
                startActivityWithoutExtras(meHaveBuyActivity.class);
                break;
            //浏览记录
            case R.id.fragment_me_browsingHistoryLL:
                startActivityWithoutExtras(meBrowsingHistoryActivity.class);
                break;
            //优惠劵
            case R.id.fragment_me_youHuiTicketLL:
                startActivityWithoutExtras(meYouHuiTicketActivity.class);
                break;
            //返现
            case R.id.fragment_me_crashBackLL:
                startActivityWithoutExtras(meCashBackActivity.class);
                break;
            //帮助反馈
            case R.id.fragment_me_helpFeedBackLL:
                startActivityWithoutExtras(meHelpFeedbackActivity.class);
                break;
        }
    }

    /**
     * 接受登录返回的用户信息，进行头像设置,购物车信息设置等等
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUESTCODE && resultCode == RESULTCODE) {
//            Bundle bundle = intent.getExtras();
//            String data = bundle.getString("testResult");
//        }
        switch (requestCode) {
            //个人设置
            case REQUEST_EXITCODE:
                //退出登录后
                if (resultCode == REQUEST_EXITCODE) {
                    Picasso.with(getActivity())
                            .load(R.drawable.default_load_img)
                            .centerCrop()
                            .fit()
                            .into(headImg);
                }
                break;

            //个人信息
            case REQUEST_PERSONALCODE:
                //如果用户登录,获取保存本地的头像
                if (resultCode == REQUEST_PERSONALCODE) {
                    File f = new File(FileUtils.SDPATH, "waycube_user_head.png");
                    if (f.exists()) {
                        Picasso.with(getActivity())
                                .load(f)
                                .centerCrop()
                                .fit()
                                .into(headImg);
                    }
                }
                break;
        }
    }
}
