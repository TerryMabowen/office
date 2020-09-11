package com.mbw.office.demo.web.ctl;

import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.common.lang.response.ResponseResults;
import com.mbw.office.common.util.excel.easypoi.ExportExcelUtil;
import com.mbw.office.demo.biz.easypoi.EasypoiExportExcelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Mabowen
 * @date 2020-07-17 15:02
 */
@Slf4j
@RestController
@RequestMapping("/index")
public class IndexDataCtl {
    @Autowired
    private EasypoiExportExcelService easypoiExportExcelService;

    @GetMapping("/test")
    public ResponseResults easyPoiExportExcel() {
        try {
            return ResponseResults.newSuccess();
        } catch (Exception e) {
            return ResponseResults.newFailed(e.getMessage());
        }
    }

    @GetMapping("/easypoi/export/simple/excel")
    public void f1(HttpServletResponse response) {
        try {
            String filename = "课程表";
            Workbook workbook = easypoiExportExcelService.getWorkbook();
            if (workbook != null) {
                ExportExcelUtil.exportExcel(workbook, filename, response);
            } else {
                throw new ServiceException("Workbook cannot be null");
            }
        } catch (Exception e) {
            log.error("Easypoi导出简单Excel失败：" + e.getMessage(), e);
        }
    }
}
