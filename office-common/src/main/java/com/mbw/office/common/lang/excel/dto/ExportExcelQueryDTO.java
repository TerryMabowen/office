package com.mbw.office.common.lang.excel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 导出excel的条件DTO
 *
 * @author Mabowen
 * @date 2020-08-14 15:38
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExportExcelQueryDTO {
    /**
     * 比赛id
     */
    private Long sourceId;

    /**
     * 导出的模版类型
     */
    private Set<String> modelTypes;

    /**
     * 赛项id
     */
    private Long matchProjectId;
}
