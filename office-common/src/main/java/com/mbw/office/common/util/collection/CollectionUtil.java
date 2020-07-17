package com.mbw.office.common.util.collection;

import java.util.*;

/**
 * 集合工具类
 *
 * @author Mabowen
 * @date 2020-07-10 11:01
 */
public class CollectionUtil {

    /**
     * 对多个集合并集，将多个集合合并成一个集合，对一个集合元素去重
     *
     * @author Mabowen
     * @date 09:23 2020-01-08
     * @param args 多个list集合
     * @return
     */
    @SafeVarargs
    public static <T> List<T> mergeList(List<T>... args) {
        Set<T> set = new HashSet<>();
        for (List<T> arg : args) {
            set.addAll(arg);
        }
        return new ArrayList<>(set);
    }

    public static <E> boolean isNotEmpty(Collection<E> collection) {
        return !isEmpty(collection);
    }

    public static <E> boolean isEmpty(Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }
}
