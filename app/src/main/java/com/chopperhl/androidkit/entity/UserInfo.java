package com.chopperhl.androidkit.entity;

import android.text.TextUtils;
import com.chopperhl.androidkit.common.Constants;
import com.chopperhl.androidkit.util.SharedPrefUtil;

/**
 * Description:
 * Author chopperhl
 * Date 10/8/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class UserInfo {
    private static UserInfo INSTANCE;
    private String token;

    public static String getLoginID() {
        return SharedPrefUtil.INSTANCE.getSharedStr(Constants.KEY_LOGIN_ID, "");
    }

    public static void saveLoginID(String loginID) {
        SharedPrefUtil.INSTANCE.getDefaultSharedEditor()
                .putString(Constants.KEY_LOGIN_ID, loginID)
                .apply();
    }

    private UserInfo() {
    }

    public static UserInfo getInstance() {
        if (INSTANCE == null) {
            synchronized (UserInfo.class) {
                if (INSTANCE == null) {
                    INSTANCE = SharedPrefUtil.INSTANCE
                            .getObj(Constants.KEY_USER_INFO, UserInfo.class);
                }
                if (INSTANCE == null) {
                    INSTANCE = new UserInfo();
                }
            }
        }
        return INSTANCE;
    }

    public void setToken(String token) {
        this.token = token;
        SharedPrefUtil.INSTANCE.getDefaultSharedEditor()
                .putString(Constants.KEY_LOGIN_TOKEN, token)
                .apply();
    }

    public String getToken() {
        if (!TextUtils.isEmpty(token)) return token;
        return token = SharedPrefUtil.INSTANCE.getSharedStr(Constants.KEY_LOGIN_TOKEN, "");
    }

    public void clearUserInfo() {
        setToken(null);
        SharedPrefUtil.INSTANCE.putObj(Constants.KEY_USER_INFO, null);
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(getToken());
    }
}
