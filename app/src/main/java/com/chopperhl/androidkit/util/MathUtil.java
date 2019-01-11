package com.chopperhl.androidkit.util;


import java.math.BigDecimal;

/**
 * Description: 浮点计算的工具类
 * Author chopperhl
 * Date 6/1/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class MathUtil {

    public static BigDecimal parseBigDecimal(String str) {
        return parseBigDecimal(str, 2);
    }

    public static BigDecimal parseBigDecimal(String str, int scale) {
        BigDecimal db = BigDecimal.ZERO.setScale(scale, BigDecimal.ROUND_HALF_UP);
        try {
            db = new BigDecimal(str).setScale(scale, BigDecimal.ROUND_HALF_UP);
        } catch (Exception ignored) {
        }
        return db;
    }

    public static BigDecimal parseBigDecimal(BigDecimal decimal) {
        return parseBigDecimal(decimal, 2);
    }

    public static BigDecimal parseBigDecimal(BigDecimal decimal, int scale) {
        return decimal == null ? BigDecimal.ZERO.setScale(scale, BigDecimal.ROUND_HALF_UP)
                : decimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

}
