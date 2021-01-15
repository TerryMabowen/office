package com.mbw.office.common.lang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 任务执行状态
 *
 * @author Mabowen
 * @date 2021-01-15 18:46
 */
@Getter
@AllArgsConstructor
public enum EnumTaskState implements BaseEnumStatus<Integer> {
    WAITING(1, "等待执行"),
    RUNNING(2, "正在执行"),
    SUCCESS_OVER(3, "执行成功"),
    FAILURE_OVER(4, "执行失败");

    private Integer value;
    private String desc;
}
