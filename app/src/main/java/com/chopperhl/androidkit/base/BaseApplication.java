package com.chopperhl.androidkit.base;


import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chopperhl.androidkit.BuildConfig;


import java.util.List;


/**
 * Description: ApplicationContext
 * Author chopperhl
 * Date 6/1/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class BaseApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static BaseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        int pid = android.os.Process.myPid();
        String processName = getProcessName(pid);
        application = this;
        //只在主进程初始化
        if (!getPackageName().equalsIgnoreCase(processName)) return;
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    /**
     * 获取主进程名字
     *
     * @param pID
     * @return
     */
    private String getProcessName(int pID) {
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        if (am == null) return null;
        List<ActivityManager.RunningAppProcessInfo> infoList = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infoList) {
            if (info.pid == pID) return info.processName;
        }
        return null;
    }


    public static BaseApplication getApplication() {
        return application;
    }
}
