package com.mbw.office.common.lang.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mbw.office.common.lang.excel.dto.ExportExcelQueryDTO;
import com.mbw.office.common.lang.excel.handle.AbstractExportHandler;
import com.mbw.office.common.lang.excel.handle.ExportHandlerHelper;
import com.mbw.office.common.lang.page.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 导出处理器帮助类
 * @author Mabowen
 * @date 2020-01-04 16:44
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class ExportDataService {
    @Autowired
    private ExportHandlerHelper exportHandlerHelper;

    /**
     * 获取导出的数据的分页列表数据
     *
     * @author Mabowen
     * @date 14:59 2020-01-15
     * @param pageNumber
     * @param pageSize
     * @param sourceId 比赛/游学id
     * @param modelType 导出的报表模版类型
     * @return
     */
    public PageBean<?> pageMatchExportData(int pageNumber, int pageSize, Long sourceId, String modelType, Long matchProjectId) {
        return exportHandlerHelper.pageExportData(pageNumber, pageSize, sourceId, modelType, matchProjectId);
    }

    /**
     * 通过模版类型导出比赛excel表
     * 获取一个workbook
     * @author Mabowen
     * @date 19:32 2020-01-04
     * @param matchExcelDTO 条件
     * @return
     */
    public List<Map<String, Object>> exportByModelTypes(ExportExcelQueryDTO matchExcelDTO) {
        // 根据模版类型获取到相应的处理者
        List<AbstractExportHandler> handlers = exportHandlerHelper.listExportMatchExcelHandlers(matchExcelDTO.getModelTypes());
        // 通过handler获取需要导出的数据
        Map<String, List<?>> matchMap = Maps.newHashMap();
        if (handlers != null && !handlers.isEmpty()) {
            handlers.forEach(handler -> {
                // 执行获取list
                List<?> matches = handler.listExportData(matchExcelDTO.getSourceId(), matchExcelDTO.getMatchProjectId());
                matchMap.put(handler.getModelType(), matches);
            });
        }
        // 设置sheet页
        if (!matchMap.isEmpty()) {
            List<Map<String, Object>> list = Lists.newArrayList();
            matchMap.forEach((k, v) -> {
                // 设置sheet
                ExportParams params = new ExportParams();
                params.setType(ExcelType.XSSF);
                params.setStyle(CustomExportExcelStyler.class);
                // 根据modelType获取表名
                exportHandlerHelper.setSheetName(params, k);
                Map<String, Object> map = Maps.newHashMap();
                // title
                map.put("title", params);
                if (!"organizationMatchAllInfo".equalsIgnoreCase(k)) {
                    // entity 根据modelType获取导出的实体类
                    exportHandlerHelper.setExportEntity(map, k);
                    if (!v.isEmpty()) {
                        // data
                        map.put("data", v);
                    } else {
                        map.put("data", Lists.newArrayList());
                    }
                }
                list.add(map);
            });
            return list;
        }
        return Collections.emptyList();
    }

    /**
     * 获取导出的excel workbook集合
     * @author Mabowen
     * @date 2020/02/18 17:41
     * @param matchExcelDTO
     * @return {@link List<Workbook>}
     */
    @SuppressWarnings("unchecked")
    public List<Workbook> exportExcelToZip(ExportExcelQueryDTO matchExcelDTO, List<String> excelNames, String filename) {
        // 根据模版类型获取到相应的处理者
        List<AbstractExportHandler> handlers = exportHandlerHelper.listExportMatchExcelHandlers(matchExcelDTO.getModelTypes());
        // 通过handler获取需要导出的数据
        Map<String, List<?>> matchMap = Maps.newHashMap();
        if (handlers != null && !handlers.isEmpty()) {
            handlers.forEach(handler -> {
                // 执行获取list
                List<?> matches = handler.listExportData(matchExcelDTO.getSourceId(), matchExcelDTO.getMatchProjectId());
                matchMap.put(handler.getModelType(), matches);
            });
        }
        // 设置sheet页
        if (!matchMap.isEmpty()) {
            List<Workbook> workbooks = Lists.newArrayList();
            List<Map<String, Object>> list = Lists.newArrayList();
            matchMap.forEach((k, v) -> {
                AbstractExportHandler handler = exportHandlerHelper.getHandler(k);
                //excelNames
                // 设置sheet
                ExportParams params = new ExportParams();
                params.setType(ExcelType.XSSF);
                params.setStyle(CustomExportExcelStyler.class);
                // 根据modelType获取表名
                exportHandlerHelper.setSheetName(params, k);
                Map<String, Object> map = Maps.newHashMap();
                if (!"organizationMatchAllInfo".equalsIgnoreCase(k)) {
                    // title
                    map.put("title", params);
                    // entity 根据modelType获取导出的实体类
                    exportHandlerHelper.setExportEntity(map, k);
                    // data
                    if (!v.isEmpty()) {
                        map.put("data", v);
                    } else {
                        map.put("data", Lists.newArrayList());
                    }

                    list.add(map);
                } else if ("organizationMatchAllInfo".equalsIgnoreCase(k)) {
                    String sheetName = handler.getSheetName(k);
                    if (!excelNames.contains(sheetName)) {
                        excelNames.add(sheetName);
                    }
                    // ExportParams
                    // colList
                    List<ExcelExportEntity> colList = handler.generateExcelColList(matchExcelDTO.getSourceId());
                    // dataList
                    List<Map<String, Object>> dataList = (List<Map<String, Object>>) handler.listExportData(matchExcelDTO.getSourceId(), matchExcelDTO.getMatchProjectId());

                    Workbook workbook = ExcelExportUtil.exportExcel(params, colList, dataList);
                    workbooks.add(workbook);
                }
            });

            if (!list.isEmpty()) {
                if (!excelNames.contains(filename)) {
                    excelNames.add(filename);
                }
                Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.XSSF);
                workbooks.add(workbook);
            }
            return workbooks;
        }

        return Collections.emptyList();
    }

    /**
     * 动态生成表头
     * @author Mabowen
     * @date 2020/02/15 15:02
     * @param matchId
     * @param modelType
     * @return {@link List<Map<String,Object>>}
     */
    public List<Map<String, Object>> dynamicGenerateTableHead(Long matchId, String modelType) {
        if (matchId != null && StringUtils.isNotBlank(modelType)) {
            if ("organizationMatchAllInfo".equalsIgnoreCase(modelType)) {
                AbstractExportHandler handler = exportHandlerHelper.getHandler(modelType);
                return handler.generateTableHead(matchId);
            }
        }
        return Collections.emptyList();
    }

    /**
     * 获取excel表名
     * @author Mabowen
     * @date 2020/02/18 17:40
     * @param matchExcelDTO
     * @return {@link List<String>}
     */
    public List<String> listExcelNames(ExportExcelQueryDTO matchExcelDTO) {
        // 根据模版类型获取到相应的处理者
        List<AbstractExportHandler> handlers = exportHandlerHelper.listExportMatchExcelHandlers(matchExcelDTO.getModelTypes());
        if (!handlers.isEmpty()) {
            List<String> excelNames = Lists.newArrayList();
            handlers.forEach(h -> {
                for (String modelType : matchExcelDTO.getModelTypes()) {
                    String sheetName = h.getSheetName(modelType);
                    if (!excelNames.contains(sheetName)) {
                        excelNames.add(sheetName);
                    }
                }
            });

            return excelNames;
        }
        return Collections.emptyList();
    }
}
