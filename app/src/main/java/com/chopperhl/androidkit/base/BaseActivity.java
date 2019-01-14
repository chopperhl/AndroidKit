package com.chopperhl.androidkit.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chopperhl.androidkit.R;
import com.chopperhl.androidkit.common.ActivityManager;
import com.chopperhl.androidkit.common.LifecycleListener;
import com.chopperhl.androidkit.util.KeyboardHelper;
import com.chopperhl.androidkit.util.RxUtil;
import com.chopperhl.androidkit.util.StatusBarHelper;
import com.chopperhl.androidkit.util.Util;
import com.chopperhl.androidkit.widget.LoadingDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.functions.Action;

import java.lang.reflect.Constructor;


/**
 * Description: activity 基础类
 * Author chopperhl
 * Date 6/1/18
 * <p>
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity implements BaseView {

    private LifecycleListener mLifecycleListener;
    protected Toolbar toolbar;

    private Unbinder unbinder;
    protected P mPresenter;
    private LoadingDialog mLoadingDialog;
    private TextView mTitleTextView;
    private RxPermissions mRxPermissions;

    private void initPresenter() {
        try {
            Presenter p = getClass().getAnnotation(Presenter.class);
            if (p == null) return;
            Constructor constructor = p.value().getConstructor();
            mPresenter = (P) constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mPresenter != null) mPresenter.mView = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        ActivityManager.INSTANCE.add(this);
        initPresenter();
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) bundle = new Bundle();
        initParams(bundle);
        if (bindLayout() > 0) setContentView(bindLayout());
        StatusBarHelper.setStatusBarLightMode(getActivity());
        unbinder = ButterKnife.bind(this);
        if (isFullScreen()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (isSteepStatusBar()) {
            steepStatusBar();
        }
        if (!isAllowScreenRotate()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        toolbar = findViewById(R.id.toolbar);
        mTitleTextView = findViewById(R.id.titleText);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        initLoadingDialog();
        initView();
    }

    protected BaseActivity getActivity() {
        return this;
    }


    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitleText(String title) {
        mTitleTextView.setText(title);
    }

    /**
     * 设置标题
     *
     * @param titleRes
     */
    public void setTitleText(@StringRes int titleRes) {
        setTitleText(getResources().getString(titleRes));
    }

    public int getColorRes(@ColorRes int colorRes) {
        return getResources().getColor(colorRes);
    }

    /**
     * 初始化加载弹窗
     */
    protected void initLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog.Builder(getActivity()).create();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        doBusiness();
    }

    /**
     * 设置沉浸式状态栏
     */
    private void steepStatusBar() {
        StatusBarHelper.translucent(getActivity());
    }

    public abstract void initParams(Bundle params);

    public abstract int bindLayout();

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, me)) KeyboardHelper.hideKeyboard(v);
        }
        return super.dispatchTouchEvent(me);
    }

    /**
     * 判断点击区域是不edit
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v == null) return false;
        if (v instanceof EditText) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        return false;
    }

    /**
     * 替换{@link Fragment}
     *
     * @param containerId
     * @param fragment
     */
    public void replaceFragment(int containerId, Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }

    /**
     * 添加{@link Fragment}
     *
     * @param containerId
     * @param fragment
     */
    public void addFragment(int containerId, Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerId, fragment)
                .commit();
    }

    /**
     * 隐藏{@link Fragment}
     *
     * @param fragment
     */
    public void hideFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .hide(fragment)
                .commit();
    }

    /**
     * 显示{@link Fragment}
     *
     * @param fragment
     */
    public void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .show(fragment)
                .commit();
    }


    /**
     * 控制是否全屏
     *
     * @return
     */
    protected boolean isFullScreen() {
        return false;
    }

    /**
     * 控制沉浸式状态栏
     *
     * @return
     */
    protected boolean isSteepStatusBar() {
        return false;
    }

    /**
     * 控制是否允许旋转屏幕
     *
     * @return
     */
    protected boolean isAllowScreenRotate() {
        return false;
    }

    /**
     * 在这里初始化View
     */
    public abstract void initView();


    /**
     * 在这里处理业务逻辑
     */
    public abstract void doBusiness();

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    /**
     * 处理键盘问题
     */
    @Override
    public void finish() {
        KeyboardHelper.hideKeyboard(getWindow().getDecorView());
        super.finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
        ActivityManager.INSTANCE.remove(this);
    }

    @Override
    public void showProgressView() {
        if (mLoadingDialog != null) mLoadingDialog.show();
    }

    @Override
    public void dismissProgressView() {
        if (mLoadingDialog != null) mLoadingDialog.dismiss();
    }


    /**
     * 简单的错误处理
     *
     * @param t
     */
    @Override
    public void dealError(Throwable t) {
        Util.toastError(t);
    }

    /**
     * 获取{@link RxPermissions}对象
     *
     * @return
     */
    public RxPermissions getRxPermissions() {
        if (mRxPermissions == null) mRxPermissions = new RxPermissions(getActivity());
        return mRxPermissions;
    }

    /**
     * 权限申请失败后提示用户
     *
     * 提供权限申请成功的回调
     *
     * 在回调中处理业务
     *
     * @param permissions
     * @param success
     */
    @SuppressLint("CheckResult")
    public void requestPermissions(String[] permissions, Action success) {
        getRxPermissions().request(permissions)
                .compose(RxUtil.applySchedulers(this, HUD_TYPE_NONE))
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        success.run();
                    } else {
                        Util.showToast(R.string.permission_request_fail);
                    }
                }, throwable -> showToast(throwable.getMessage()));
    }

    /**
     * 权限被拒绝，直接结束当前页面
     *
     * @param permissions
     */
    @SuppressLint("CheckResult")
    public void requestPermissions(String[] permissions) {
        getRxPermissions().request(permissions)
                .compose(RxUtil.applySchedulers(this, HUD_TYPE_NONE))
                .subscribe(aBoolean -> {
                    if (!aBoolean) {
                        Util.showToast(R.string.permission_request_fail);
                        finish();
                    }
                }, throwable -> {
                    showToast(throwable.getMessage());
                    finish();
                });
    }

    public String getText(TextView textView) {
        return textView.getText().toString().trim();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mLifecycleListener != null) {
            mLifecycleListener.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 注册生命周期
     *
     * 使用时确认监听是否被重新设置
     *
     * @param listener
     */
    public void registerLifecycleListener(LifecycleListener listener) {
        this.mLifecycleListener = listener;
    }

    @Override
    public void showRefreshView() {

    }

    @Override
    public void dismissRefreshView() {

    }


    @Override
    public void showToast(String msg) {
        Util.showToast(msg);
    }

    @Override
    public void showToast(@StringRes int msg) {
        Util.showToast(msg);
    }

    @Override
    public void showToastLong(String msg) {
        Util.showToastLong(msg);
    }

    @Override
    public void showToastLong(@StringRes int msg) {
        Util.showToastLong(msg);
    }


    @Override
    public void clearList() {

    }

}