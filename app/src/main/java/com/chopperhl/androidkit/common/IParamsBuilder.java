package com.chopperhl.androidkit.common;

/**
 * Description: 参数构造接口，方便实现链式调用
 * ************ 实际上没什么卵用
 * Author chopperhl
 * Date 6/5/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public interface IParamsBuilder {

    /**
     * 清空参数
     *
     * @return
     */
    IParamsBuilder clearParams();

    /**
     * 添加参数
     *
     * @param key
     * @param val
     * @return
     */
    IParamsBuilder appendParams(String key, String val);


    /**
     * 请求接口
     *
     * @param page
     */
    void requestApi(int page);
}
