package com.mbw.office.sso.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mbw.office.common.lang.enums.EnumLogicStatus;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @author Mabowen
 * @date 2020-07-01 15:32
 */
@Data
@Entity
@Table(name = "oc_sso_users")
@EntityListeners(AuditingEntityListener.class)
public class UserPO implements Serializable {
    private static final long serialVersionUID = -5535772926419600611L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 加密密码
     */
    @Column(name = "password_hash")
    @JsonIgnore
    private String passwordHash;

    private Integer status = EnumLogicStatus.NORMAL.getValue();

    @CreatedDate
    @Column(name = "created_time")
    private Date createdTime;

    @LastModifiedDate
    @Column(name = "updated_time")
    private Date updatedTime;
}
