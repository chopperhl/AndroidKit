package com.chopperhl.androidkit.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.chopperhl.androidkit.util.Util;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.lang.reflect.Constructor;


/**
 * Description: 基础fragment
 * Author chopperhl
 * Date 6/1/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public abstract class BaseFragment<P extends BasePresenter> extends RxFragment implements BaseView {
    private Unbinder unbinder;
    private View view;
    protected P mPresenter;

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

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(bindLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initPresenter();
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle == null) bundle = new Bundle();
        initParams(bundle);

        initView();
        doBusiness();
        return view;
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }


    /**
     * 获取根View
     *
     * @return
     */
    public View getRootView() {
        return view;
    }

    /**
     * 返回布局文件
     *
     * @return
     */
    public abstract int bindLayout();

    public void startActivity(Class clz) {
        startActivity(new Intent(getActivity(), clz));
    }

    public void startActivity(Class clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clz);
        if (bundle != null) intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startActivityForResult(Class clz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(getActivity(), clz);
        if (bundle != null) intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(Class clz, int requestCode) {
        startActivityForResult(clz, null, requestCode);
    }

    /**
     * @return
     */
    protected boolean useEventBus() {
        return false;
    }

    /**
     * 在这里初始化参数
     *
     * @param params
     */
    protected abstract void initParams(Bundle params);


    /**
     * 在这里初始化View
     */
    protected abstract void initView();

    /**
     * 在这里处理业务逻辑
     */
    protected abstract void doBusiness();

    @Override
    public void showRefreshView() {

    }

    @Override
    public void dismissRefreshView() {

    }

    /**
     * 显示加载动画
     */
    public void showProgressView() {
        ((BaseActivity) getActivity()).showProgressView();
    }

    /**
     * 隐藏加载动画
     */
    public void dismissProgressView() {
        ((BaseActivity) getActivity()).dismissProgressView();
    }

    public int getColorRes(@ColorRes int color) {
        return getResources().getColor(color);
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
    public void dealError(Throwable t) {
        Util.toastError(t);
    }

    public String getText(TextView textView) {
        return textView.getText().toString().trim();
    }

    @Override
    public void clearList() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }


}
