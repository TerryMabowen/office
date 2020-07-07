package com.mbw.office.demo.entity.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
public class BaseEntity extends Model<BaseEntity> {
    private static final long serialVersionUID = 541122854610664130L;

    @TableLogic(value = "1")
    protected Integer status = EnumLogicStatus.NORMAL.getValue();

    @TableField(fill = FieldFill.INSERT)
    protected Date createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Date updatedTime;
}
