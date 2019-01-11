package com.chopperhl.androidkit.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chopperhl.androidkit.R;
import com.chopperhl.androidkit.entity.Province;
import com.chopperhl.androidkit.util.Lists;
import com.chopperhl.androidkit.util.Util;
import com.google.gson.reflect.TypeToken;
import com.itheima.wheelpicker.WheelPicker;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author chopperhl
 * Date 10/10/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class AreaPickerDialog extends Dialog {
    public interface OnConfirmListener {
        void onConfirm(AreaPickerDialog dialog, Province province, Province.City city, Province.City.Area area);
    }

    @BindView(R.id.wheelPicker_1)
    WheelPicker mWheelPicker1;
    @BindView(R.id.wheelPicker_2)
    WheelPicker mWheelPicker2;
    @BindView(R.id.wheelPicker_3)
    WheelPicker mWheelPicker3;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_confirm)
    TextView mTvConfirm;
    private List<Province> mData = new ArrayList<>();

    private AreaPickerDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        setContentView(R.layout.dialog_area_picker);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        Window window = getWindow();
        //设置dialog的宽度为当前手机屏幕的宽度
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.BOTTOM;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(lp);
        }
        if (Lists.isEmpty(mData)) return;
        mWheelPicker1.setData(Lists.replaceNull(mData));
        mWheelPicker2.setData(Lists.replaceNull(mData.get(0).sub));
        mWheelPicker3.setData(Lists.replaceNull(mData.get(0).sub.get(0).sub));
        mWheelPicker1.setOnItemSelectedListener((wheelPicker, o, i) -> {
            mWheelPicker2.setData(Lists.replaceNull(mData.get(i).sub));
            mWheelPicker2.setSelectedItemPosition(0);
            mWheelPicker3.setData(Lists.replaceNull(mData.get(i).sub.get(0).sub));
            mWheelPicker3.setSelectedItemPosition(0);
        });
        mWheelPicker2.setOnItemSelectedListener((wheelPicker, o, i) -> {
            List<Province.City> cities = wheelPicker.getData();
            mWheelPicker3.setData(Lists.replaceNull(cities.get(i).sub));
            mWheelPicker3.setSelectedItemPosition(0);
        });
    }

    private void initData() {
        InputStream is = null;
        try {
            is = getContext().getAssets().open("city.json");
            mData = Util.gson.fromJson(new InputStreamReader(is), new TypeToken<List<Province>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Builder {
        private Context context;
        private boolean isCancelable = true;
        private boolean isCancelOutside = true;
        private OnConfirmListener mOnConfirmListener;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置是否可以按返回键取消
         *
         * @param isCancelable
         * @return
         */

        public Builder setCancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }

        /**
         * 设置是否可以取消
         *
         * @param isCancelOutside
         * @return
         */
        public Builder setCancelOutside(boolean isCancelOutside) {
            this.isCancelOutside = isCancelOutside;
            return this;
        }

        public Builder setOnConfirmListener(OnConfirmListener onConfirmListener) {
            mOnConfirmListener = onConfirmListener;
            return this;
        }

        @SuppressLint("InflateParams")
        public AreaPickerDialog create() {
            AreaPickerDialog dialog = new AreaPickerDialog(context);
            dialog.setCancelable(isCancelable);
            dialog.setCanceledOnTouchOutside(isCancelOutside);
            Util.setOnClickListener(v -> {
                if (v == dialog.mTvCancel) {
                    dialog.dismiss();
                }
                if (v == dialog.mTvConfirm) {
                    if (mOnConfirmListener == null) return;
                    int pos1 = dialog.mWheelPicker1.getCurrentItemPosition();
                    int pos2 = dialog.mWheelPicker2.getCurrentItemPosition();
                    int pos3 = dialog.mWheelPicker3.getCurrentItemPosition();
                    List<Province> provinces = dialog.mData;
                    //个别空的情况
                    Province.City.Area area = provinces.get(pos1).sub.get(pos2).sub == null
                            ? Province.City.Area.empty() : provinces.get(pos1).sub.get(pos2).sub.get(pos3);
                    mOnConfirmListener.onConfirm(
                            dialog,
                            provinces.get(pos1),
                            provinces.get(pos1).sub.get(pos2),
                            area
                    );
                    dialog.dismiss();
                }
            }, dialog.mTvCancel, dialog.mTvConfirm);
            return dialog;

        }

    }
}
