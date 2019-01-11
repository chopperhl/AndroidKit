package com.chopperhl.androidkit.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chopperhl.androidkit.R;
import com.chopperhl.androidkit.adapter.SheetAdapter;


import java.util.List;

/**
 * Description:
 * Author chopperhl
 * Date 10/9/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class BottomSheetDialog extends Dialog {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private SheetAdapter mAdapter;


    private BottomSheetDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        setContentView(R.layout.dialog_bottom_sheet);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(mAdapter = new SheetAdapter());
        Window window = getWindow();
        if (window == null) return;
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }


    public static class Builder {
        private Context context;
        private boolean isCancelable = true;
        private boolean isCancelOutside = true;
        private SheetAdapter.OnItemClickListener mOnItemClickListener;
        private List mSheetData;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setSheetData(List sheetData) {
            mSheetData = sheetData;
            return this;
        }

        public Builder setOnItemClickListener(SheetAdapter.OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
            return this;
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

        public BottomSheetDialog create() {
            BottomSheetDialog dialog = new BottomSheetDialog(context);
            dialog.mAdapter.setOnItemClickListener((index, obj) -> {
                dialog.dismiss();
                if (mOnItemClickListener == null) return;
                mOnItemClickListener.onClick(index, obj);
            });
            dialog.mAdapter.setSheetData(mSheetData);
            dialog.setCancelable(isCancelable);
            dialog.setCanceledOnTouchOutside(isCancelOutside);
            return dialog;
        }

    }
}
