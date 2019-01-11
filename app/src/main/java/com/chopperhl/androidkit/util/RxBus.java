package com.chopperhl.androidkit.util;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Description:
 * Author chopperhl
 * Date 1/11/19
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class RxBus {

    private static RxBus instance;
    private Subject<Object> mBus;

    private RxBus() {
        this.mBus = PublishSubject.create().toSerialized();
    }

    public static RxBus getInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    public void post(Object obj) {
        mBus.onNext(obj);
    }

    public <T> Observable<T> toObservable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }

    public Observable<Object> toObservable() {
        return mBus;
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }
}
