package com.chopperhl.androidkit.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chopperhl.androidkit.R;
import com.chopperhl.androidkit.util.Util;


/**
 * Description:
 * Author chopperhl
 * Date 9/18/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class ShareDialog extends Dialog {
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.ll_share_qq)
    LinearLayout mLlShareQq;
    @BindView(R.id.ll_share_qzone)
    LinearLayout mLlShareQzone;
    @BindView(R.id.ll_share_wx)
    LinearLayout mLlShareWx;
    @BindView(R.id.ll_share_wx_circle)
    LinearLayout mLlShareWxCircle;

    public static class OnShareActionListener {

    }

    private ShareDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        setContentView(R.layout.dialog_share);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Window window = getWindow();
        if (window == null) return;
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        Util.setOnClickListener(v -> dismiss(), mTvCancel);

    }

    public static class Builder {
        private Context context;
        private boolean isCancelable = true;
        private boolean isCancelOutside = true;
        private String title;
        private String url;
        private String desc;
        private String imageUrl;
        private Bitmap thumbBmp = null;

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

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setDesc(String desc) {
            this.desc = desc;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        @SuppressLint("CheckResult")
        public ShareDialog create() {
            ShareDialog dialog = new ShareDialog(context);
            dialog.setCancelable(isCancelable);
            dialog.setCanceledOnTouchOutside(isCancelOutside);
            Util.setOnClickListener(v -> {

                dialog.dismiss();
            }, dialog.mLlShareQq, dialog.mLlShareQzone, dialog.mLlShareWx, dialog.mLlShareWxCircle);
            return dialog;
        }

    }
}
