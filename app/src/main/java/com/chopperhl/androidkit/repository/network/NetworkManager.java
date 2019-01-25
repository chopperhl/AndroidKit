package com.chopperhl.androidkit.repository.network;


import com.chopperhl.androidkit.BuildConfig;
import com.chopperhl.androidkit.Config;
import com.chopperhl.androidkit.base.BaseApplication;
import com.chopperhl.androidkit.repository.network.api.MainService;
import com.chopperhl.androidkit.util.Logger;
import com.chopperhl.androidkit.util.Util;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * 网络请求
 * Created by chopperhl on 3/10/18.
 */

public class NetworkManager {
    public static final NetworkManager INSTANCE = new NetworkManager();
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;

    private static MainService mainService;

    private NetworkManager() {
    }


    public OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor =
                new HttpLoggingInterceptor(message -> Logger.d("Retrofit", message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .addInterceptor(new CommonInterceptor())
                    .addInterceptor(new CacheControlInterceptor())
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(8, TimeUnit.SECONDS)
                    .readTimeout(8, TimeUnit.SECONDS)
                    .cache(getCache());
            if (!BuildConfig.DEBUG) builder.proxy(Proxy.NO_PROXY);
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    private Cache getCache() {
        File cacheFile = new File(BaseApplication.getApplication().getCacheDir(), "foboth");
        return new Cache(cacheFile, 1024 * 1024 * 100);
    }

    public void clearCache() {
        try {
            getCache().delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient client = getOkHttpClient();
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(Util.gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public MainService getMainService() {
        if (mainService == null) mainService = getRetrofit().create(MainService.class);
        return mainService;
    }

}
