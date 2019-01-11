package com.chopperhl.androidkit.base;


/**
 * Description: Presenter 基础 Presenter
 * Author chopperhl
 * Date 6/1/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */

public abstract class BasePresenter<V extends BaseView> {
    protected V mView;

    protected void detachView() {
        mView = null;
    }
}
