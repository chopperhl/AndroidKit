package com.chopperhl.androidkit.common;

import android.text.TextWatcher;

/**
 * Description:{@link TextWatcher } 用于减少代码行数
 * Author chopperhl
 * Date 7/19/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public interface TextChangeListener extends TextWatcher {
    @Override
    default void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    default void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}
