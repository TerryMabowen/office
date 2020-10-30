package com.mbw.office.dingtalk.biz.ding.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-10-30 10:29
 */
@Data
public class DtDepartmentVO {
    private Long id;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastSyncTime;

    private String oaDepartmentCode;

    private String oaDepartmentName;
}
