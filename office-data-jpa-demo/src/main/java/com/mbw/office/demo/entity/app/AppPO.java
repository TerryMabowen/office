package com.mbw.office.demo.entity.app;

import java.io.Serializable;

/**
 * 应用表
 * @author Mabowen
 * @date 2020-07-01 15:54
 */

public class AppPO implements Serializable {
    private static final long serialVersionUID = -8618613332166867290L;

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
    private String appSecret;

    /**
     * 启用状态
     */
    private Integer enableState;
}
