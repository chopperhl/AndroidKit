package com.chopperhl.androidkit.common;

import android.content.Intent;

/**
 * Description: 生命周期注册接口
 * Author chopperhl
 * Date 7/27/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public interface LifecycleListener {
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
