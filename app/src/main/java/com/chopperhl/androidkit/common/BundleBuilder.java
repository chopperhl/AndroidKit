package com.chopperhl.androidkit.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

/**
 * Description:
 * Author chopperhl
 * Date 10/27/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class BundleBuilder {
    private Activity mContext;
    private Bundle mBundle;
    private int flag;

    public BundleBuilder(Activity context) {
        mContext = context;
        mBundle = new Bundle();
    }

    public BundleBuilder putString(String key, String val) {
        mBundle.putString(key, val);
        return this;
    }

    public BundleBuilder putBoolean(String key, boolean val) {
        mBundle.putBoolean(key, val);
        return this;
    }

    public BundleBuilder setFlag(int flag) {
        this.flag = flag;
        return this;
    }

    public BundleBuilder putInt(String key, int val) {
        mBundle.putInt(key, val);
        return this;
    }

    public BundleBuilder putSerializable(String key, Serializable val) {
        mBundle.putSerializable(key, val);
        return this;
    }

    public Bundle createBundle() {
        return mBundle;
    }

    public void start(Class<? extends Activity> clz) {
        Intent intent = new Intent(mContext, clz);
        if (flag != 0) intent.setFlags(flag);
        mContext.startActivity(intent.putExtras(mBundle));
    }

    public void start(Class<? extends Activity> clz, int requestCode) {
        Intent intent = new Intent(mContext, clz);
        if (flag != 0) intent.setFlags(flag);
        mContext.startActivityForResult(intent.putExtras(mBundle), requestCode);
    }


}
