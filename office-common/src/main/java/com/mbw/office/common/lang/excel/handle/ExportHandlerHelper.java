package com.mbw.office.common.lang.excel.handle;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.google.common.collect.Lists;
import com.mbw.office.common.lang.excel.mate.BaseExcelEntity;
import com.mbw.office.common.lang.page.PageBean;
import com.mbw.office.common.util.validate.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Mabowen
 * @date 2020-01-04 19:46
 */
@Component
public class ExportHandlerHelper {
    @Autowired
    private ExportHandlerContext exportHandlerContext;

    /**
     * 根据导出的报表模版类型获取对应的handler
     *
     * @author Mabowen
     * @date 14:58 2020-01-15
     * @param modelTypes
     * @return
     */
    public List<AbstractExportHandler> listExportMatchExcelHandlers(Set<String> modelTypes) {
        if (modelTypes != null && !modelTypes.isEmpty()) {
            List<AbstractExportHandler> handlers = Lists.newArrayList();
            modelTypes.forEach(modelType -> {
                AbstractExportHandler exportHandler = getHandler(modelType);
                if (exportHandler != null) {
                    handlers.add(exportHandler);
                }
            });
            return handlers;
        }

        return Collections.emptyList();
    }

    /**
     * 根据modelType设置sheet名
     *
     * @author Mabowen
     * @date 11:58 2020-01-15
     * @param params
     * @param modelType 导出的报表模版类型
     */
    public void setSheetName(ExportParams params, String modelType) {
        if (StringUtils.isNotBlank(modelType)) {
            AbstractExportHandler exportHandler = getHandler(modelType);
            exportHandler.setSheetName(params, modelType);
        }
    }

    /**
     * 根据modelType设置导出的实体类
     *
     * @author Mabowen
     * @date 11:59 2020-01-15
     * @param map
     * @param modelType 导出的报表模版类型
     * @return
     */
    public void setExportEntity(Map<String, Object> map, String modelType) {
        if (StringUtils.isNotBlank(modelType)) {
            AbstractExportHandler exportHandler = getHandler(modelType);
            exportHandler.setExportEntity(map, modelType);
        }
    }

    /**
     * 获取导出的数据的分页列表数据
     *
     * @author Mabowen
     * @date 14:57 2020-01-15
     * @param pageNumber
     * @param pageSize
     * @param sourceId 比赛/游学id
     * @param modelType 导出的报表模版类型
     * @return
     */
    public PageBean<? extends BaseExcelEntity> pageExportData(int pageNumber, int pageSize, Long sourceId, String modelType, Long matchProjectId) {
        AssertUtil.assertNotNull(sourceId, "比赛/游学id不能为空");
        AssertUtil.assertNotEmpty(modelType, "导出的报表模版类型不能为空");

        AbstractExportHandler exportHandler = getHandler(modelType);
        return exportHandler.getPageData(pageNumber, pageSize, sourceId, matchProjectId);
    }

    /**
     * 根据类型获取处理器
     * @author Mabowen
     * @date 2020/02/15 15:12
     * @param modelType
     * @return {@link AbstractExportHandler}
     */
    public AbstractExportHandler getHandler(String modelType) {
        return exportHandlerContext.getExportHandler(modelType);
    }
}
