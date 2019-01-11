package com.chopperhl.androidkit.repository.network;

import android.text.TextUtils;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Description: 缓存控制
 * Author chopperhl
 * Date 8/30/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class CacheControlInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response.Builder builder = chain.proceed(request)
                .newBuilder()
                .removeHeader("Pragma");
        String cacheString = request.cacheControl().toString();
        if (!TextUtils.isEmpty(cacheString)) {
            builder.header("Cache-Control", cacheString);
        }
        return builder.build();
    }

}
