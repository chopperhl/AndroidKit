package com.chopperhl.androidkit.repository.network;

/**
 * Description: 基础返回实体
 * Author chopperhl
 * Date 6/6/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class BaseResponse<T> {
    public int code;
    public String message;
    public T data;

    public boolean isOk() {
        return code == ApiState.STATE_OK;
    }
}
