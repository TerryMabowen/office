package com.mbw.office.common.util.excel.easypoi;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.util.StrUtil;
import com.mbw.office.common.lang.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 导入excel工具
 *
 * @author Mabowen
 * @date 2020-07-10 13:31
 */
@Slf4j
public class ImportExcelUtil {
    /**
     * 功能描述：根据接收的Excel文件来导入Excel,并封装成实体类
     *
     * @param file       上传的文件
     * @param titleRows  表标题的行数
     * @param headerRows 表头行数
     * @param pojoClass  Excel实体类
     * @return
     * @author Mabowen
     * @date 17:37 2020-04-18
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        if (titleRows != null) {
            params.setTitleRows(titleRows);
        }
        if (headerRows != null) {
            params.setHeadRows(headerRows);
        }

        List<T> list = new ArrayList<>();
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new ServiceException("excel文件数据为空, " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ServiceException("导入excel数据失败，" + e.getMessage(), e);
        }
        return list;
    }

    public static <T> List<T> importExcelData(MultipartFile file) {
        if (file == null) {
            return null;
        }
        Workbook workBook = getWorkBook(file);
        if (workBook == null) {
            List<String[]> strings = readExcelGetList(workBook);
            //TODO
        }

        return Collections.emptyList();
    }

    /**
     * 解析excel
     *
     * @param workbook
     * @return
     */
    public static List<String[]> readExcelGetList(Workbook workbook) {
        List<String[]> list = new ArrayList<String[]>();
        if (workbook != null) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第一行的所有行
                for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数
                    int lastCellNum = row.getPhysicalNumberOfCells();
                    String[] cells = new String[lastCellNum];
                    //循环当前行
                    for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
                    }
                    list.add(cells);
                }
            }
        }
        return list;
    }

    /**
     * 判断文件版本
     *
     * @param file
     * @return
     */
    public static Workbook getWorkBook(MultipartFile file) {
        String xls = "xls";
        String xlsx = "xlsx";
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        //获得文件名
        String fileName = file.getOriginalFilename();
        if (fileName != null && StrUtil.isNotBlank(fileName)) {
            try {
                //获取excel文件的io流
                InputStream is = file.getInputStream();
                //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
                if (fileName.endsWith(xls)) {
                    //2003
                    workbook = new HSSFWorkbook(is);
                } else if (fileName.endsWith(xlsx)) {
                    //2007
                    workbook = new XSSFWorkbook(is);
                }
            } catch (IOException e) {
                throw new ServiceException(e.getMessage(), e);
            }
        }
        return workbook;
    }

    /**
     * 把所有类型转换成字符串  转换
     *
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if (cell.getCachedFormulaResultType() == CellType.NUMERIC) {
            cell.setCellType(CellType.STRING);
        }

        //判断数据的类型
        switch (cell.getCachedFormulaResultType()) {
            case STRING:
                //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case NUMERIC:
                //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case BOOLEAN:
                //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK:
                //空值
                cellValue = "";
                break;
            case ERROR:
                //故障
                cellValue = "非法字符";
                break;
            case _NONE:
                cellValue = "未知类型";
                break;
            default:
                cellValue = "未知类型";
                break;
        }

        return cellValue;
    }
}
