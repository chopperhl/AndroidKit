package com.chopperhl.androidkit.util;

import android.content.Context;
import android.os.Environment;
import com.chopperhl.androidkit.base.BaseApplication;


import java.io.File;
import java.math.BigDecimal;


/**
 * Description:
 * Author chopperhl
 * Date 10/15/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class CacheManager {
    private CacheManager() {
    }

    private static CacheManager INSTANCE;

    public static CacheManager getInstance() {
        if (INSTANCE == null) {
            synchronized (CacheManager.class) {
                if (INSTANCE == null) INSTANCE = new CacheManager();
            }
        }
        return INSTANCE;
    }

    /**
     * 获取缓存大小
     *
     * @return
     */
    public String getTotalCacheSize() {
        Context context = BaseApplication.getApplication();
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    /**
     * 清除缓存
     */
    public void clearAllCache() {
        Context context = BaseApplication.getApplication();
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    private boolean deleteDir(File dir) {
        if (dir == null) return false;
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) return false;

            }
        }
        return dir.delete();
    }

    private long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                // 如果下面还有文件
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    private String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return "0.00K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "K";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "M";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }
}
