package com.chopperhl.androidkit.repository.network.api;


import io.reactivex.Observable;
import retrofit2.http.*;
import java.util.Map;

/**
 * Description: 主要接口
 * Author chopperhl
 * Date 6/1/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */

public interface MainService {

    /**
     * 检查更新
     *
     * @param updateUrl
     * @return
     */
    @GET
    Observable<Map<String, String>> checkUpdate(@Url String updateUrl);
}
