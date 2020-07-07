package com.mbw.office.demo.entity.role;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色表
 * @author Mabowen
 * @date 2020-07-01 15:51
 */
@Data
@Entity
@Table(name = "oc_sso_roles")
public class RolePO implements Serializable {
    private static final long serialVersionUID = -1515580025645265875L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 角色编号
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;
}
