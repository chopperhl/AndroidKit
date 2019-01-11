package com.chopperhl.androidkit.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.widget.Button;
import android.widget.TextView;
import com.chopperhl.androidkit.R;


public class AlertDialog extends Dialog {
    private TextView tvTitle;
    private TextView tvContent;
    private Button butCancel, butYes;

    private AlertDialog(Context context) {
        super(context, R.style.Dialog);
        setContentView(R.layout.alert_dialog);
        initView();
    }

    private void initView() {
        tvTitle = findViewById(R.id.tvTitle);
        tvContent = findViewById(R.id.tvContent);
        butYes = findViewById(R.id.butYes);
        butCancel = findViewById(R.id.butCancel);
    }

    public void setPositiveButton(@StringRes int textId, DialogInterface.OnClickListener listener) {
        setPositiveButton(getContext().getResources().getString(textId), listener);
    }

    public void setPositiveButton(CharSequence text, DialogInterface.OnClickListener listener) {
        butYes.setText(text);
        butYes.setOnClickListener(v -> {
            listener.onClick(AlertDialog.this, v.getId());
            dismiss();
        });
    }


    public void setNegativeButton(@StringRes int textId, DialogInterface.OnClickListener listener) {
        setNegativeButton(getContext().getResources().getString(textId), listener);
    }

    public void setNegativeButton(CharSequence text, DialogInterface.OnClickListener listener) {
        butCancel.setText(text);
        if (listener == null) {
            butCancel.setOnClickListener(v -> dismiss());
        } else {
            butCancel.setOnClickListener(v -> {
                listener.onClick(AlertDialog.this, v.getId());
                dismiss();
            });
        }
    }


    @Override
    public void setTitle(CharSequence title) {
        tvTitle.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        tvTitle.setText(titleId);
    }

    /**
     * 设置内容文本。
     *
     * @param contentId
     */
    public void setContent(int contentId) {
        tvContent.setText(contentId);
    }

    public void setContent(String contentId) {
        tvContent.setText(contentId);
    }


    public static class Builder {

        private Context context;
        private boolean isCancelable = false;
        private boolean isCancelOutside = false;
        private String content, title;
        private String negativeText, positiveText;
        private DialogInterface.OnClickListener onConfirmListener;
        private DialogInterface.OnClickListener onCancelListener;

        public Builder(Context context) {
            this.context = context;
            negativeText = context.getResources().getString(R.string.cancel);
            positiveText = context.getResources().getString(R.string.confirm);
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


        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setContent(int content) {
            return setContent(context.getResources().getString(content));
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitle(int title) {
            return setTitle(context.getResources().getString(title));
        }


        public Builder setNegativeText(String negativeText) {
            this.negativeText = negativeText;
            return this;
        }

        public Builder setNegativeText(int negativeText) {
            return setNegativeText(context.getResources().getString(negativeText));
        }

        public Builder setPositiveText(String positiveText) {
            this.positiveText = positiveText;
            return this;
        }

        public Builder setPositiveText(int positiveText) {
            return setNegativeText(context.getResources().getString(positiveText));
        }

        public Builder setOnConfirmListener(DialogInterface.OnClickListener onConfirmListener) {
            this.onConfirmListener = onConfirmListener;
            return this;
        }

        public Builder setOnCancelListener(OnClickListener onCancelListener) {
            this.onCancelListener = onCancelListener;
            return this;
        }

        @SuppressLint("InflateParams")
        public AlertDialog create() {
            AlertDialog alertDialog = new AlertDialog(context);
            alertDialog.setCancelable(isCancelable);
            alertDialog.setCanceledOnTouchOutside(isCancelOutside);
            alertDialog.setPositiveButton(positiveText, onConfirmListener);
            alertDialog.setNegativeButton(negativeText, onCancelListener);
            alertDialog.setContent(content);
            alertDialog.setTitle(title);
            return alertDialog;
        }

    }
}
