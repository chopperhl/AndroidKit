package com.chopperhl.androidkit.repository.local;


import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;

import com.chopperhl.androidkit.BuildConfig;
import com.chopperhl.androidkit.base.BaseApplication;
import com.chopperhl.androidkit.repository.local.table.SearchHistory;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.SQLiteHelper;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.litesuits.orm.db.model.ColumnsValue;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * Author chopperhl
 * Date 7/30/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class DatabaseManager implements SQLiteHelper.OnUpdateListener {
    private static final String DB_NAME = "chopperhl.db";
    private static final int DB_VERSION = 1;
    private static DatabaseManager INSTANCE;
    private LiteOrm liteOrm;

    private DatabaseManager() {
    }

    public static DatabaseManager getInstance() {
        if (INSTANCE == null) {
            synchronized (DatabaseManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DatabaseManager();
                }
            }
        }
        return INSTANCE;
    }

    private LiteOrm getLiteOrm() {
        if (liteOrm == null) {
            synchronized (LiteOrm.class) {
                if (liteOrm == null) {
                    BaseApplication app = BaseApplication.getApplication();
                    DataBaseConfig config = new DataBaseConfig(app);
                    config.dbName = DB_NAME;
                    config.debugged = BuildConfig.DEBUG;
                    config.dbVersion = DB_VERSION;
                    config.onUpdateListener = this;
                    liteOrm = LiteOrm.newSingleInstance(config);
                }
            }
        }
        return liteOrm;
    }

    @Override
    public void onUpdate(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //处理数据库更新
    }


    @SuppressLint("CheckResult")
    public void insertKeyWords(String keyWords) {
        Observable.just(1).map(i -> {
            long now = new Date().getTime();
            long count = getLiteOrm().queryCount(QueryBuilder.create(SearchHistory.class).where("keyWords = ?", keyWords));
            if (count > 0) {
                getLiteOrm().update(WhereBuilder.create(SearchHistory.class)
                                .where("keyWords = ?", keyWords)
                        , new ColumnsValue(new String[]{"updateTime"}, new Object[]{now}), null);
            } else {
                SearchHistory searchHistory = new SearchHistory();
                searchHistory.createTime = now;
                searchHistory.updateTime = now;
                searchHistory.keyWords = keyWords;
                getLiteOrm().insert(searchHistory);
            }
            return 1;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                }, throwable -> {
                });
    }

    @SuppressLint("CheckResult")
    public Observable<List<SearchHistory>> queryTopHistory() {
        return Observable.just(1).map(integer -> getLiteOrm()
                .query(QueryBuilder.create(SearchHistory.class)
                        .appendOrderDescBy("updateTime")
                        .limit(0, 20)));
    }

    @SuppressLint("CheckResult")
    public Observable<Integer> deleteAllSearchHis() {
        return Observable.just(1).map(i -> {
            getLiteOrm().delete(SearchHistory.class);
            return 1;
        });
    }
}
