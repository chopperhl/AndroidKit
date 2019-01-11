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
import com.chopperhl.androidkit.util.Lists;
import com.chopperhl.androidkit.util.Util;
import com.itheima.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 单个列表的滚轮选择器
 * Author chopperhl
 * Date 6/22/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class WheelPickerDialog extends Dialog {

    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_confirm)
    TextView mTvConfirm;
    @BindView(R.id.wheelPicker)
    WheelPicker mWheelPicker;
    private WheelPickerCallback mWheelPickerCallback;
    private List mList = new ArrayList<>();

    public interface WheelPickerCallback<T> {
        void onSelect(WheelPickerDialog dialog, T obj);
    }

    private void setTitle(String title) {
        mTvTitle.setText(title);
    }

    private void setData(List list) {
        mList.clear();
        if (Lists.isNotEmpty(list)) mList.addAll(list);
        mWheelPicker.setData(list);
    }

    private WheelPickerDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        setContentView(R.layout.wheel_picker_dialog);
        ButterKnife.bind(this);
        initView();
    }

    private void setWheelPickerCallback(WheelPickerCallback wheelPickerCallback) {
        mWheelPickerCallback = wheelPickerCallback;
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
        Util.setOnClickListener(v -> {
            if (v == mTvConfirm) {
                if (mWheelPickerCallback == null) return;
                int selectedIndex = mWheelPicker.getCurrentItemPosition();
                mWheelPickerCallback.onSelect(this, mList.get(selectedIndex));
                dismiss();
            }

            if (v == mTvCancel) {
                dismiss();
            }
        }, mTvConfirm, mTvCancel);
    }

    public static class Builder<T> {
        private Context context;
        private boolean isCancelable = true;
        private boolean isCancelOutside = true;
        private String mTitle;
        private List<T> mEntities = new ArrayList<>();

        private WheelPickerCallback mCallback;


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

        public Builder setData(List<T> list) {
            if (Lists.isEmpty(list)) return this;
            mEntities.clear();
            mEntities.addAll(list);
            return this;
        }

        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder setCallback(WheelPickerCallback<T> callback) {
            mCallback = callback;
            return this;
        }

        @SuppressLint("InflateParams")
        public WheelPickerDialog create() {
            WheelPickerDialog dialog = new WheelPickerDialog(context);
            dialog.setWheelPickerCallback(mCallback);
            dialog.setTitle(mTitle);
            dialog.setData(mEntities);
            dialog.setCancelable(isCancelable);
            dialog.setCanceledOnTouchOutside(isCancelOutside);
            return dialog;

        }

    }
}
