package com.mbw.office.demo.entity.user;

import com.mbw.office.demo.entity.role.RolePO;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户表
 * @author Mabowen
 * @date 2020-07-01 15:32
 */
@Data
@Entity
@Table(name = "oc_sso_users")
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
    private String passwordHash;

    @OneToMany(targetEntity = RolePO.class, fetch=FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Set<RolePO> roles = new HashSet<>();
}
