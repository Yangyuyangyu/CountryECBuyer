package com.countryecbuyer.adapter.shopping_cart;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.countryecbuyer.MainActivity;
import com.countryecbuyer.R;
import com.countryecbuyer.bean.shopping_cart.GoodsBean;
import com.countryecbuyer.bean.shopping_cart.StoreBean;
import com.countryecbuyer.view.sweet_alert_dialog.SweetAlertDialog;

import java.util.List;
import java.util.Map;

/**
 * Created by louisgeek on 2016/4/27. 购物车expandListView 适配器
 */
public class MyBaseExpandableListAdapter extends BaseExpandableListAdapter {
    private List<Map<String, Object>> parentMapList;//店铺信息
    private List<List<Map<String, Object>>> childMapList_list;//店铺下的商品信息
    private Context context;
    private int totalCount = 0;
    private double totalPrice = 0.00;
    public static final String EDITING = "编辑";
    public static final String FINISH_EDITING = "完成";
    private OnAllCheckedBoxNeedChangeListener onAllCheckedBoxNeedChangeListener;//全选
    private OnGoodsCheckedChangeListener onGoodsCheckedChangeListener;//item选择
    private OnCheckHasGoodsListener onCheckHasGoodsListener;//状态重置，用于刷新
    private OnEditingTvChangeListener onEditingTvChangeListener;//编辑

    public void setOnCheckHasGoodsListener(OnCheckHasGoodsListener onCheckHasGoodsListener) {
        this.onCheckHasGoodsListener = onCheckHasGoodsListener;
    }

    public void setOnEditingTvChangeListener(OnEditingTvChangeListener onEditingTvChangeListener) {
        this.onEditingTvChangeListener = onEditingTvChangeListener;
    }

    public void setOnGoodsCheckedChangeListener(OnGoodsCheckedChangeListener onGoodsCheckedChangeListener) {
        this.onGoodsCheckedChangeListener = onGoodsCheckedChangeListener;
    }

    public void setOnAllCheckedBoxNeedChangeListener(OnAllCheckedBoxNeedChangeListener onAllCheckedBoxNeedChangeListener) {
        this.onAllCheckedBoxNeedChangeListener = onAllCheckedBoxNeedChangeListener;
    }

    public MyBaseExpandableListAdapter(Context context, List<Map<String, Object>> parentMapList, List<List<Map<String, Object>>> childMapList_list) {
        this.parentMapList = parentMapList;
        this.childMapList_list = childMapList_list;
        this.context = context;
    }


    //获取当前父item的数据数量
    @Override
    public int getGroupCount() {
        return parentMapList.size();
    }

