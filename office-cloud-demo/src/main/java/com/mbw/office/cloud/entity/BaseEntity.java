package com.mbw.office.cloud.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.mbw.office.cloud.common.lang.enums.EnumLogicStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Mabowen
 * @date 2020-07-02 14:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BaseEntity extends Model<BaseEntity> {
    private static final long serialVersionUID = 541122854610664130L;

    protected Integer status = EnumLogicStatus.NORMAL.getValue();

    @TableField(fill = FieldFill.INSERT)
    protected Date createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Date updatedTime;
}
