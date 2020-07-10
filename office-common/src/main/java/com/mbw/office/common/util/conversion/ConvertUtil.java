package com.mbw.office.common.util.conversion;

import cn.hutool.core.util.StrUtil;

import java.util.*;

/**
 * 转化工具类
 *
 * @author Mabowen
 * @date 2020-07-10 11:04
 */
public class ConvertUtil {

    public static <T> String convertListToStr(List<T> list) {
        return convertCollectionToStr(list);
    }

    public static <T> String convertSetToStr(Set<T> set) {
        return convertCollectionToStr(set);
    }

    public static List<String> convertStrToList(String str) {
        return (List<String>) convertStrToCollection(str, List.class);
    }

    public static Set<String> convertStrToSet(String str) {
        return (Set<String>) convertStrToCollection(str, Set.class);
    }

    /**
     *
     * 将集合拼接成一个字符串,以,分隔
     * @author Mabowen
     * @date 11:19 2019-11-26
     */
    private static <E> String convertCollectionToStr(Collection<E> collection) {
        if (collection == null || collection.isEmpty()) {
            return StrUtil.EMPTY;
        }

        StringBuilder builder = new StringBuilder();
        int lastIndex = collection.size() - 1;
        int i = 0;
        for (E e : collection) {
            if (i == lastIndex) {
                builder.append(e);
            } else {
                builder.append(e).append(",");
            }
            i++;
        }

        return builder.toString();
    }

    /**
     *
     * 将以,分隔字符串转换成集合
     * @author Mabowen
     * @date 11:19 2019-11-26
     */
    private static <E> Collection<String> convertStrToCollection(String str, Class<? extends Collection> clz) {
        if (StrUtil.isNotBlank(str)) {
            String[] split = str.split(",");
            if (clz.isAssignableFrom(List.class)) {
                return new ArrayList<>(Arrays.asList(split));
            } else {
                return new HashSet<>(Arrays.asList(split));
            }
        }

        if (clz.isAssignableFrom(List.class)) {
            return Collections.emptyList();
        } else {
            return Collections.emptySet();
        }
    }
}
