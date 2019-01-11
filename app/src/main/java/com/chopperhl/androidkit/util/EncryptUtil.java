package com.chopperhl.androidkit.util;


import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Description: 加密工具
 * Author chopperhl
 * Date 6/1/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class EncryptUtil {

    private static final String dic = "0123456789abcdef";


    /**
     * md5加密  返回字符串
     *
     * @param src
     * @return
     */
    public static String md5ToString(String src) {
        if (TextUtils.isEmpty(src)) src = "";
        byte[] bytes = new byte[0];
        try {
            bytes = MessageDigest.getInstance("md5").digest(src.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return byte2Hex(bytes);
    }


    /**
     * md5加密 返回byte
     *
     * @param src
     * @return
     */
    public static byte[] md5ToByte(String src) {
        if (TextUtils.isEmpty(src)) src = "";
        byte[] bytes = new byte[0];
        try {
            bytes = MessageDigest.getInstance("md5").digest(src.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * byte转16进制字符串
     *
     * @param src
     * @return
     */

    public static String byte2Hex(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte aSrc : src) {
            int v = aSrc & 0xff;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 16进制字符串传byte
     *
     * @param src
     * @return
     */
    public static byte[] hex2Byte(String src) {
        if (src == null || src.equals("")) {
            return null;
        }
        src = src.toLowerCase();
        int length = src.length() / 2;
        char[] hexChars = src.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (dic.indexOf(hexChars[pos]) << 4 | dic.indexOf(hexChars[pos + 1]));
        }
        return d;
    }



    public static String sha1(String info) {
        byte[] bytes = null;
        try {
            MessageDigest alga = MessageDigest.getInstance("SHA-1");
            alga.update(info.getBytes());
            bytes = alga.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return byte2Hex(bytes);
    }
}