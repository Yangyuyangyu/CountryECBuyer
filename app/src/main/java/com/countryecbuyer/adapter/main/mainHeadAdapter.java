package com.countryecbuyer.adapter.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.view.BaseViewHolder;

/**
 * Created by Administrator on 2016/6/13.首页顶部分类
 */
public class mainHeadAdapter extends BaseAdapter {
    private Context mContext;
    public String[] img_text = {"男装", "女装", "家电", "小吃", "数码", "运动", "户外", "家装", "图书"};

    public int[] imgs = {R.drawable.default_load_img, R.drawable.default_load_img, R.drawable.default_load_img,
            R.drawable.default_load_img, R.drawable.default_load_img, R.drawable.default_load_img,
            R.drawable.default_load_img, R.drawable.default_load_img, R.drawable.default_load_img,};

    public mainHeadAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return img_text.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_main_gridview, parent, false);
        }
        final TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
        final ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
        iv.setBackgroundResource(imgs[position]);
        tv.setText(img_text[position]);
        return convertView;
    }
}
