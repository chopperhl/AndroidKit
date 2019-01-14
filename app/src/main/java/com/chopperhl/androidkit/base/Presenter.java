package com.chopperhl.androidkit.base;

import java.lang.annotation.Retention;


import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Description:
 * Author chopperhl
 * Date 1/14/19
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */

@Retention(RUNTIME)
public @interface Presenter {
    Class<? extends BasePresenter> value();
}
