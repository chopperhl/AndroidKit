package com.chopperhl.androidkit.common;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Description: 利用 java8特性 简化代码
 * Author chopperhl
 * Date 11/6/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public interface OnSingleTapUpGesture extends GestureDetector.OnGestureListener {
    @Override
    default boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    default void onShowPress(MotionEvent e) {

    }


    @Override
    default boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    default void onLongPress(MotionEvent e) {

    }

    @Override
    default boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
