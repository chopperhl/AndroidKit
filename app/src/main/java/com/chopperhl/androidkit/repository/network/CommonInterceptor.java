package com.chopperhl.androidkit.repository.network;


import com.chopperhl.androidkit.entity.UserInfo;
import com.chopperhl.androidkit.util.DeviceHelper;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Description: 通用的网络请求拦截
 * Author chopperhl
 * Date 6/5/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class CommonInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request oldRequest = chain.request();
        // 新的请求
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(oldRequest.url())
                .headers(oldRequest.headers())
                .addHeader("token", UserInfo.getInstance().getToken())
                .addHeader("deviceId", DeviceHelper.getDeviceId())
                .build();
        return chain.proceed(newRequest);
    }
}