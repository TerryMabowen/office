package com.mbw.office.common.util.excel.apachepoi;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * apache poi 导出excel工具类
 *
 * @author Mabowen
 * @date 2020-07-14 14:20
 */
@Slf4j
public class ExportExcelUtil {
    private static ThreadLocal<List<ExportExcelUtil.RectangleArea>> mergeAreaList = new ThreadLocal<>();

    public static List<ExportExcelUtil.RectangleArea> getMergeAreaList() {
        return mergeAreaList.get();
    }

    public static void setMergeAreaList(List<ExportExcelUtil.RectangleArea> list) {
        mergeAreaList.set(list);
    }

    /**
     * 导出excel——某些属性不展示
     *
     * @param title
     * @param headers
     * @param list
     * @param response
     * @param useXSSF
     * @param sheetName
     * @param excludeAttr
     */
    public static void exportExcel(String title, String[] headers, List<?> list, HttpServletResponse response, boolean useXSSF, String sheetName, String... excludeAttr) {
        try {
            Workbook workbook = getExcelWorkBook(headers, list, useXSSF, sheetName, excludeAttr);
            addMergeArea(workbook, 0, getMergeAreaList());
            writeToResp(title, workbook, response, useXSSF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出excel ——展示某些属性
     *
     * @param title
     * @param headers
     * @param list
     * @param response
     * @param useXSSF
     * @param sheetName
     * @param includeAttr
     */
    public static void exportExcel(String title, String[] headers, List<?> list, HttpServletResponse response, boolean useXSSF, String sheetName, List<String> includeAttr) {
        try {
            Workbook workbook = getExcelWorkBook(headers, list, useXSSF, sheetName, includeAttr);
            addMergeArea(workbook, 0, getMergeAreaList());
            writeToResp(title, workbook, response, useXSSF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给出导出字符串的情况下的导出方法
     *
     * @param title
     * @param list
     * @param response
     */
    public static void exportExcel(String title, List<List<String>> list, boolean useXSSF, HttpServletResponse response) {
        try {
            Workbook workbook = getExcelWorkBook(list, useXSSF);
            writeToResp(title, workbook, response, useXSSF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给出字符串的情况下获取WorkBook
     *
     * @param list
     * @return
     */
    public static Workbook getExcelWorkBook(List<List<String>> list, boolean useXSSF) {
        Workbook workbook = getWorkbook(useXSSF);
        // 创建sheet, 名字默认为sheet0
        Sheet sheet = workbook.createSheet("sheet0");
        // 确定行数
        for (int i = 0; i < list.size(); i++) {
            // 创建一行
            Row dataRow = sheet.createRow(i);
            List<String> t = list.get(i);
            // 为每个单元格设值
            for (int j = 0; j < t.size(); j++) {
                Cell dataCell = dataRow.createCell(j);
                // 设置固定宽度, 每个单元格可存放16个字符
                setCellValue(workbook, dataCell, t.get(j));
            }
        }
        return workbook;
    }

    /**
     * 展示某些属性的方法
     *
     * @param headers
     * @param list
     * @param useXSSF     是否使用2007
     * @param sheetName
     * @param includeAttr
     * @return
     */
    private static Workbook getExcelWorkBook(String[] headers, List<?> list, boolean useXSSF, String sheetName, List<String> includeAttr) {
        Workbook workbook = getWorkbook(useXSSF);
        // 创建sheet
        Sheet sheet = workbook.createSheet(sheetName);
        // 创建表头
        createHeader(headers, sheet, useXSSF);
        // 确定行数
        for (int i = 0; i < list.size(); i++) {
            // 创建一行
            Row dataRow = sheet.createRow(i + 1);
            Object t = list.get(i);
            // 为每个单元格设值
            for (int j = 0; j < includeAttr.size(); j++) {
                Cell dataCell = dataRow.createCell(j);
                // 设置固定宽度, 每个单元格可存放16个字符
                sheet.setColumnWidth(j, 16 * 256);
                String fieldName = includeAttr.get(j);
                setCellValue(workbook, dataCell, ReflectUtil.getFieldValue(t, fieldName));
            }
        }
        return workbook;
    }

    /**
     * 导出excel所需的workbook
     *
     * @param headers
     * @param list
     * @param sheetName
     * @param excludeAttr
     * @return
     */
    private static Workbook getExcelWorkBook(String[] headers, List<?> list, boolean useXSSF, String sheetName, String... excludeAttr) {
        Workbook workbook = getWorkbook(useXSSF);
        // 创建sheet
        Sheet sheet = workbook.createSheet(sheetName);
        // 创建表头
        createHeader(headers, sheet, useXSSF);
        // 确定行数
        for (int i = 0; i < list.size(); i++) {
            // 创建一行
            Row dataRow = sheet.createRow(i + 1);
            Object t = list.get(i);

            // 反射拿到对象里面的值
            Class<?> cls = t.getClass();
            Field[] fields = cls.getDeclaredFields();

            // k用来解决某些字段并不在excel中展示带来的数据和列名不匹配问题
            for (int j = 0, k = 0; j < fields.length - 1; j++) {
                Field field = fields[j];
                String fieldName = field.getName();
                // flag标志代表是否不展示该项的值
                boolean flag = false;
                for (String s : excludeAttr) {
                    if (s.equals(fieldName)) {
                        // 解决生成数据不匹配问题
                        k++;
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    continue;
                }

                Cell dataCell = dataRow.createCell(j - k);
                // 设置固定宽度, 每个单元格可存放16个字符
                sheet.setColumnWidth(j - k, 8 * 2 * 256);
                setCellValue(workbook, dataCell, ReflectUtil.getFieldValue(t, fieldName));
            }
        }
        return workbook;
    }

    /**
     * 为单元格设置值
     *
     * @param workbook
     * @param dataCell
     * @param value
     */
    public static void setCellValue(Workbook workbook, Cell dataCell, Object value) {
        // 单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
        if (value instanceof Date) {
//            DateFormat sdf = DateUtil.secondFormat.get();
//            String zero = "00:00:00";
//            if (zero.equals(sdf.format(value).split(" ")[1])) {
//                sdf = DateUtil.dayFormat.get();
//            }
//            dataCell.setCellStyle(cellStyle);
//            dataCell.setCellValue(sdf.format(value));
        } else if (value instanceof Number) {
            DataFormat df = workbook.createDataFormat();
            cellStyle.setDataFormat(df.getFormat("#,#0"));
            dataCell.setCellStyle(cellStyle);
            dataCell.setCellValue(Double.parseDouble(String.valueOf(value)));
        } else if (value == null) {
            dataCell.setCellStyle(cellStyle);
            dataCell.setCellValue("");
        } else {
            dataCell.setCellStyle(cellStyle);
            dataCell.setCellValue(value.toString());
        }
    }

    /**
     * 创建表头
     *
     * @param headers
     * @param sheet
     */
    private static void createHeader(String[] headers, Sheet sheet, boolean useXSSF) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell headerRowCell = headerRow.createCell(i);
            RichTextString text = useXSSF ? new XSSFRichTextString(headers[i]) : new HSSFRichTextString(headers[i]);
            headerRowCell.setCellValue(text);
        }
    }


    /**
     * 将excel流写入response
     *
     * @param title
     * @param workbook
     * @param response
     * @param useXSSF
     */
    private static void writeToResp(String title, Workbook workbook, HttpServletResponse response, boolean useXSSF) {
        ServletOutputStream outputStream = null;
        try {
            log.info("" + workbook);
            // 解决浏览器及中文乱码问题https://tools.ietf.org/html/rfc2231
            response.setHeader("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(title, "UTF-8") + (useXSSF ? ".xlsx" : ".xls"));
            response.setContentType("application/msexcel");
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流资源
            IoUtil.close(outputStream);
            IoUtil.close(workbook);
        }
    }

    /**
     * 合并单元格并水平垂直居中实现
     *
     * @param areas
     */
    public static void addMergeArea(Workbook workbook, int sheetIndex, List<ExportExcelUtil.RectangleArea> areas) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            log.info("Thread: {}, threadLocal is: {}.", Thread.currentThread().getName(), String.valueOf(getMergeAreaList()));
            if (areas != null && areas.size() > 0) {
                for (ExportExcelUtil.RectangleArea area : areas) {
                    workbook.getSheetAt(sheetIndex)
                            .addMergedRegion(new CellRangeAddress(area.getFirstRow(), area.getLastRow(), area.getFirstCol(), area.getLastCol()));
                    // 合并单元格时默认居中（水平、垂直居中）
                    CellStyle cellStyle = workbook.createCellStyle();
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                    workbook.getSheetAt(sheetIndex).getRow(area.getFirstRow()).getCell(area.getFirstCol()).setCellStyle(cellStyle);
                }
                mergeAreaList.set(null);
            }
        } finally {
            lock.unlock();
        }
    }

    private static Workbook getWorkbook(boolean useXSSF) {
        return useXSSF ? new XSSFWorkbook() : new HSSFWorkbook();
    }

    public static class RectangleArea {
        private int firstRow, lastRow, firstCol, lastCol;

        public RectangleArea(int firstRow, int lastRow, int firstCol, int lastCol) {
            this.firstRow = firstRow;
            this.lastRow = lastRow;
            this.firstCol = firstCol;
            this.lastCol = lastCol;
        }

        public int getFirstRow() {
            return firstRow;
        }

        public int getLastRow() {
            return lastRow;
        }

        public int getFirstCol() {
            return firstCol;
        }

        public int getLastCol() {
            return lastCol;
        }
    }
}
