package com.chopperhl.androidkit.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 * Author chopperhl
 * Date 8/13/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class RegUtil {
    public static boolean isPhoneNumber(String mobiles) {
        try {
            Pattern p = Pattern.compile("^((13)|(15)|(14)|(18)|(17)|(19))\\d{9}");
            Matcher m = p.matcher(mobiles);
            return m.matches();
        } catch (Exception ignored) {
        }
        return false;
    }
}
