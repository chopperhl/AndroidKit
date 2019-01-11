package com.chopperhl.androidkit.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import com.chopperhl.androidkit.R;

/**
 * Description: 加载弹窗
 * Author chopperhl
 * Date 6/1/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class LoadingDialog extends Dialog {


    private LoadingDialog(@NonNull Context context) {
        super(context, R.style.LoadingDialog);
        initView();
    }

    private void initView() {
        setContentView(R.layout.progress_bar_layout);
    }

    public static class Builder {

        private Context context;
        private boolean isCancelable = false;
        private boolean isCancelOutside = false;


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

        @SuppressLint("InflateParams")
        public LoadingDialog create() {
            LoadingDialog loadingDialog = new LoadingDialog(context);
            loadingDialog.setCancelable(isCancelable);
            loadingDialog.setCanceledOnTouchOutside(isCancelOutside);
            return loadingDialog;

        }

    }
}
