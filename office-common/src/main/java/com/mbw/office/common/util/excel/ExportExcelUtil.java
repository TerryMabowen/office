package com.mbw.office.common.util.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.mbw.office.common.exception.ServiceException;
import com.mbw.office.common.util.validate.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 导出excel工具
 *
 * @author Mabowen
 * @date 2020-07-10 13:30
 */
@Slf4j
public class ExportExcelUtil {
    /**
     * 默认导出excel方法
     *
     * @param params    表头及sheet页
     * @param pojoClass 实体类
     * @param list      数据集合
     * @param fileName
     * @param response
     * @return
     * @author Mabowen
     * @date 17:37 2020-04-18
     */
    public static void defaultExportExcel(ExportParams params, Class<?> pojoClass, List<?> list, String fileName, HttpServletResponse response) {
        try {
            Workbook workbook = ExcelExportUtil.exportExcel(params, pojoClass, list);
            if (null != workbook) {
                exportExcel(workbook, fileName, response);
            }
        } catch (Exception e) {
            throw new ServiceException("导出excel异常：" + e.getMessage(), e);
        }

    }

    /**
     * 多sheet页导出
     *
     * @param list
     * @param fileName
     * @param response
     * @author Mabowen
     * @date 17:26 2020-01-15
     */
    public static void sheetsExportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        try {
            Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.XSSF);
            if (null != workbook) {
                exportExcel(workbook, fileName, response);
            }
        } catch (Exception e) {
            throw new ServiceException("导出excel异常：" + e.getMessage(), e);
        }
    }

    /**
     * 导出动态表头的excel表格
     *
     * @param params   导出参数
     * @param colList  表头list
     * @param dataList 数据list
     * @param fileName
     * @param response
     * @return
     * @author Mabowen
     * @date 2020/02/18 16:07
     */
    public static void dynamicTableHeadExportExcel(ExportParams params, List<ExcelExportEntity> colList, List<Map<String, Object>> dataList, String fileName, HttpServletResponse response) {
        try {
            Workbook workbook = ExcelExportUtil.exportExcel(params, colList, dataList);
            if (null != workbook) {
                exportExcel(workbook, fileName, response);
            }
        } catch (Exception e) {
            throw new ServiceException("导出动态表头excel异常：" + e.getMessage(), e);
        }
    }

    /**
     * 导出大数据量excel
     *
     * @param
     * @return
     * @author Mabowen
     * @date 15:51 2020-04-18
     */
    public static void exportBigDataExcel(Workbook workbook, String fileName, HttpServletResponse response) {
        try {
            if (workbook != null) {
                exportExcel(workbook, fileName, response);
            }
        } catch (Exception e) {
            throw new ServiceException("导出大数据量Excel异常：" + e.getMessage(), e);
        }
    }

    /**
     * 导出工作薄excel表格
     *
     * @param workbook 工作薄
     * @param fileName excel文件名
     * @param response
     * @author Mabowen
     * @date 19:28 2020-01-04
     */
    private static void exportExcel(Workbook workbook, String fileName, HttpServletResponse response) {
        //设置响应头
        setResponseHeader(fileName, response);
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            workbook.write(os);
            os.flush();
        } catch (Exception e) {
            throw new ServiceException(String.format("导出" + fileName + "失败, 失败原因: %s", e.getMessage()), e);
        } finally {
            if (null != workbook) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 设置响应头
     *
     * @param fileName
     * @param response
     * @author Mabowen
     * @date 15:10 2020-05-25
     */
    private static void setResponseHeader(String fileName, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");
        response.addHeader("Cache-Control", "no-cache");
        response.setHeader("Content-Disposition", "attachment;filename=" + encodeFileName(fileName) + ".xlsx");
    }

    /**
     * 文件名转码
     *
     * @param fileName
     * @return
     * @author Mabowen
     * @date 15:16 2020-01-15
     */
    private static String encodeFileName(String fileName) {
        AssertUtil.assertNotEmpty(fileName, "需要转码的文件名不能为空");

        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            return fileName;
        } catch (UnsupportedEncodingException e) {
            throw new ServiceException(String.format("转码失败，失败原因: %s", e.getMessage()), e);
        }
    }
}
