package com.mbw.office.demo.entity.base;

import com.mbw.office.common.enums.EnumLogicStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Mabowen
 * @date 2020-07-02 14:32
 */
@Data
@Accessors(chain = true)
public class BaseEntity {
    protected Integer status = EnumLogicStatus.NORMAL.getValue();

    protected Date createdTime;

    protected Date updatedTime;
}
