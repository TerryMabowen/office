package com.mbw.office.sso.entity.app;

import com.mbw.office.common.lang.enums.EnumLogicStatus;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 应用表
 * @author Mabowen
 * @date 2020-07-01 15:54
 */
@Data
@Entity
@Table(name = "oc_sso_apps")
@EntityListeners(AuditingEntityListener.class)
public class AppPO implements Serializable {
    private static final long serialVersionUID = -8618613332166867290L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 应用编码
     */
    private String code;

    /**
     * 应用名
     */
    private String name;

    /**
     * 应用链接
     */
    private String url;

    /**
     * 应用版本
     */
    private String version;

    /**
     * 应用秘钥
     */
    @Column(name = "app_secret")
    private String appSecret;

    /**
     * 启用状态
     */
    @Column(name = "enable_state")
    private Integer enableState;

    private Integer status = EnumLogicStatus.NORMAL.getValue();

    @CreatedDate
    @Column(name = "created_time")
    private Date createdTime;

    @LastModifiedDate
    @Column(name = "updated_time")
    private Date updatedTime;
}
