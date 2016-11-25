package com.countryecbuyer.activity.receive_address;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.activity.base.BaseActivity;
import com.countryecbuyer.fragment.bottom_sheet.BS_AddressFragment;
import com.flipboard.bottomsheet.BottomSheetLayout;

/**
 * 收货地址详情
 */
public class ReceiveAddrDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView top_title;
    private TextView delete;
    private View delete_line;//分割线
    private Button default_addr;
    private BottomSheetLayout bottomSheetLayout;

    private int getIntDex = -1;//新增地址
    private BS_AddressFragment addressFragment;
    private TextView province, city, area;//省市区

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_receive_addr_detail;
    }

    @Override
    protected void init() {
        addressFragment = new BS_AddressFragment();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) getIntDex = bundle.getInt("the_newAdd_addr");

    }

    @Override
    protected void initData() {
        if (getIntDex == 0) {
            delete.setVisibility(View.GONE);
            default_addr.setVisibility(View.GONE);
            delete_line.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initView() {
        back = customFindViewById(R.id.my_top_actionbar_back);
        delete = customFindViewById(R.id.receive_addr_delete);
        delete_line = customFindViewById(R.id.receive_addr_delete_line);
        default_addr = customFindViewById(R.id.receive_addr_default);
        top_title = customFindViewById(R.id.my_top_actionbar_title);
        province = customFindViewById(R.id.receive_addr_province);
        city = customFindViewById(R.id.receive_addr_city);
        area = customFindViewById(R.id.receive_addr_area);
        bottomSheetLayout = customFindViewById(R.id.receive_addr_bottomSheetLayout);
        top_title.setText("管理收货地址");
        back.setOnClickListener(this);
        delete.setOnClickListener(this);
        default_addr.setOnClickListener(this);
        province.setOnClickListener(this);
        city.setOnClickListener(this);
        area.setOnClickListener(this);
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
            case R.id.my_top_actionbar_back:
                finish();
                break;
            case R.id.receive_addr_default:
                //设置默认

                break;
            case R.id.receive_addr_delete:
                break;
            case R.id.receive_addr_province:
                Bundle bundle = new Bundle();
                bundle.putInt("addr_type", 0);
                addressFragment.setArguments(bundle);
                addressFragment.show(getSupportFragmentManager(), R.id.receive_addr_bottomSheetLayout);
                break;
            case R.id.receive_addr_city:
                Bundle bundle2 = new Bundle();
                bundle2.putInt("addr_type", 1);
                addressFragment.setArguments(bundle2);
                addressFragment.show(getSupportFragmentManager(), R.id.receive_addr_bottomSheetLayout);
                break;
            case R.id.receive_addr_area:
                Bundle bundle3 = new Bundle();
                bundle3.putInt("addr_type", 2);
                addressFragment.setArguments(bundle3);
                addressFragment.show(getSupportFragmentManager(), R.id.receive_addr_bottomSheetLayout);
                break;


        }
    }
}
