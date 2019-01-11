package com.chopperhl.androidkit.repository.network;


/**
 * Description: 接口错误类型
 * Author chopperhl
 * Date 6/1/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class ApiException extends RuntimeException {
    private int state = ApiState.STATE_UNKNOWN_ERROR;

    public ApiException(String msg, int state) {
        super(msg);
        this.state = state;
    }

    public ApiException(String msg) {
        super(msg);
    }

    public ApiException() {
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
