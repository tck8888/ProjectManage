package com.healthmudi.utils;

import java.util.List;


public class ListUtil {

    private ListUtil() {
        throw new AssertionError();
    }

    /**
     * 数据源大小
     *
     * @param sourceList 数据源
     */
    public static <V> int getSize(List<V> sourceList) {
        return sourceList == null ? 0 : sourceList.size();
    }

    /**
     * 数据源是否为空
     *
     * @param sourceList 数据源
     */
    public static <V> boolean isEmpty(List<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }

    /**
     * 清空集合
     *
     * @param sourceList 数据源
     */
    public static <V> void clearList(List<V> sourceList) {
        if (sourceList != null) {
            sourceList.clear();
            sourceList = null;
        }
    }

}