    //获取当前父item下的子item的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return childMapList_list.get(groupPosition).size();
    }

    //获取当前父item的数据
    @Override
    public Object getGroup(int groupPosition) {
        return parentMapList.get(groupPosition);
    }

    //得到子item需要关联的数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childMapList_list.get(groupPosition).get(childPosition);
    }

    //得到父item的ID
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //得到子item的ID
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    //设置父item组件
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.a_shop_parent_layout, null);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tv_title_parent = (TextView) convertView
                    .findViewById(R.id.tv_title_parent);
            groupViewHolder.id_tv_edit = (TextView) convertView
                    .findViewById(R.id.id_tv_edit);
            groupViewHolder.id_cb_select_parent = (CheckBox) convertView
                    .findViewById(R.id.id_cb_select_parent);

            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        StoreBean storeBean = (StoreBean) parentMapList.get(groupPosition).get("parentName");
        final String parentName = storeBean.getName();
        groupViewHolder.tv_title_parent.setText(parentName);

        if (storeBean.isEditing()) {
            groupViewHolder.id_tv_edit.setText(FINISH_EDITING);
        } else {
            groupViewHolder.id_tv_edit.setText(EDITING);
        }
        groupViewHolder.id_tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "";
                TextView textView = null;
                if (v instanceof TextView) {
                    textView = (TextView) v;
                }
                textView.setText(text);
                setupEditing(groupPosition);

                onEditingTvChangeListener.onEditingTvChange(dealAllEditingIsEditing());
            }
        });

        //覆盖原有收起展开事件
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "店铺：" + parentName, Toast.LENGTH_SHORT).show();
            }
        });


        groupViewHolder.id_cb_select_parent.setChecked(storeBean.isChecked());
        final boolean nowBeanChecked = storeBean.isChecked();
        groupViewHolder.id_cb_select_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupOneParentAllChildChecked(!nowBeanChecked, groupPosition);
                //控制总checkedbox 接口
                onAllCheckedBoxNeedChangeListener.onCheckedBoxNeedChange(dealAllParentIsChecked());
            }
        });
        return convertView;
    }

    //设置子item的组件
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.a_shop_child_layout, null);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tv_items_child = (TextView) convertView
                    .findViewById(R.id.tv_items_child);
            childViewHolder.id_cb_select_child = (CheckBox) convertView
                    .findViewById(R.id.id_cb_select_child);
            childViewHolder.id_ll_normal = (LinearLayout) convertView
                    .findViewById(R.id.id_ll_normal);
            childViewHolder.id_ll_edtoring = (LinearLayout) convertView
                    .findViewById(R.id.id_ll_edtoring);
            //常规下：
            childViewHolder.tv_items_child_desc = (TextView) convertView
                    .findViewById(R.id.tv_items_child_desc);
            childViewHolder.id_tv_price = (TextView) convertView
                    .findViewById(R.id.id_tv_price);
            childViewHolder.id_tv_discount_price = (TextView) convertView
                    .findViewById(R.id.id_tv_discount_price);
            childViewHolder.id_tv_count = (TextView) convertView
                    .findViewById(R.id.id_tv_count);
            //编辑下：
            childViewHolder.id_iv_reduce = (ImageView) convertView
                    .findViewById(R.id.id_iv_reduce);
            childViewHolder.id_iv_add = (ImageView) convertView
                    .findViewById(R.id.id_iv_add);
            childViewHolder.id_tv_count_now = (TextView) convertView
                    .findViewById(R.id.id_tv_count_now);
            childViewHolder.id_tv_price_now = (TextView) convertView
                    .findViewById(R.id.id_tv_price_now);
            childViewHolder.id_tv_des_now = (TextView) convertView
                    .findViewById(R.id.id_tv_des_now);
            childViewHolder.id_tv_goods_star = (TextView) convertView
                    .findViewById(R.id.id_tv_goods_star);
            childViewHolder.id_tv_goods_delete = (TextView) convertView
                    .findViewById(R.id.id_tv_goods_delete);


            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }


        final GoodsBean goodsBean = (GoodsBean) childMapList_list.get(groupPosition).get(childPosition).get("childName");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "商品：" + goodsBean.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        childViewHolder.tv_items_child.setText(goodsBean.getName());
        childViewHolder.id_tv_price.setText(String.format(context.getString(R.string.price), goodsBean.getPrice()));
        childViewHolder.id_tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并抗锯齿
        childViewHolder.id_tv_discount_price.setText(String.format(context.getString(R.string.price), goodsBean.getDiscountPrice()));
        childViewHolder.tv_items_child_desc.setText(String.valueOf(goodsBean.getDesc()));

        childViewHolder.id_tv_count.setText(String.format(context.getString(R.string.good_count), goodsBean.getCount()));
        childViewHolder.id_tv_count_now.setText(String.valueOf(goodsBean.getCount()));

        double priceNow = goodsBean.getCount() * goodsBean.getDiscountPrice();//小结
        childViewHolder.id_tv_price_now.setText(String.format(context.getString(R.string.price), priceNow));
        childViewHolder.id_tv_des_now.setText(goodsBean.getDesc());

        childViewHolder.id_cb_select_child.setChecked(goodsBean.isChecked());
        childViewHolder.id_cb_select_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean nowBeanChecked = goodsBean.isChecked();
                //更新数据
                goodsBean.setIsChecked(!nowBeanChecked);

                boolean isOneParentAllChildIsChecked = dealOneParentAllChildIsChecked(groupPosition);
                StoreBean storeBean = (StoreBean) parentMapList.get(groupPosition).get("parentName");
                storeBean.setIsChecked(isOneParentAllChildIsChecked);


                notifyDataSetChanged();
                //控制总checkedbox 接口
                onAllCheckedBoxNeedChangeListener.onCheckedBoxNeedChange(dealAllParentIsChecked());
                dealPrice();
            }
        });

        if (goodsBean.isEditing()) {
            childViewHolder.id_ll_normal.setVisibility(View.GONE);
            childViewHolder.id_ll_edtoring.setVisibility(View.VISIBLE);
        } else {
            childViewHolder.id_ll_normal.setVisibility(View.VISIBLE);
            childViewHolder.id_ll_edtoring.setVisibility(View.GONE);
        }

        childViewHolder.id_iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealAdd(goodsBean);
            }
        });
        childViewHolder.id_iv_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealReduce(goodsBean);
            }
        });

        childViewHolder.id_tv_goods_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "收藏商品：" + goodsBean.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        childViewHolder.id_tv_goods_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(context).setTitleText("提示")
                        .setContentText("确定要删除该商品吗?")
                        .setConfirmText("确定")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                removeOneGood(groupPosition, childPosition);
                                setMessageNumber();
                                //如果该组商品全部删除则返回编辑状态

                            }
                        }).show();
            }
        });
        return convertView;
    }

    private void setMessageNumber() {
        int length = parentMapList.size();
        int allCount = 0;
        for (int i = 0; i < length; i++) {
            allCount += childMapList_list.get(i).size();
        }
        MainActivity.controller.setMessageNumber("gwc", allCount);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //供全选按钮调用
    public void setupAllChecked(boolean isChecked) {
        for (int i = 0; i < parentMapList.size(); i++) {
            StoreBean storeBean = (StoreBean) parentMapList.get(i).get("parentName");
            storeBean.setIsChecked(isChecked);

            List<Map<String, Object>> childMapList = childMapList_list.get(i);
            for (int j = 0; j < childMapList.size(); j++) {
                GoodsBean goodsBean = (GoodsBean) childMapList.get(j).get("childName");
                goodsBean.setIsChecked(isChecked);
            }
        }
        notifyDataSetChanged();
        dealPrice();
    }

    private void setupOneParentAllChildChecked(boolean isChecked, int groupPosition) {
        StoreBean storeBean = (StoreBean) parentMapList.get(groupPosition).get("parentName");
        storeBean.setIsChecked(isChecked);
        List<Map<String, Object>> childMapList = childMapList_list.get(groupPosition);
        for (int j = 0; j < childMapList.size(); j++) {
            GoodsBean goodsBean = (GoodsBean) childMapList.get(j).get("childName");
            goodsBean.setIsChecked(isChecked);
        }
        notifyDataSetChanged();
        dealPrice();
    }

    public boolean dealOneParentAllChildIsChecked(int groupPosition) {
        List<Map<String, Object>> childMapList = childMapList_list.get(groupPosition);
        for (int j = 0; j < childMapList.size(); j++) {
            GoodsBean goodsBean = (GoodsBean) childMapList.get(j).get("childName");
            if (!goodsBean.isChecked()) {
                return false;//如果有一个没选择  就false
            }
        }
        return true;
    }

    public boolean dealAllParentIsChecked() {
        for (int i = 0; i < parentMapList.size(); i++) {
            StoreBean storeBean = (StoreBean) parentMapList.get(i).get("parentName");
            if (!storeBean.isChecked()) {
                return false;//如果有一个没选择  就false
            }
        }
        return true;
    }

    public void dealPrice() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < parentMapList.size(); i++) {
            List<Map<String, Object>> childMapList = childMapList_list.get(i);
            for (int j = 0; j < childMapList.size(); j++) {
                GoodsBean goodsBean = (GoodsBean) childMapList.get(j).get("childName");
                int count = goodsBean.getCount();
                double discountPrice = goodsBean.getDiscountPrice();
                if (goodsBean.isChecked()) {
                    totalCount++;//单品多数量只记1
                    totalPrice += discountPrice * count;
                }

            }
        }
        //价格数量计算回调
        onGoodsCheckedChangeListener.onGoodsCheckedChange(totalCount, totalPrice);
    }

    public boolean dealAllEditingIsEditing() {
        for (int i = 0; i < parentMapList.size(); i++) {
            StoreBean storeBean = (StoreBean) parentMapList.get(i).get("parentName");
            if (storeBean.isEditing()) {
                return true;//如果有一个是编辑状态  就true
            }
        }
        return false;
    }

    public void setupEditing(int groupPosition) {
        StoreBean storeBean = (StoreBean) parentMapList.get(groupPosition).get("parentName");
        boolean isEditing = !storeBean.isEditing();
        storeBean.setIsEditing(isEditing);
        List<Map<String, Object>> childMapList = childMapList_list.get(groupPosition);
        for (int j = 0; j < childMapList.size(); j++) {
            GoodsBean goodsBean = (GoodsBean) childMapList.get(j).get("childName");
            goodsBean.setIsEditing(isEditing);
        }
        notifyDataSetChanged();
    }

    public void dealAdd(GoodsBean goodsBean) {
        int count = goodsBean.getCount();
        count++;
        goodsBean.setCount(count);
        notifyDataSetChanged();
        dealPrice();
    }

    public void dealReduce(GoodsBean goodsBean) {
        int count = goodsBean.getCount();
        if (count == 1) {
            return;
        }
        count--;
        goodsBean.setCount(count);
        notifyDataSetChanged();
        dealPrice();
    }

    public void removeOneGood(int groupPosition, int childPosition) {
        List<Map<String, Object>> childMapList = childMapList_list.get(groupPosition);
        childMapList.remove(childPosition);
        //通过子项
        if (childMapList != null && childMapList.size() > 0) {

        } else {
            parentMapList.remove(groupPosition);
            childMapList_list.remove(groupPosition);//parentMapList和childMapList_list是pos关联的保持一致
        }
        if (parentMapList != null && parentMapList.size() > 0) {
            onCheckHasGoodsListener.onCheckHasGoods(true);//
        } else {
            onCheckHasGoodsListener.onCheckHasGoods(false);//
        }
        notifyDataSetChanged();
        dealPrice();
    }

    /**
     * 1.不要边遍历边删除，容易出现数组越界的情况
     * 2.现将要删除的对象放进相应的列表容器中，待遍历完后，以removeAll的方式进行删除
     */
    public void removeGoods() {
        for (int i = parentMapList.size() - 1; i >= 0; i--) {//倒过来遍历  remove
            StoreBean storeBean = (StoreBean) parentMapList.get(i).get("parentName");
            if (storeBean.isChecked()) {
                parentMapList.remove(i);
                childMapList_list.remove(i);
            } else {
                List<Map<String, Object>> childMapList = childMapList_list.get(i);
                for (int j = childMapList.size() - 1; j >= 0; j--) {//倒过来遍历  remove
                    GoodsBean goodsBean = (GoodsBean) childMapList.get(j).get("childName");
                    if (goodsBean.isChecked()) {
                        childMapList.remove(j);
                    }
                }
            }
        }

        if (parentMapList != null && parentMapList.size() > 0) {
            onCheckHasGoodsListener.onCheckHasGoods(true);//
        } else {
            onCheckHasGoodsListener.onCheckHasGoods(false);//
        }

        notifyDataSetChanged();//
        dealPrice();//重新计算
        setMessageNumber();
    }

