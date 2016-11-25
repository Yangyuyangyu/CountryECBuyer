package com.countryecbuyer.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.countryecbuyer.MainActivity;
import com.countryecbuyer.R;
import com.countryecbuyer.adapter.shopping_cart.MyBaseExpandableListAdapter;
import com.countryecbuyer.bean.mainBean;
import com.countryecbuyer.bean.shopping_cart.GoodsBean;
import com.countryecbuyer.bean.shopping_cart.StoreBean;
import com.countryecbuyer.fragment.base.BaseFragment;
import com.countryecbuyer.utils.FileUtils;
import com.countryecbuyer.utils.HttpUtils;
import com.countryecbuyer.utils.NetworkUtils;
import com.countryecbuyer.utils.StringUtils;
import com.countryecbuyer.utils.ToastUtils;
import com.countryecbuyer.view.AutoSwipeRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/1.购物车
 */
public class ShoppingCartFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final int REFRESH_COMPLETE = 0X118;
    private Button empty_goodsBtn;
    private AutoSwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout emptyLL;
    /**
     * expandListView设计购物车
     */
    //店铺列表集合
    List<Map<String, Object>> parentMapList = new ArrayList<Map<String, Object>>();
    //店铺下商品列表集合
    List<List<Map<String, Object>>> childMapList_list = new ArrayList<List<Map<String, Object>>>();

    private ExpandableListView expandableListView;
    private MyBaseExpandableListAdapter myBaseExpandableListAdapter;
    private CheckBox foot_select_all;//底部全选
    private LinearLayout foot_ll_normal_all_state;//底部常规状态
    private LinearLayout foot_ll_editing_all_state;//底部编辑状态
    private RelativeLayout foot_rl;//底部布局
    private TextView id_tv_delete_all;//底部删除
    private TextView id_tv_totalPrice;//底部总价
    private TextView id_tv_totalCount_jiesuan;//底部结算


    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
