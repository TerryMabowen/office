package com.mbw.office.sso.model.app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mbw.office.common.util.date.DateUtil;
import lombok.Data;

import java.util.Date;

/**
 *
 * @author Mabowen
 * @date 2020-07-01 16:26
 */
@Data
public class AppVO {
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

    /**
     * 数据状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DateUtil.DEFAULT_PATTERN)
    private Date createdTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = DateUtil.DEFAULT_PATTERN)
    private Date updatedTime;
}
