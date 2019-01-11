package com.chopperhl.androidkit.base;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import com.chopperhl.androidkit.common.IParamsBuilder;
import com.chopperhl.androidkit.repository.network.BaseResponse;
import com.chopperhl.androidkit.repository.network.NetworkManager;
import com.chopperhl.androidkit.repository.network.api.MainService;
import com.chopperhl.androidkit.util.RxUtil;
import io.reactivex.Observable;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 与{@link BaseListActivity} 和{@link BaseListFragment} 配合使用
 * Author chopperhl
 * Date 6/5/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public abstract class BaseListPresenter<T, V extends BaseView> extends BasePresenter<V> implements IParamsBuilder {
    private int rows = 20;
    private boolean usePager = false;
    //过滤的参数集合
    private Map<String, String> filterParams = new HashMap<>();


    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getRows() {
        return rows;
    }

    /**
     * 设置是否使用分页
     *
     * {@link BaseListFragment#usePager()} {@link BaseListActivity#usePager()}  }
     * 的返回值会传递到这里
     *
     * @param usePager
     */
    public void setUsePager(boolean usePager) {
        this.usePager = usePager;
    }


    @SuppressLint("CheckResult")
    @Override
    public void requestApi(int page) {
        //请求参数集合
        Map<String, String> requestParams = new HashMap<>();
        for (String key : filterParams.keySet()) {
            String val = filterParams.get(key);
            if (TextUtils.isEmpty(val)) continue;
            requestParams.put(key, val);
        }
        if (usePager) requestParams.put("page", String.valueOf(page));
        if (usePager) requestParams.put("rows", String.valueOf(rows));
        fetchData(requestParams)
                .compose(RxUtil.applySchedulers(mView, page <= 1 ? BaseView.HUD_TYPE_REFRESH : BaseView.HUD_TYPE_NONE))
                .compose(RxUtil.applyValidate())
                .subscribe(response -> {
                    if (page == 1) mView.clearList();
                    onDataResponse(response.data);
                }, throwable -> mView.dealError(throwable));
    }


    public MainService getMainService() {
        return NetworkManager.INSTANCE.getMainService();
    }

    /**
     * 处理请求结果响应
     *
     * @param data
     */
    protected abstract void onDataResponse(T data);

    /**
     * 发起网络
     *
     * @param params
     * @return
     */
    protected abstract Observable<BaseResponse<T>> fetchData(Map<String, String> params);


    @Override
    public IParamsBuilder clearParams() {
        filterParams.clear();
        return this;
    }

    @Override
    public IParamsBuilder appendParams(String key, String val) {
        filterParams.put(key, val);
        return this;
    }
}
