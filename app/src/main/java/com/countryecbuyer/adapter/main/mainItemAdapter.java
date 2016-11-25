package com.countryecbuyer.adapter.main;

import android.content.Context;
import android.content.Intent;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.DetailGoodsActivity;
import com.countryecbuyer.adapter.base.SolidRVBaseAdapter;
import com.countryecbuyer.bean.main.mainItemBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/17.点击首页列表进入展示
 */
public class mainItemAdapter extends SolidRVBaseAdapter<mainItemBean> {

//    public static final int PULLUP_LOAD_MORE = 0;//上拉加载更多
//    public static final int LOADING_MORE = 1;//正在加载中
//    private int load_more_status = 0;//上拉加载更多状态默认为0
//    private static final int TYPE_ITEM = 0;  //普通Item View
//    private static final int TYPE_FOOTER = 1;  //顶部FootView

    public mainItemAdapter(Context context, List<mainItemBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_main_item;
    }

    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, DetailGoodsActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final mainItemBean bean) {

    }

//    @Override
//    public void onBindViewHolder(SolidCommonViewHolder holder, int position) {
//        super.onBindViewHolder(holder, position);
//
////        runEnterAnimation(holder.itemView, position);
////        onBindDataToView(holder, mBeans.get(position));
//    }
//
//
//
//    /**
//     * 添加上拉加载foot
//     */
//    @Override
//    public int getItemCount() {
//        return mBeans.size() + 1;//添加foot
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        // 最后一个item设置为footerView
//        if (position + 1 == getItemCount()) {
//            return TYPE_FOOTER;
//        } else {
//            return TYPE_ITEM;
//        }
//    }
//
//    @Override
//    public SolidCommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        //进行判断显示类型，来创建返回不同的View
//        if (viewType == TYPE_ITEM) {
//            return super.onCreateViewHolder(parent, viewType);
//        } else if (viewType == TYPE_FOOTER) {
//            LayoutInflater inflater = LayoutInflater.from(mContext);
////            Viewfoot_view=mInflater.inflate(R.layout.recycler_load_more_layout,parent,false);
////            //这边可以做一些属性设置，甚至事件监听绑定
////            //view.setBackgroundColor(Color.RED);
////            FootViewHolder footViewHolder=new FootViewHolder(foot_view);
////            return footViewHolder;
//        }
//        return null;
//
//    }
}