//                    adapter.clear();
                    parentMapList.clear();
                    childMapList_list.clear();
                    getData();
                    swipeRefreshLayout.setRefreshing(false);
                    foot_rl.setVisibility(View.VISIBLE);
                    expandableListView.setVisibility(View.VISIBLE);
                    ToastUtils.getInstance().showToast("刷新完成");
                    break;
            }
        }
    };

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_shoppingcart;
    }

    @Override
    protected void initData() {
        swipeRefreshLayout.autoRefresh();

    }

    @Override
    protected void initView() {
        empty_goodsBtn = customFindViewById(R.id.empty_goods_btn);
        emptyLL = customFindViewById(R.id.ll_reload_wrap);
        swipeRefreshLayout = customFindViewById(R.id.shopping_cart_swipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.swipe_color_1,
                R.color.swipe_color_2,
                R.color.swipe_color_3,
                R.color.swipe_color_4);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setProgressBackgroundColor(R.color.swipe_background_color);
        swipeRefreshLayout.setOnRefreshListener(this);
        empty_goodsBtn.setOnClickListener(this);

        //购物车
        foot_ll_normal_all_state = customFindViewById(R.id.id_ll_normal_all_state);
        foot_ll_editing_all_state = customFindViewById(R.id.id_ll_editing_all_state);
        foot_select_all = customFindViewById(R.id.id_cb_select_all);
        foot_rl = customFindViewById(R.id.shopping_cart_foot);
        expandableListView = customFindViewById(R.id.shopping_cart_expandListview);
        myBaseExpandableListAdapter = new MyBaseExpandableListAdapter(getContext(), parentMapList, childMapList_list);
        expandableListView.setAdapter(myBaseExpandableListAdapter);
        expandableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.getInstance().showToastCenter("click：" + position);
            }
        });

        //底部删除
        id_tv_delete_all = customFindViewById(R.id.id_tv_delete_all);
        id_tv_delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBaseExpandableListAdapter.removeGoods();
            }
        });
        //底部全选
        foot_select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) v;
                    myBaseExpandableListAdapter.setupAllChecked(checkBox.isChecked());
                }
            }
        });

        id_tv_totalPrice = customFindViewById(R.id.id_tv_totalPrice);

        id_tv_totalCount_jiesuan = customFindViewById(R.id.id_tv_totalCount_jiesuan);
        id_tv_totalCount_jiesuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.getInstance().showToastCenter("结算跳转");
            }
        });

        myBaseExpandableListAdapter.setOnGoodsCheckedChangeListener(new MyBaseExpandableListAdapter.OnGoodsCheckedChangeListener() {
            @Override
            public void onGoodsCheckedChange(int totalCount, double totalPrice) {
                id_tv_totalPrice.setText(String.format(getString(R.string.total), totalPrice));
                id_tv_totalCount_jiesuan.setText(String.format(getString(R.string.jiesuan), totalCount));
            }
        });

        myBaseExpandableListAdapter.setOnAllCheckedBoxNeedChangeListener(new MyBaseExpandableListAdapter.OnAllCheckedBoxNeedChangeListener() {
            @Override
            public void onCheckedBoxNeedChange(boolean allParentIsChecked) {
                foot_select_all.setChecked(allParentIsChecked);
            }
        });
        myBaseExpandableListAdapter.setOnEditingTvChangeListener(new MyBaseExpandableListAdapter.OnEditingTvChangeListener() {
            @Override
            public void onEditingTvChange(boolean allIsEditing) {

                changeFootShowDeleteView(allIsEditing);//这边类似的功能 后期待使用观察者模式

            }
        });

        myBaseExpandableListAdapter.setOnCheckHasGoodsListener(new MyBaseExpandableListAdapter.OnCheckHasGoodsListener() {
            @Override
            public void onCheckHasGoods(boolean isHasGoods) {
                setEmptyViewsShow(isHasGoods);
            }
        });

    }

    //有无数据
    private void setEmptyViewsShow(boolean isHasGoods) {
        if (isHasGoods) {
            expandableListView.setVisibility(View.VISIBLE);
            emptyLL.setVisibility(View.GONE);
            foot_rl.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setEnabled(true);
        } else {
            expandableListView.setVisibility(View.GONE);
            emptyLL.setVisibility(View.VISIBLE);//列表为空显示
            foot_rl.setVisibility(View.GONE);
            swipeRefreshLayout.setEnabled(false);
        }
    }

    //底部模式转换
    public void changeFootShowDeleteView(boolean showDeleteView) {
        if (showDeleteView) {
            foot_ll_normal_all_state.setVisibility(View.INVISIBLE);
            foot_ll_editing_all_state.setVisibility(View.VISIBLE);
        } else {
            foot_ll_normal_all_state.setVisibility(View.VISIBLE);
            foot_ll_editing_all_state.setVisibility(View.INVISIBLE);
        }
    }

    private void getData() {
        for (int i = 0; i < 4; i++) {
            String store = "一品堂旗舰店";
            if (i % 2 == 0) {
                store = "一品堂专营店";
            }
            Map<String, Object> parentMap = new HashMap<String, Object>();
            parentMap.put("parentName", new StoreBean("" + i, store + i, false, false));
            parentMapList.add(parentMap);
            List<Map<String, Object>> childMapList = new ArrayList<Map<String, Object>>();

            if (i == 0) {
                Map<String, Object> childMap = new HashMap<String, Object>();
                GoodsBean goodsBean = new GoodsBean(i + "_", store + i + "下的商品", "url", "80g，茗茶", 150, 120, 1, GoodsBean.STATUS_VALID, false, false);
                childMap.put("childName", goodsBean);
                childMapList.add(childMap);
            } else {
                for (int j = 0; j < 3; j++) {
                    Map<String, Object> childMap = new HashMap<String, Object>();
                    GoodsBean goodsBean = new GoodsBean(i + "_" + j, store + i + "下的商品" + j, "url", "80g，茗茶", 150, 120, 1, GoodsBean.STATUS_VALID, false, false);
                    childMap.put("childName", goodsBean);
                    childMapList.add(childMap);
                }
            }
            childMapList_list.add(childMapList);
        }

        myBaseExpandableListAdapter.notifyDataSetChanged();
        for (int i = 0; i < parentMapList.size(); i++) {
            expandableListView.expandGroup(i);
        }
        if (parentMapList != null && parentMapList.size() > 0) {
            setEmptyViewsShow(true);
        } else {
            setEmptyViewsShow(false);
        }
    }


    /**
     * 加载与缓存
     */
    private void loadData() {
        final String reqUrl = "";
        if (!NetworkUtils.isNetworkConnected(getMContext())) {//no network
            String result = obtainOfflineData(reqUrl);//读取缓存
            onDataSuccessReceived(result);
//            MySnackbar.getmInstance().showMessage(fab, "当前无网络连接");
        } else
            HttpUtils.getInstance().loadString(reqUrl, new HttpUtils.HttpCallBack() {
                @Override
                public void onLoading() {
                }

                @Override
                public void onSuccess(String result) {
                    //刷新缓存数据
//                    if (mCurrentAction == ACTION_REFRESH) {
//                        storeOfflineData(reqUrl, result);
//                    }
                    onDataSuccessReceived(result);
                }

                @Override
                public void onError(Exception e) {
                    onDataErrorReceived();
                }
            });
    }

    /**
     * 获取数据失败
     */
    private void onDataErrorReceived() {
//        mLLReloadWarp.setVisibility(View.VISIBLE);
//        loadComplete();
    }

    /**
     * 获取数据成功
     */
    private void onDataSuccessReceived(String result) {
        if (!StringUtils.isNullOrEmpty(result)) {
            List<mainBean> list = parseData(result);
//            mAdapter.addAll(list);
//            loadComplete();
//            mLLReloadWarp.setVisibility(View.GONE);
        } else {
            onDataErrorReceived();
        }
    }

    /**
     * json解析返回List
     */
    private List parseData(String result) {
        List<mainBean> list = null;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
            if (jsonObject.getString("code").equals("0")) {
                Gson gson = new Gson();
                list = gson.fromJson(
                        jsonObject.getString("data"),
                        new TypeToken<List<mainBean>>() {
                        }.getType());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            list = new ArrayList<>();
        }
        return list;

    }

    /**
     * 缓存离线数据
     */
    public void storeOfflineData(String url, String result) {
        try {
            FileUtils.writeFile(getOfflineDir(url), result, "UTF-8", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取离线数据
     */
    public String obtainOfflineData(String url) {
        String result = null;
        try {
            result = FileUtils.readFile(getOfflineDir(url), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * get the dir of the offline data
     */
    protected String getOfflineDir(String url) {
        return FileUtils.getCacheDir(getMContext()) + File.separator + "waycube_shop_cache" + File.separator + StringUtils.md5(url);
    }


    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.empty_goods_btn:
                MainActivity.controller.setSelect("yh");
                break;
        }
    }
}
