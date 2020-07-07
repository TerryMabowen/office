package com.mbw.office.demo.entity.base;

import com.mbw.office.common.enums.EnumLogicStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author Mabowen
 * @date 2020-07-07 15:24
 */
@Data
@EqualsAndHashCode
@ToString
@MappedSuperclass
public class BaseEntity {
    private Integer status = EnumLogicStatus.NORMAL.getValue();

    @CreatedDate
    private Date createdTime;

    @UpdateTimestamp
    private Date updatedTime;
}
