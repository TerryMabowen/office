package com.mbw.office.cloud.common.kit.enums;


import com.mbw.office.cloud.common.lang.enums.BaseEnumStatus;
import com.mbw.office.cloud.common.lang.enums.CommonEnumStatus;

/**
 * @author Mabowen
 * @date 2020/04/10 23:13
 */
public class EnumKit {

    public static <T> BaseEnumStatus getEnumByValue(Class<?> clz, T value) {
        if (clz.isEnum()) {
            Object[] statuses = clz.getEnumConstants();

            for (Object o : statuses) {
                BaseEnumStatus status = (BaseEnumStatus) o;
                if (status.getValue().equals(value)) {
                    return status;
                }
            }
        }

        return CommonEnumStatus.UNKNOWN;
    }

    public static <T> BaseEnumStatus getEnumByDesc(Class<?> clz, T desc) {
        if (clz.isEnum()) {
            Object[] statuses = clz.getEnumConstants();

            for (Object o : statuses) {
                BaseEnumStatus status = (BaseEnumStatus) o;
                if (status.getDesc().equals(desc)) {
                    return status;
                }
            }
        }

        return CommonEnumStatus.UNKNOWN;
    }
}
