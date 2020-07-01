package com.mbw.office.common.entity;

import com.mbw.office.common.enums.EnumLogicStatus;
import com.mbw.office.common.util.date.DateUtil;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Mabowen
 * @date 2020-07-01 15:19
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -8666448776493504234L;

    private Integer status = EnumLogicStatus.NORMAL.getValue();

    @CreatedDate
    @Column(name = "created_time")
    private Date createdTime;

    @LastModifiedDate
    @Column(name = "updated_time")
    private Date updatedTime;

    protected void createStamp() {
        this.createdTime = DateUtil.now();
    }

    protected void updateStamp() {
        this.updatedTime = DateUtil.now();
    }
}
