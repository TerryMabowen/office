package com.mbw.office.sso.entity.permission;

import com.mbw.office.common.enums.EnumLogicStatus;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 权限表
 * @author Mabowen
 * @date 2020-07-01 15:52
 */
@Data
@Entity
@Table(name = "oc_sso_permissions")
@EntityListeners(AuditingEntityListener.class)
public class PermissionPO implements Serializable {
    private static final long serialVersionUID = -7372985955141388266L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 应用ID
     */
    @Column(name = "app_id")
    private Long appId;

    /**
     * 权限编码
     */
    private String code;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 父权限ID
     */
    private Long pid;

    /**
     * 父路径
     */
    @Column(name = "parent_path")
    private String parentPath;

    private Integer status = EnumLogicStatus.NORMAL.getValue();

    @CreatedDate
    @Column(name = "created_time")
    private Date createdTime;

    @LastModifiedDate
    @Column(name = "updated_time")
    private Date updatedTime;
}
