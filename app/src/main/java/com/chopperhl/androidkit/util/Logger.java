package com.chopperhl.androidkit.util;

import android.text.TextUtils;
import android.util.Log;
import com.chopperhl.androidkit.BuildConfig;


/**
 * Description: 日志打印
 * Author chopperhl
 * Date 6/13/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class Logger {
    private static final String TAG = "TAG";
    private static int MAX_LENGTH = 2000;

    public static void d(String tag, String str) {
        if (TextUtils.isEmpty(str)) return;
        if (!BuildConfig.DEBUG) return;
        int len = str.length();
        int start = 0;
        int end = MAX_LENGTH;
        for (int i = 0; i < 100; i++) {
            if (len > end) {
                Log.d(tag, str.substring(start, end));
                start = end;
                end = end + MAX_LENGTH;
            } else {
                Log.d(tag, str.substring(start, len));
                break;
            }
        }
    }

    public static void d(String str) {
        d(TAG, str);
    }

    public static void e(String tag, String str) {
        if (TextUtils.isEmpty(str)) return;
        if (!BuildConfig.DEBUG) return;
        int len = str.length();
        int start = 0;
        int end = MAX_LENGTH;
        for (int i = 0; i < 100; i++) {
            if (len > end) {
                Log.e(tag, str.substring(start, end));
                start = end;
                end = end + MAX_LENGTH;
            } else {
                Log.e(tag, str.substring(start, len));
                break;
            }
        }
    }

    public static void e(String str) {
        e(TAG, str);
    }
}
