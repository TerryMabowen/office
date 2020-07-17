package com.mbw.office.demo.jalian.model.app.dto;

import lombok.Data;

/**
 *
 * @author Mabowen
 * @date 2020-07-01 16:26
 */
@Data
public class AppDTO {
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