//    private void resetViewAfterDelete() {
//        for (int i = 0; i < parentMapList.size(); i++) {
//            StoreBean storeBean = (StoreBean) parentMapList.get(i).get("parentName");
//            storeBean.setIsChecked(false);
//            storeBean.setIsEditing(false);
//            List<Map<String, Object>> childMapList = childMapList_list.get(i);
//
//            for (int j = 0; j < childMapList.size(); j++) {
//                GoodsBean goodsBean = (GoodsBean) childMapList.get(j).get("childName");
//                goodsBean.setIsChecked(false);
//                goodsBean.setIsEditing(false);
//            }
//        }
//    }

//    void showList(String tempStr) {
//        Log.d(TAG, "showList:" + tempStr);
//        for (int i = 0; i < parentMapList.size(); i++) {
//            StoreBean storeBean = (StoreBean) parentMapList.get(i).get("parentName");
//            List<Map<String, Object>> childMapList = childMapList_list.get(i);
//            for (int j = 0; j < childMapList.size(); j++) {
//                GoodsBean goodsBean = (GoodsBean) childMapList.get(j).get("childName");
//            }
//        }
//    }

    public interface OnAllCheckedBoxNeedChangeListener {
        void onCheckedBoxNeedChange(boolean allParentIsChecked);
    }

    public interface OnEditingTvChangeListener {
        void onEditingTvChange(boolean allIsEditing);
    }

    public interface OnGoodsCheckedChangeListener {
        void onGoodsCheckedChange(int totalCount, double totalPrice);
    }

    public interface OnCheckHasGoodsListener {
        void onCheckHasGoods(boolean isHasGoods);
    }

    class GroupViewHolder {
        TextView tv_title_parent;//店名
        TextView id_tv_edit;//点偶编辑
        CheckBox id_cb_select_parent;//店铺全选
    }

    class ChildViewHolder {
        TextView tv_items_child;//商品名
        CheckBox id_cb_select_child;//商品选择
        LinearLayout id_ll_normal;//正常模式LL
        LinearLayout id_ll_edtoring;//编辑模式LL

        TextView tv_items_child_desc;//商品常规下描述
        TextView id_tv_price;//商品价格
        TextView id_tv_discount_price;//商品折扣价
        TextView id_tv_count;//商品数量

        ImageView id_iv_reduce;//减
        ImageView id_iv_add;//加
        TextView id_tv_des_now;//编辑下描述
        TextView id_tv_count_now;//加减后数量
        TextView id_tv_price_now;//加减后价格
        TextView id_tv_goods_star;//收藏
        TextView id_tv_goods_delete;//删除

    }

}
