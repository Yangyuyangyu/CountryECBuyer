package com.countryecbuyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.WonderfulReviewActivity;
import com.countryecbuyer.adapter.base.SolidMultiItemTypeRVBaseAdapter;
import com.countryecbuyer.adapter.main.mainFootAdapter;
import com.countryecbuyer.adapter.main.mainHeadAdapter;
import com.countryecbuyer.bean.main.mainFootBean;
import com.countryecbuyer.bean.mainBean;

import com.countryecbuyer.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/12.测试多ITEM类型
 */
public class testAdapter extends SolidMultiItemTypeRVBaseAdapter<mainBean> {
    private LayoutInflater inflater;
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 1;
    //正在加载中
    public static final int LOADING_MORE = 2;
    //没有更多了
    public static final int LOADING_COMPLETE = 3;
    //默认为0
    private int load_more_status = 1;

    /**
     * 建立枚举类型  顶部 ，list的RecyclerView，更多精彩，grid的RecyclerView，底部
     */
    public enum ITEM_TYPE {
        ITEM_TOP,
        ITEM_REC_LIST,
        ITEM_MORE,
        ITEM_REC_GRID,
        ITEM_BOTTOM
    }

    public testAdapter(Context context, List<mainBean> beans) {
        super(context, beans);
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE.ITEM_TOP.ordinal();
        } else if (position == 1) {
            return ITEM_TYPE.ITEM_REC_LIST.ordinal();
        } else if (position == 2) {
            return ITEM_TYPE.ITEM_MORE.ordinal();
        } else if (position == 3) {
            return ITEM_TYPE.ITEM_REC_GRID.ordinal();
        } else if (position == getItemCount() - 1) {
            return ITEM_TYPE.ITEM_BOTTOM.ordinal();
        } else {
            return ITEM_TYPE.ITEM_REC_GRID.ordinal();
        }
    }

    @Override
    public SolidCommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TOP.ordinal()) {
            return new topViewHolder(inflater.inflate(getItemLayoutID(viewType), parent, false));
        } else if (viewType == ITEM_TYPE.ITEM_REC_LIST.ordinal()) {
            return new listViewHolder(inflater.inflate(getItemLayoutID(viewType), parent, false));
        } else if (viewType == ITEM_TYPE.ITEM_MORE.ordinal()) {
            return new moreViewHolder(inflater.inflate(getItemLayoutID(viewType), parent, false));
        } else if (viewType == ITEM_TYPE.ITEM_REC_GRID.ordinal()) {
            return new gridViewHolder(inflater.inflate(getItemLayoutID(viewType), parent, false));
        } else {
            return new footViewHolder(inflater.inflate(getItemLayoutID(viewType), parent, false));
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getItemLayoutID(int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TOP.ordinal()) {
            return R.layout.include_main_recycle_headview;
        } else if (viewType == ITEM_TYPE.ITEM_REC_LIST.ordinal()) {
            return R.layout.a_test_list;
        } else if (viewType == ITEM_TYPE.ITEM_MORE.ordinal()) {
            return R.layout.a_test_more;
        } else if (viewType == ITEM_TYPE.ITEM_REC_GRID.ordinal()) {
            return R.layout.a_test_grid;
        } else if (viewType == ITEM_TYPE.ITEM_BOTTOM.ordinal()) {
            return R.layout.a_test_foot;
        } else {
            return R.layout.a_test_grid;
        }
    }

    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, mainBean bean) {
        if (holder instanceof topViewHolder) {
            ((topViewHolder) holder).myGridView.setAdapter(new mainHeadAdapter(mContext));
            ((topViewHolder) holder).myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
        } else if (holder instanceof listViewHolder) {
            LinearLayoutManager linearManager = new LinearLayoutManager(mContext);
            mainAdapter mainadapter = new mainAdapter(mContext, new ArrayList<mainBean>());
            ((listViewHolder) holder).recyclerView_list.setAdapter(mainadapter);
            ((listViewHolder) holder).recyclerView_list.setHasFixedSize(true);
            ((listViewHolder) holder).recyclerView_list.setLayoutManager(linearManager);
            addListData(mainadapter);
        } else if (holder instanceof moreViewHolder) {
            ((moreViewHolder) holder).more_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, WonderfulReviewActivity.class);
                    mContext.startActivity(intent);
                }
            });

        } else if (holder instanceof gridViewHolder) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
            mainFootAdapter footAdapter = new mainFootAdapter(mContext, new ArrayList<mainFootBean>());
            ((gridViewHolder) holder).recyclerView_grid.setHasFixedSize(true);
            ((gridViewHolder) holder).recyclerView_grid.setAdapter(footAdapter);
            ((gridViewHolder) holder).recyclerView_grid.setLayoutManager(gridLayoutManager);
            addFootData(footAdapter);
        } else if (holder instanceof footViewHolder) {
            switch (load_more_status) {
                case PULLUP_LOAD_MORE:
                    ((footViewHolder) holder).tv.setVisibility(View.VISIBLE);
                    ((footViewHolder) holder).tv.setText("上拉加载更多");
                    ((footViewHolder) holder).progressBar.setVisibility(View.GONE);
                    break;
                case LOADING_MORE:
                    ((footViewHolder) holder).tv.setVisibility(View.GONE);
                    ((footViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
                    break;
                case LOADING_COMPLETE:
                    ((footViewHolder) holder).progressBar.setVisibility(View.GONE);
                    ((footViewHolder) holder).tv.setVisibility(View.VISIBLE);
                    ((footViewHolder) holder).tv.setText("已经到底了哦");
                    break;
            }
        }

    }

    @Override
    protected void onItemClick(int position) {
        super.onItemClick(position);
    }

    public class topViewHolder extends SolidCommonViewHolder {
        MyGridView myGridView;

        public topViewHolder(View view) {
            super(view);
            myGridView = (MyGridView) view.findViewById(R.id.main_headView_gridview);
        }
    }

    public class listViewHolder extends SolidCommonViewHolder {
        RecyclerView recyclerView_list;

        public listViewHolder(View view) {
            super(view);
            recyclerView_list = (RecyclerView) view.findViewById(R.id.test_rec_list);
        }
    }

    public class moreViewHolder extends SolidCommonViewHolder {
        Button more_btn;

        public moreViewHolder(View view) {
            super(view);
            more_btn = (Button) view.findViewById(R.id.include_main_foot_btn);
        }
    }

    public class gridViewHolder extends SolidCommonViewHolder {
        RecyclerView recyclerView_grid;

        public gridViewHolder(View view) {
            super(view);
            recyclerView_grid = (RecyclerView) view.findViewById(R.id.test_rec_grid);
        }
    }

    public class footViewHolder extends SolidCommonViewHolder {
        TextView tv;
        ProgressBar progressBar;

        public footViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.test_foot_text);
            progressBar = (ProgressBar) view.findViewById(R.id.test_foot_progress);
        }
    }

    /**
     * 底部加载状态
     */
    public void setMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }


    private void addListData(mainAdapter adapter) {
        List<mainBean> mbList = new ArrayList<mainBean>();

        mainBean mb1 = new mainBean();
        mainBean mb2 = new mainBean();
        mainBean mb3 = new mainBean();
        mainBean mb4 = new mainBean();
        mainBean mb5 = new mainBean();
        mbList.add(mb1);
        mbList.add(mb2);
        mbList.add(mb3);
        mbList.add(mb4);
        mbList.add(mb5);
        adapter.addAll(mbList);
    }

    private void addFootData(mainFootAdapter adapter) {
        List<mainFootBean> list = new ArrayList<mainFootBean>();
        mainFootBean m1 = new mainFootBean();
        mainFootBean m2 = new mainFootBean();
        list.add(m1);
        list.add(m2);
        adapter.addAll(list);
    }

}
