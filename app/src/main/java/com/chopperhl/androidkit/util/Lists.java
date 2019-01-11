package com.chopperhl.androidkit.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description:
 * Author chopperhl
 * Date 6/1/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class Lists {
    private Lists() {
    }

    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E... elements) {
        ArrayList<E> list = new ArrayList<>();
        if (elements == null) return list;
        Collections.addAll(list, elements);
        return list;
    }

    public static boolean isNotEmpty(List list) {
        return (list != null && list.size() > 0);
    }

    public static boolean isEmpty(List list) {
        return (list == null || list.size() == 0);
    }

    public static <E> List<E> replaceNull(List list) {
        return list == null ? new ArrayList<>() : list;
    }

}

