package com.countryecbuyer.adapter;

import android.content.Context;
import android.view.View;

import com.countryecbuyer.R;
import com.countryecbuyer.adapter.base.SolidRVBaseAdapter;
import com.countryecbuyer.bean.classifyNameBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/2.分类
 */
public class classifyNameAdapter extends SolidRVBaseAdapter<classifyNameBean> {
    private onItemNameClick monItemNameClick;
    private int index = 0;//选中下标

    public classifyNameAdapter(Context context, List<classifyNameBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_classify_name;
    }

    @Override
    protected void onItemClick(int position) {
    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, classifyNameBean bean) {
        holder.setText(R.id.item_classify_name_text, bean.getName());
        holder.setOnItemClickListener(R.id.item_classify_name_text, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = holder.getAdapterPosition();
                monItemNameClick.onclickListener(mBeans.get(holder.getAdapterPosition()).getName());
                notifyDataSetChanged();
            }
        });
        if (holder.getAdapterPosition() == index) {
            holder.setTextColor(R.id.item_classify_name_text, mContext.getResources().getColor(R.color.red_color));
            holder.setViewColor(R.id.item_classify_name_text, mContext.getResources().getColor(R.color.swipe_background_color));
            holder.setIsClick(R.id.item_classify_name_text, false);//已选中不可点击
        } else {
            holder.setTextColor(R.id.item_classify_name_text, mContext.getResources().getColor(R.color.three));
            holder.setViewColor(R.id.item_classify_name_text, mContext.getResources().getColor(R.color.gray_bg));
            holder.setIsClick(R.id.item_classify_name_text, true);//未选中可点击
        }
    }

    public void setOnIteNameClick(onItemNameClick listener) {
        this.monItemNameClick = listener;
    }

    public interface onItemNameClick {
        void onclickListener(String name);
    }

}
