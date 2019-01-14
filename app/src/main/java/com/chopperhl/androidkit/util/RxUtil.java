package com.chopperhl.androidkit.util;


import com.chopperhl.androidkit.R;
import com.chopperhl.androidkit.base.BaseView;
import com.chopperhl.androidkit.common.ActivityManager;
import com.chopperhl.androidkit.entity.UserInfo;
import com.chopperhl.androidkit.repository.network.ApiException;
import com.chopperhl.androidkit.repository.network.BaseResponse;
import com.chopperhl.androidkit.widget.AlertDialog;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Description:
 * Author chopperhl
 * Date 6/1/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class RxUtil {
    /**
     * RxJava 绑定生命周期
     *
     * @param view
     * @param <T>
     * @return
     */
    public static <T> LifecycleTransformer<T> bindToLifecycle(@NonNull BaseView view) {
        if (view instanceof RxAppCompatActivity) {
            LifecycleProvider<ActivityEvent> lifecycleProvider = (LifecycleProvider) view;
            return lifecycleProvider.bindUntilEvent(ActivityEvent.DESTROY);
        } else if (view instanceof RxFragment) {
            LifecycleProvider<FragmentEvent> lifecycleProvider = (LifecycleProvider) view;
            return lifecycleProvider.bindUntilEvent(FragmentEvent.DESTROY);
        } else {
            throw new IllegalArgumentException("lifecycle bind error");
        }
    }

    /**
     * RxJava处理线程调度，加载动画，生命周期绑定
     *
     * @param view
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> applySchedulers(final BaseView view) {
        return applySchedulers(view, BaseView.HUD_TYPE_PROGRESS);
    }


    /**
     * RxJava处理线程调度，加载动画，生命周期绑定
     *
     * 可控制加载动画的显示
     *
     * @param view
     * @param hudType
     * @param <T>
     * @return
     */

    public static <T> ObservableTransformer<T, T> applySchedulers(final BaseView view, int hudType) {
        return observable -> observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (hudType == BaseView.HUD_TYPE_NONE) return;
                    if (hudType == BaseView.HUD_TYPE_PROGRESS) view.showProgressView();
                    if (hudType == BaseView.HUD_TYPE_REFRESH) view.showRefreshView();

                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (hudType == BaseView.HUD_TYPE_NONE) return;
                    if (hudType == BaseView.HUD_TYPE_PROGRESS) view.dismissProgressView();
                    if (hudType == BaseView.HUD_TYPE_REFRESH) view.dismissRefreshView();
                })
                .compose(bindToLifecycle(view));
    }


    /**
     * data返回校验
     *
     * @return
     */
    public static <T extends BaseResponse> ObservableTransformer<T, T> applyValidate() {
        return observable -> observable.map(response -> {
            if (response == null) throw new NullPointerException();
            if (!response.isOk()) {
                if (response.code == 2) {//需要登陆
                    UserInfo.getInstance().clearUserInfo();
                    new AlertDialog.Builder(ActivityManager.INSTANCE.getTopActivity())
                            .setTitle(R.string.login_not_avi)
                            .setContent(R.string.ask_2_login)
                            .setOnConfirmListener((dialog, which) -> {

                            })
                            .create()
                            .show();
                }
                throw new ApiException(response.message, response.code);
            }
            return response;
        });
    }

}
