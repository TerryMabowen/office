package com.mbw.office.demo.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
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

    @TableField(value = "created_time")
    protected Date createdTime;

    @TableField(value = "updated_time")
    protected Date updatedTime;
}
