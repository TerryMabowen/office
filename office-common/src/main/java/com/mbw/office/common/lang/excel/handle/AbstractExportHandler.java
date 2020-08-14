package com.mbw.office.common.lang.excel.handle;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.mbw.office.common.lang.excel.ExportEntityProperties;
import com.mbw.office.common.lang.excel.mate.BaseExcelEntity;
import com.mbw.office.common.lang.page.PageBean;
import com.mbw.office.common.util.validate.AssertUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Mabowen
 * @date 2020-01-04 19:45
 */
@Slf4j
@Data
@Component
@Transactional(readOnly = true)
public abstract class AbstractExportHandler {
    protected final int pageNumber = 1;
    protected final int pageSize = 20;
    private String modelType = "";
    protected String sheetTitle = "";
    protected String sheetName = "";
    protected PageBean<? extends BaseExcelEntity> pageQuery = new PageBean<>(this.pageNumber, this.pageSize);

    /**
     * 获取导出的所有数据
     * @author Mabowen
     * @date 2020/02/15 15:14
     * @param sourceId
     * @param matchProjectId
     * @return {@link List<? extends BaseExcelEntity>}
     */
    public abstract List<?> listExportData(Long sourceId, Long matchProjectId);

    /**
     * 获取分页列表数据
     * @author Mabowen
     * @date 2020/02/15 15:15
     * @param pageNumber
     * @param pageSize
     * @param sourceId
     * @param matchProjectId
     * @return
     */
    protected abstract void pageExportData(int pageNumber, int pageSize, Long sourceId, Long matchProjectId);

    /**
     * 获取导出实体的class
     * @author Mabowen
     * @date 2020/02/15 15:15
     * @param modelType
     * @return {@link Class<? extends BaseExcelEntity>}
     */
    protected Class<? extends BaseExcelEntity> getEntityClass(String modelType) {
        if (StringUtils.isNotBlank(modelType)) {
            if (this.modelType.equals(modelType)) {
                return BaseExcelEntity.class;
            }
        }
        return null;
    }

    /**
     * 获取导出的Excel的sheet名
     * @author Mabowen
     * @date 2020/02/15 15:16
     * @param modelType
     * @return {@link String}
     */
    public String getSheetName(String modelType) {
        if (StringUtils.isNotBlank(modelType)) {
            if (this.modelType.equals(modelType)) {
                return this.sheetName;
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取导出的excel表头
     * @author Mabowen
     * @date 2020/02/15 15:16
     * @param modelType
     * @return {@link String}
     */
    protected String getSheetTitle(String modelType) {
        if (StringUtils.isNotBlank(modelType)) {
            if (this.modelType.equals(modelType)) {
                return this.sheetTitle;
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * 动态生成表头---页面展示
     * @author Mabowen
     * @date 2020/02/15 15:16
     * @param sourceId
     * @return {@link List<Map<String,Object>>}
     */
    public List<Map<String,Object>> generateTableHead(Long sourceId) {
        return Collections.emptyList();
    }

    /**
     * 动态生成表头---excel表
     * @author Mabowen
     * @date 2020/02/17 10:36
     * @param
     * @return {@link List<ExcelExportEntity>}
     */
    public List<ExcelExportEntity> generateExcelColList(Long matchId) {
        return Collections.emptyList();
    }

    /**
     * 获取并设置excel导出动态表头的属性
     * @author Mabowen
     * @date 2020/02/17 11:21
     * @param entityProperties 实体属性
     * @return {@link ExcelExportEntity}
     */
    protected ExcelExportEntity getExportEntityProperties(ExportEntityProperties entityProperties) {
        if (StringUtils.isNotBlank(entityProperties.getKey()) && StringUtils.isNotBlank(entityProperties.getName())) {
            ExcelExportEntity colEntity = new ExcelExportEntity(entityProperties.getName(), entityProperties.getKey());
            if (entityProperties.getOrderNum() != null) {
                colEntity.setOrderNum(entityProperties.getOrderNum());
            }

            if (entityProperties.getWidth() != null) {
                colEntity.setWidth(entityProperties.getWidth());
            }

            if (entityProperties.getReplaces() != null && entityProperties.getReplaces().length > 0) {
                colEntity.setReplace(entityProperties.getReplaces());
            }

            if (entityProperties.getType() != null && entityProperties.getType() != 0) {
                colEntity.setType(entityProperties.getType());
            }

            if (StringUtils.isNotBlank(entityProperties.getNumFormat())) {
                colEntity.setNumFormat(entityProperties.getNumFormat());
            }

            if (StringUtils.isNotBlank(entityProperties.getDict())) {
                colEntity.setDict(entityProperties.getDict());
            }

            return colEntity;
        }
        return new ExcelExportEntity();
    }

    /**
     * 获取比赛获游学的名称
     * @author Mabowen
     * @date 2020/02/15 15:17
     * @param sourceId
     * @param type
     * @return {@link String}
     */
    protected String getMatchOrTravelName(Long sourceId, Integer type) {
        AssertUtil.assertNotNull(sourceId, "比赛/游学id不能为空");
        if (1 == type) {
            return "比赛";
        } else if (2 == type) {
            return "游学";
        }

        return StringUtils.EMPTY;
    }

    /**
     * 设置导出的excel的sheet名
     * @author Mabowen
     * @date 2020/02/15 15:17
     * @param params
     * @param modelType
     * @return
     */
    public void setSheetName(ExportParams params, String modelType) {
        String sheetName = getSheetName(modelType);
        if (StringUtils.isNotBlank(sheetName)) {
            params.setSheetName(sheetName);
        }
        String sheetTitle = getSheetTitle(modelType);
        if (StringUtils.isNotBlank(sheetTitle)) {
            params.setTitle(sheetTitle);
        }
    }

    /**
     * 设置导出数据的实体
     * @author Mabowen
     * @date 2020/02/15 15:17
     * @param map
     * @param modelType
     * @return
     */
    public void setExportEntity(Map<String, Object> map, String modelType) {
        Class<? extends BaseExcelEntity> entityClass = getEntityClass(modelType);
        if (entityClass != null) {
            map.put("entity", entityClass);
        }
    }

    /**
     * 获取分页数据
     * @author Mabowen
     * @date 2020/02/15 15:18
     * @param pageNumber
     * @param pageSize
     * @param sourceId
     * @return {@link PageBean<? extends BaseExcelEntity>}
     */
    public PageBean<? extends BaseExcelEntity> getPageData(int pageNumber, int pageSize, Long sourceId, Long matchProjectId) {
        AssertUtil.assertNotNull(sourceId, "比赛/游学id不能为空");
        pageExportData(pageNumber, pageSize, sourceId, matchProjectId);
        return pageQuery;
    }
}
