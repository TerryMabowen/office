package com.mbw.office.demo.biz.easypoi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.demo.biz.sourcedata.SourceDataService;
import com.mbw.office.demo.model.Course;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Mabowen
 * @date 2020-09-11 17:37
 */
@Slf4j
@Service
public class EasypoiExportExcelService {
    @Autowired
    private SourceDataService sourceDataService;

    public Workbook getWorkbook() {
        try {
            List<Map<String, Object>> list = Lists.newArrayList();
            Map<String, Object> map = Maps.newHashMap();
            ExportParams params = new ExportParams("课程表", "课程表");
            params.setType(ExcelType.XSSF);
            map.put("title", params);
            map.put("entity", Course.class);
            List<Course> courses = sourceDataService.listCourses();
            map.put("data", courses);
            list.add(map);
            return ExcelExportUtil.exportExcel(list, ExcelType.XSSF);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
