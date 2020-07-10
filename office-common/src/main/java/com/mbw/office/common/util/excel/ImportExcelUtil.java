package com.mbw.office.common.util.excel;

import cn.hutool.core.util.StrUtil;
import com.mbw.office.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 导入excel工具
 *
 * @author Mabowen
 * @date 2020-07-10 13:31
 */
@Slf4j
public class ImportExcelUtil {
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
                    String[] cells = new String[row.getPhysicalNumberOfCells()];
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
        if (StrUtil.isNotBlank(fileName)) {
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
                log.error(e.getMessage(), e);
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
        if (cell.getCellType().getCode() == CellType.NUMERIC.getCode()) {
            cell.setCellType(CellType.STRING);
        }

        //判断数据的类型
        int code = cell.getCellType().getCode();
        if (CellType.NUMERIC.getCode() == code) {
            //数字
            cellValue = String.valueOf(cell.getNumericCellValue());
        } else if (CellType.STRING.getCode() == code) {
            //字符串
            cellValue = String.valueOf(cell.getStringCellValue());
        } else if (CellType.BOOLEAN.getCode() == code) {
            //Boolean
            cellValue = String.valueOf(cell.getBooleanCellValue());
        } else if (CellType.FORMULA.getCode() == code) {
            //公式
            cellValue = String.valueOf(cell.getCellFormula());
        } else if (CellType.BLANK.getCode() == code) {
            //空值
            cellValue = "";
        } else if (CellType.ERROR.getCode() == code) {
            //故障
            cellValue = "非法字符";
        } else {
            cellValue = "未知类型";
        }

        return cellValue;
    }
}
