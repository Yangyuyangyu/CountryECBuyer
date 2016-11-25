package com.countryecbuyer.adapter.search;

import android.content.Context;

import com.countryecbuyer.R;
import com.countryecbuyer.adapter.base.SolidRVBaseAdapter;
import com.countryecbuyer.bean.search.SearchDataBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/15.搜索
 */
public class SearchDataAdapter extends SolidRVBaseAdapter<SearchDataBean> {
    private OnItemlistener itemlistener;

    public SearchDataAdapter(Context context, List<SearchDataBean> beans) {
        super(context, beans);
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_search;
    }

    @Override
    protected void onItemClick(int position) {
        itemlistener.onItemClick(mBeans.get(position).getName());
    }

    @Override
    protected void onBindDataToView(final SolidCommonViewHolder holder, final SearchDataBean bean) {
        holder.setText(R.id.item_search_name, bean.getName());
    }

    public void setOnItemlistener(OnItemlistener listener) {
        this.itemlistener = listener;
    }

    public interface OnItemlistener {
        void onItemClick(String name);
    }
}
