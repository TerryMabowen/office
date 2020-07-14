package com.mbw.office.sso.entity.role;

import com.mbw.office.common.lang.enums.EnumLogicStatus;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色表
 * @author Mabowen
 * @date 2020-07-01 15:51
 */
@Data
@Entity
@Table(name = "oc_sso_roles")
@EntityListeners(AuditingEntityListener.class)
public class RolePO implements Serializable {
    private static final long serialVersionUID = -1515580025645265875L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 应用ID
     */
    @Column(name = "app_id")
    private Long appId;

    /**
     * 角色编号
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

    private Integer status = EnumLogicStatus.NORMAL.getValue();

    @CreatedDate
    @Column(name = "created_time")
    private Date createdTime;

    @LastModifiedDate
    @Column(name = "updated_time")
    private Date updatedTime;
}
