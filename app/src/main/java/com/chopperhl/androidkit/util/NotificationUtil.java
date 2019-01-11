package com.chopperhl.androidkit.util;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import com.chopperhl.androidkit.R;
import com.chopperhl.androidkit.base.BaseApplication;
import com.chopperhl.androidkit.widget.AlertDialog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Description:
 * Author chopperhl
 * Date 8/20/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class NotificationUtil {

    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    public static boolean isNotificationEnabled() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return true;
        }
        Context context = BaseApplication.getApplication();
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        try {
            Method checkOpNoThrowMethod = AppOpsManager.class.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = AppOpsManager.class.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void ask2SetNotification(Activity activity, DialogInterface.OnClickListener onCancelListener) {
        if (isNotificationEnabled()) return;
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setTitle(R.string.notification_permission)
                .setContent(R.string.notification_setting)
                .setOnCancelListener(onCancelListener)
                .setOnConfirmListener(((dialog, which) -> {
                    Intent localIntent = new Intent();
                    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    localIntent.setData(Uri.fromParts("package", BaseApplication.getApplication().getPackageName(), null));
                    activity.startActivity(localIntent);
                }))
                .create();
        alertDialog.show();

    }

}
