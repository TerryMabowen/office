package com.mbw.office.cloud.common.kit.bean;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * cn.hutool.core.bean.BeanUtil增强工具
 *
 * @author Mabowen
 * @date 2020-07-07 10:16
 */
public class BeanKit {

    public static <T, E> List<T> toBeans(List<E> list, Class<T> clz) {
        if (CollUtil.isNotEmpty(list)) {
            return list.stream().map(e -> BeanUtil.toBean(e, clz)).collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}
