package com.mbw.office.common.lang.excel.handle;

import com.mbw.office.common.lang.excel.mate.travel.EnumExportTravelType;

import java.lang.annotation.*;

/**
 * 游学导出类型注解
 * @author Mabowen
 * @date 2020-01-15 13:08
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TravelExportType {

    EnumExportTravelType value();
}
