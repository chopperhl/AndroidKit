package com.chopperhl.androidkit.base;


import android.support.annotation.StringRes;

/**
 * Description: View基础类
 * Author chopperhl
 * Date 6/1/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */

public interface BaseView {
    int HUD_TYPE_NONE = 0;//无加载动画
    int HUD_TYPE_PROGRESS = 1;//显示dialog
    int HUD_TYPE_REFRESH = 2;//显示刷新动画
    String KEY_PARAMS_1 = "key_params_1";
    String KEY_PARAMS_2 = "key_params_2";
    String KEY_PARAMS_3 = "kay_params_3";
    String KEY_PARAMS_4 = "kay_params_4";
    int REQUEST_CODE_1 = 10001;
    int REQUEST_CODE_2 = 10002;
    int REQUEST_CODE_3 = 10003;
    int REQUEST_CODE_4 = 10004;


    /**
     * 用于{@link BaseListActivity}和{@link BaseListActivity}控制刷新动画隐藏
     *
     * 见 {@link BaseListActivity#dismissRefreshView()}和{@link BaseListFragment#dismissRefreshView()}
     */
    void dismissRefreshView();

    /**
     * 用于{@link BaseListActivity}和{@link BaseListFragment}控制刷新动画显示
     *
     * 见 {@link BaseListActivity#showRefreshView()}和{@link BaseListFragment#showRefreshView()}
     */
    void showRefreshView();

    /**
     * 显示加载动画
     */
    void showProgressView();

    /**
     * 隐藏加载动画
     */
    void dismissProgressView();

    /**
     * 通用错误处理
     *
     * @param t
     */
    void dealError(Throwable t);

    /**
     * 显示toast
     *
     * @param msg
     */
    void showToast(String msg);

    /**
     * 显示toast
     *
     * @param msg
     */
    void showToast(@StringRes int msg);

    /**
     * 显示toast
     *
     * @param msg
     */
    void showToastLong(String msg);

    /**
     * 显示toast
     *
     * @param msg
     */
    void showToastLong(@StringRes int msg);


    /**
     * 用于{@link BaseListActivity}和{@link BaseListFragment}清空列表数据，避免刷新时重复添加
     *
     * 见 {@link BaseListActivity#clearList()}和{@link BaseListFragment#clearList()}
     */
    void clearList();
}
