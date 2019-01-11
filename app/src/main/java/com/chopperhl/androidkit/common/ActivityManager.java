package com.chopperhl.androidkit.common;


import com.chopperhl.androidkit.base.BaseActivity;

import java.util.Stack;

/**
 * Description: Act出入栈管理工具
 * Author chopperhl
 * Date 6/1/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class ActivityManager {
    public static ActivityManager INSTANCE = new ActivityManager();

    private static Stack<BaseActivity> mActivityStack = new Stack<>();

    private ActivityManager() {
    }

    /**
     * 入栈
     *
     * @param activity
     */
    public void add(BaseActivity activity) {
        mActivityStack.add(activity);
    }

    /**
     * 出栈
     *
     * @param activity
     */

    public void remove(BaseActivity activity) {
        mActivityStack.remove(activity);
    }

    /**
     * 结束所有页面
     */
    public void finishAll() {
        while (!mActivityStack.empty()) {
            BaseActivity activity = mActivityStack.remove(0);
            activity.finish();
        }
    }

    /**
     * 获取Stack中的Act数量
     *
     * @return
     */
    public int getActivityCount() {
        return mActivityStack.size();
    }

    /**
     * 获取顶层的Act 即当前页面
     *
     * 用于不能使用 ApplicationContext ,必须使用 ActivityContext 的情况
     *
     * 如创建 {@link android.app.Dialog }
     *
     * @return
     */
    public BaseActivity getTopActivity() {
        if (mActivityStack == null || mActivityStack.empty()) return null;
        return mActivityStack.get(mActivityStack.size() - 1);
    }
}
