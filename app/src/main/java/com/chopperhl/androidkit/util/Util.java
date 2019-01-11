package com.chopperhl.androidkit.util;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;
import com.chopperhl.androidkit.R;
import com.chopperhl.androidkit.base.BaseApplication;
import com.chopperhl.androidkit.repository.network.ApiException;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.json.JSONException;
import retrofit2.HttpException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Locale;

/**
 * Description: 通用工具类
 * Author chopperhl
 * Date 6/1/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */

public class Util {
    public static Gson gson = new Gson();
    public static final Handler handler = new Handler(Looper.getMainLooper());
    public static final int NETWORK_NONE = 0;
    public static final int NETWORK_GPRS = 1;
    public static final int NETWORK_WIFI = 2;


    private Util() {
    }


    /**
     * 线程调度   主线程
     *
     * @param action
     */
    public static void doOnMainThread(Runnable action) {
        handler.post(action);
    }


    public static String getString(@StringRes int strRes) {
        return BaseApplication.getApplication().getResources().getString(strRes);
    }

    public static int getColor(@ColorRes int colorRes) {
        return BaseApplication.getApplication().getResources().getColor(colorRes);
    }

    public static void showToast(String msg) {
        Toast.makeText(BaseApplication.getApplication(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(String msg) {
        Toast.makeText(BaseApplication.getApplication(), msg, Toast.LENGTH_LONG).show();
    }

    @SuppressLint("ShowToast")
    public static void showToast(@StringRes int msgRes) {
        showToast(getString(msgRes));
    }

    @SuppressLint("ShowToast")
    public static void showToastLong(@StringRes int msgRes) {
        showToastLong(getString(msgRes));
    }

    public static boolean validate(boolean isOk, @StringRes int toastStr) {
        if (!isOk) showToast(toastStr);
        return isOk;
    }

    public static void toastError(Throwable t) {
        String msg = getString(R.string.error_not_known);
        if (t instanceof ApiException) {
            msg = t.getMessage();
        } else if (t instanceof UnknownHostException || t instanceof ConnectException) {
            msg = getString(R.string.network_unavailable);
        } else if (t instanceof SocketTimeoutException) {
            msg = getString(R.string.request_time_out);
        } else if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            if (httpException.code() == 500) {
                msg = getString(R.string.server_error);
            } else if (httpException.code() == 404) {
                msg = getString(R.string.host_not_exist);
            } else if (httpException.code() == 403) {
                msg = getString(R.string.request_forbidden);
            } else if (httpException.code() == 307) {
                msg = getString(R.string.request_redirected);
            } else {
                msg = httpException.message();
            }
        } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException) {
            msg = getString(R.string.data_parse_error);
            t.printStackTrace();
        } else {
            t.printStackTrace();
        }
        showToast(msg);
    }

    /**
     * 空替换
     *
     * @param src
     * @param replace
     * @return
     */
    public static String replaceEmpty(String src, String replace) {
        if (replace == null) return src;
        return TextUtils.isEmpty(src) ? replace : src;
    }

    /**
     * 空替换
     * 设置默认值
     *
     * @param src
     * @return
     */

    public static String replaceEmpty(String src) {
        return replaceEmpty(src, Util.getString(R.string.not_yet));
    }


    /**
     * 检查网络状态
     * <p>
     * 只处理  无网络  wifi gprs 的情况
     *
     * @return
     */
    public static int checkNetwork() {
        ConnectivityManager connMan = (ConnectivityManager) BaseApplication.getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMan == null) {
            return NETWORK_NONE;
        }
        NetworkInfo info = connMan.getActiveNetworkInfo();
        if (info == null) {
            return NETWORK_NONE;
        }
        if (!info.isAvailable()) return NETWORK_NONE;
        return info.getType() == ConnectivityManager.TYPE_WIFI ? NETWORK_WIFI : NETWORK_GPRS;
    }

    /**
     * px转dp
     *
     * @param pxValue
     * @return
     */
    public static float px2dp(int pxValue) {
        final float scale = BaseApplication.getApplication().getResources().getDisplayMetrics().density;
        return pxValue / scale;
    }

    /**
     * dp转px
     *
     * @param dipValue
     * @return
     */
    public static int dp2px(float dipValue) {
        final float scale = BaseApplication.getApplication().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 处理ButterKnife 重复点击的问题
     *
     * @param view
     */
    public static void preventQuickClick(View view) {
        view.setEnabled(false);
        view.postDelayed(() -> view.setEnabled(true), 300);
    }


    /**
     * app内修改语言
     *
     * @param locale
     */

    public static void setLanguage(Locale locale) {
        Resources resources = BaseApplication.getApplication().getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        resources.updateConfiguration(config, dm);
    }


    public static void setOnClickListener(View.OnClickListener listener, View... views) {
        View.OnClickListener onClickListener = v -> {
            Util.preventQuickClick(v);
            listener.onClick(v);
        };
        for (View view : views) {
            view.setOnClickListener(listener == null ? null : onClickListener);
        }
    }


    public static boolean equals(String s1, String s2) {
        if (s1 == null && s2 == null) return true;
        if (s1 == null || s2 == null) return false;
        return s1.equals(s2);

    }


    @Nullable
    public static <T> T convertObj(Object obj, Class<T> clz) {
        try {
            String json = gson.toJson(obj);
            if (TextUtils.isEmpty(json)) return null;
            return gson.fromJson(json, clz);
        } catch (Exception e) {
            return null;
        }
    }
}
