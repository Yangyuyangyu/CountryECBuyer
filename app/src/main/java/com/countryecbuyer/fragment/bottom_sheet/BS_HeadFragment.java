package com.countryecbuyer.fragment.bottom_sheet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.countryecbuyer.R;
import com.flipboard.bottomsheet.commons.BottomSheetFragment;

/**
 * Created by Administrator on 2016/6/29.头像底部弹窗选择
 */
public class BS_HeadFragment extends BottomSheetFragment implements View.OnClickListener {
    private TextView photo_picker, take_pictures, cancel;
    private sheetViewClick sheetviewClick;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_head, container, false);
        photo_picker = (TextView) view.findViewById(R.id.bs_head_photo_picker);
        take_pictures = (TextView) view.findViewById(R.id.bs_head_take_pictures);
        cancel = (TextView) view.findViewById(R.id.bs_head_cancel);
        photo_picker.setOnClickListener(this);
        take_pictures.setOnClickListener(this);
        cancel.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bs_head_photo_picker:
                sheetviewClick.sheetView_photoPick();
                dismiss();
                break;
            case R.id.bs_head_take_pictures:
                sheetviewClick.sheetView_take_capture();
                dismiss();
                break;
            case R.id.bs_head_cancel:
                dismiss();
                break;
        }
    }

    public void setSheetviewClick(sheetViewClick listener) {
        this.sheetviewClick = listener;
    }

    public interface sheetViewClick {
        void sheetView_photoPick();//相册选取

        void sheetView_take_capture();//拍照
    }
}
