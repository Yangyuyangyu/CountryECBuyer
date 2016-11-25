package com.countryecbuyer.fragment.bottom_sheet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.countryecbuyer.R;
import com.flipboard.bottomsheet.commons.BottomSheetFragment;

/**
 * Created by Administrator on 2016/7/1.优惠
 */
public class BS_YouHuiFragment extends BottomSheetFragment implements View.OnClickListener {
    private ImageView cancel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_youhui, container, false);
        init(view);
        return view;
    }

    private void init(View v) {
        cancel = (ImageView) v.findViewById(R.id.bs_youhui_cancel);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bs_youhui_cancel:
                dismiss();
                break;

        }
    }
}
