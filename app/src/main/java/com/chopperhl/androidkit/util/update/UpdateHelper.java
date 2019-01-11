package com.chopperhl.androidkit.util.update;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.chopperhl.androidkit.Config;
import com.chopperhl.androidkit.R;
import com.chopperhl.androidkit.base.BaseApplication;
import com.chopperhl.androidkit.common.ActivityManager;
import com.chopperhl.androidkit.repository.network.NetworkManager;
import com.chopperhl.androidkit.repository.network.api.MainService;
import com.chopperhl.androidkit.util.Util;
import com.chopperhl.androidkit.widget.AlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UpdateHelper {
    public static UpdateHelper INSTANCE = new UpdateHelper();

    private UpdateHelper() {
    }


    /**
     * @param showToast 已经为最新版本时是否提示
     */
    @SuppressLint("CheckResult")
    public void checkUpdate(boolean showToast) {
        MainService mainService = NetworkManager.INSTANCE.getMainService();
        mainService.checkUpdate(Config.UPDATE_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(map -> {
                    if (map == null
                            || TextUtils.isEmpty(map.get("version"))
                            || TextUtils.isEmpty(map.get("download"))) {
                        throw new NullPointerException();
                    }
                    int versionCode = Integer.parseInt(map.get("version"));
                    //本地版本比服务器版本高时
                    if (getVersionCode() >= versionCode) {
                        if (showToast) Util.showToast(R.string.already_the_latest_version);
                        return;
                    }
                    showNoticeDialog(map.get("download"));
                }, throwable -> {
                    throwable.printStackTrace();
                    Util.showToast(R.string.update_check_failed);
                });

    }

    /**
     * 是否更新提示窗口
     */
    private void showNoticeDialog(String url) {
        Context context = ActivityManager.INSTANCE.getTopActivity();
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.software_update))
                .setContent(context.getString(R.string.ask_to_update))
                .setCancelOutside(false)
                .setCancelable(false)
                .setOnConfirmListener((dialog, what) -> startUpdate(url))
                .create();
        alertDialog.show();
    }

    /**
     * 通知栏更新下载
     */
    private void startUpdate(String url) {
        UpdateService.Builder.create(url)
                .setIsSendBroadcast(true)
                .setDownloadSuccessNotificationFlag(Notification.DEFAULT_SOUND)
                .setDownloadErrorNotificationFlag(Notification.DEFAULT_SOUND)
                .build(BaseApplication.getApplication());
        Util.showToast(R.string.apk_start_download);
    }


    public int getVersionCode() {
        try {
            Context context = BaseApplication.getApplication();
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    public String getVersionName() {
        try {
            Context context = BaseApplication.getApplication();
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "1.0.0";
        }
    }
}
