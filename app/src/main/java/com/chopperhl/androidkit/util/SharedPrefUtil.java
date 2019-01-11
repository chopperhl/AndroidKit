package com.chopperhl.androidkit.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.chopperhl.androidkit.base.BaseApplication;

import java.lang.reflect.Type;

/**
 * Description: sharedPreference 本地存储工具
 * Author chopperhl
 * Date 6/1/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class SharedPrefUtil {
    public static SharedPrefUtil INSTANCE = new SharedPrefUtil();

    private SharedPreferences sp;

    private SharedPrefUtil() {
        sp = PreferenceManager.getDefaultSharedPreferences(BaseApplication.getApplication());
    }

    public SharedPreferences.Editor getDefaultSharedEditor() {
        return sp.edit();
    }

    public String getSharedStr(String key, String defValue) {
        return sp.getString(key, defValue);
    }


    public int getSharedInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }


    public float getSharedFloat(String key, float defValue) {
        return sp.getFloat(key, defValue);
    }


    public boolean getSharedBool(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public void putObj(String key, Object obj) {
        if (obj == null) {
            sp.edit().putString(key, "").apply();
        } else {
            String json = Util.gson.toJson(obj);
            sp.edit().putString(key, json).apply();
        }
    }

    public <T> T getObj(String key, Class<T> clz) {
        String json = sp.getString(key, "");
        if (TextUtils.isEmpty(json)) return null;
        return Util.gson.fromJson(json, clz);
    }

    public <T> T getObj(String key, Type type) {
        String json = sp.getString(key, "");
        if (TextUtils.isEmpty(json)) return null;
        return Util.gson.fromJson(json, type);
    }


    public void clearAll() {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }
}
