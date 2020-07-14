package com.mbw.office.common.lang.mybatis;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author Mabowen
 * @date 2020-07-02 15:01
 */
public interface CommonMapper<T> extends ISqlInjector, BaseMapper<T> {
}
