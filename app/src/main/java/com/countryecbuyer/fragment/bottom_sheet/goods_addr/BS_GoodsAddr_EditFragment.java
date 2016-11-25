package com.countryecbuyer.fragment.bottom_sheet.goods_addr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.fragment.bottom_sheet.BS_AddressFragment;
import com.countryecbuyer.view.sweet_alert_dialog.SweetAlertDialog;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.BottomSheetFragment;

/**
 * Created by Administrator on 2016/7/6.收货地址 编辑
 */
public class BS_GoodsAddr_EditFragment extends BottomSheetFragment implements View.OnClickListener {
    private ImageView cancel;
    private TextView province, city, area;
    private BottomSheetLayout bottomSheetLayout;

    private BS_AddressFragment addressFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_addr_edit, container, false);
        init(view);
        return view;
    }

    private void init(View v) {
        String asd = getArguments().getString("asd");
        cancel = (ImageView) v.findViewById(R.id.bs_goods_addr_edit_cancel);
        province = (TextView) v.findViewById(R.id.receive_addr_edit_province);
        city = (TextView) v.findViewById(R.id.receive_addr_edit_city);
        area = (TextView) v.findViewById(R.id.receive_addr_edit_area);
        bottomSheetLayout = (BottomSheetLayout) v.findViewById(R.id.bs_goods_addr_edit_bottomSheetLayout);
        cancel.setOnClickListener(this);
        province.setOnClickListener(this);
        city.setOnClickListener(this);
        area.setOnClickListener(this);

        addressFragment = new BS_AddressFragment();
        addressFragment.setTheAddrComplete(new BS_AddressFragment.theAddrComplete() {
            @Override
            public void getTheAddr_Province(String provinceStr) {
                province.setText(provinceStr);
            }

            @Override
            public void getTheAddr_City(String cityStr) {
                city.setText(cityStr);
            }

            @Override
            public void getTheAddr_Area(String areaStr) {
                area.setText(areaStr);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bs_goods_addr_edit_cancel:
                new SweetAlertDialog(getContext())
                        .setTitleText("提示")
                        .setContentText("确定放弃此次编辑吗?")
                        .setConfirmText("确定")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                dismiss();
                            }
                        }).show();
                break;
            case R.id.receive_addr_edit_province:
                Bundle bundle = new Bundle();
                bundle.putInt("addr_type", 0);
                addressFragment.setArguments(bundle);
                addressFragment.show(getActivity().getSupportFragmentManager(), R.id.bs_goods_addr_edit_bottomSheetLayout);
                break;
            case R.id.receive_addr_edit_city:
                Bundle bundle2 = new Bundle();
                bundle2.putInt("addr_type", 1);
                addressFragment.setArguments(bundle2);
                addressFragment.show(getActivity().getSupportFragmentManager(), R.id.bs_goods_addr_edit_bottomSheetLayout);
                break;
            case R.id.receive_addr_edit_area:
                Bundle bundle3 = new Bundle();
                bundle3.putInt("addr_type", 2);
                addressFragment.setArguments(bundle3);
                addressFragment.show(getActivity().getSupportFragmentManager(), R.id.bs_goods_addr_edit_bottomSheetLayout);
                break;

        }
    }
}
