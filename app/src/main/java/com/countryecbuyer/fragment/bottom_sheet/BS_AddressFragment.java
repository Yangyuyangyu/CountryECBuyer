package com.countryecbuyer.fragment.bottom_sheet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.countryecbuyer.bean.area.province_city_areaBean;
import com.flipboard.bottomsheet.commons.BottomSheetFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

/**
 * Created by Administrator on 2016/7/4.选择 省市区
 */
public class BS_AddressFragment extends BottomSheetFragment implements View.OnClickListener {
    private NumberPickerView pickerView;
    private TextView complete;
    private int getIntType;//判断 省0 市1 区2

    private theAddrComplete theaddrComplete;

    private province_city_areaBean bean = new province_city_areaBean();

    private String[] province = new String[34];//所有省
    private String[] city = null;//选中市
    private String[] area = null;//选中区

    private int index_province;//选中省下标
    private int index_city;//选中市下标
    private int index_area;//选中区下标
    private String isSlectProvince = null;//用于判断是否选择省
    private String isSlectCity = null;//用于判断是否选择市


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_province_city_area, container, false);
        init(view);
        return view;
    }

    private void init(View v) {
        getIntType = getArguments().getInt("addr_type");
        complete = (TextView) v.findViewById(R.id.bs_addr_complete);
        pickerView = (NumberPickerView) v.findViewById(R.id.addr_picker);
        pickerView.setOnClickListener(this);
        complete.setOnClickListener(this);
        initJsonData();
        if (getIntType == 0) {
            getContentType(0);
        } else if (getIntType == 1) {
            getContentType(1);
        } else if (getIntType == 2) {
            getContentType(2);
        }


    }

    /**
     * 读取本地assets文件下的json数据 省市区
     */
    private void initJsonData() {
        String jsonData = null;
        try {
            InputStream is = getContext().getResources().getAssets().open("area_rank_pca.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            jsonData = new String(buffer, "utf-8");
            try {
                JSONObject jsonObject = new JSONObject(jsonData);
                Gson gson = new Gson();
                province_city_areaBean list = gson.fromJson(
                        jsonObject.toString(),
                        new TypeToken<province_city_areaBean>() {
                        }.getType());
                int length = list.getCitylist().size();
                for (int i = 0; i < length; i++) {
                    province[i] = list.getCitylist().get(i).getP();
                }
                bean = list;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bs_addr_complete:
                if (getIntType == 0) {
                    theaddrComplete.getTheAddr_Province(getCurrentContent());
                    isSlectProvince = getCurrentContent();
                    index_province = pickerView.getValue();
                } else if (getIntType == 1) {
                    if (isSlectProvince == null) {
                        dismiss();
                        return;
                    }
                    theaddrComplete.getTheAddr_City(getCurrentContent());
                    isSlectCity = getCurrentContent();
                    index_city = pickerView.getValue();
                } else if (getIntType == 2) {
                    if (isSlectCity == null) {
                        dismiss();
                        return;
                    }
                    theaddrComplete.getTheAddr_Area(getCurrentContent());
                    index_area = pickerView.getValue();
                }
                dismiss();
                break;
        }
    }

    /**
     * 加载省或市或区信息
     */
    private void getContentType(int i) {
        String[] content = null;
        switch (i) {
            case 0:
                content = province;
                break;
            case 1:
                if (isSlectProvince == null) { //如果没有选择省 市则为空
                    content = getResources().getStringArray(R.array.test_addr_city);
                } else {
                    int length = bean.getCitylist().get(index_province).getC().size();//所选省的市 size
                    city = new String[length];
                    for (int j = 0; j < length; j++) {
                        city[j] = bean.getCitylist().get(index_province).getC().get(j).getN();
                    }
                    content = city;
                }
                break;
            case 2:
                if (isSlectCity == null) {
                    content = getResources().getStringArray(R.array.test_addr_area);
                } else {
                    int length_city = bean.getCitylist().get(index_province).getC().size();//所选省的市 size
                    int length_area = bean.getCitylist().get(index_province).getC().get(index_city).getA().size();//所选市的区 size
                    area = new String[length_area];

                    if (length_city > 1) {  //市两个以上的区 为getS()，一个为getN(),真坑
                        for (int k = 0; k < length_area; k++) {
                            area[k] = bean.getCitylist().get(index_province).getC().get(index_city).getA().get(k).getS();
                        }
                    } else {
                        for (int k = 0; k < length_area; k++) {
                            area[k] = bean.getCitylist().get(index_province).getC().get(index_city).getA().get(k).getN();
                        }
                    }
                    content = area;
                }
                break;
        }
        pickerView.refreshByNewDisplayedValues(content);
        if (getIntType == 0) {
            if (index_province != -1) pickerView.setValue(index_province);
        } else if (getIntType == 1) {
            if (index_city != -1) pickerView.setValue(index_city);
        } else if (getIntType == 2) {
            if (index_area != -1) pickerView.setValue(index_area);
        }
    }

    /**
     * 获取所选信息
     */
    private String getCurrentContent() {
        String[] content = pickerView.getDisplayedValues();
        if (content != null) {
            return content[pickerView.getValue() - pickerView.getMinValue()];
        } else {
            return null;
        }
    }


    public void setTheAddrComplete(theAddrComplete listener) {
        this.theaddrComplete = listener;
    }

    public interface theAddrComplete {
        void getTheAddr_Province(String provinceStr);

        void getTheAddr_City(String cityStr);

        void getTheAddr_Area(String areaStr);
    }
}
