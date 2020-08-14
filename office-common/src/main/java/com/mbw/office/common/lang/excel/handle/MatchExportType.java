package com.mbw.office.common.lang.excel.handle;

import com.mbw.office.common.lang.excel.mate.match.EnumExportMatchType;

import java.lang.annotation.*;

/**
 * 赛事导出类型注解
 * @author Mabowen
 * @date 2020-01-15 13:07
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchExportType {

    EnumExportMatchType value();
}
