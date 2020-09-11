package com.mbw.office.common.util.excel.apachepoi;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * apache poi 导入excel工具类
 *
 * @author Mabowen
 * @date 2020-07-14 14:19
 */
@Slf4j
public class ImportExcelUtil {
    /**
     * 获取单元格的值
     *
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        String cellValue = "";
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

    /**
     * 读取excel
     * 注意：默认读取的数据是在第一个sheet中的，暂不支持多个sheet的读取
     *
     * @param ins        输入流
     * @param headRowNum 表头所在行数
     * @return 返回excel中的字符串数据
     * @throws Exception
     */
    public static List<List<String>> readFile(InputStream ins, int headRowNum) throws Exception {
        Workbook workbook = WorkbookFactory.create(ins);
        Sheet sheet = workbook.getSheetAt(0);
        // 获取excel总行数
        int rownum = sheet.getLastRowNum();
        List<List<String>> result = new ArrayList<>();
        log.info("excel 总行数：{}", rownum);
        // 获取表头那一行的数据长度
        short allColumnNum = sheet.getRow(headRowNum).getLastCellNum();
        for (int i = 0; i <= rownum; i++) {
            Row row = sheet.getRow(i);
            if (row == null || row.getPhysicalNumberOfCells() == 0) {
                log.info("第{}行数据为空.", i);
                continue;
            }
            List<String> oneRecord = new ArrayList<>();
            for (int j = 0; j < allColumnNum; j++) {
                Cell cell = row.getCell(j);
                if (cell == null) {
                    oneRecord.add("");
                    continue;
                }
                String cellValue = getCellValue(cell);
                oneRecord.add(cellValue);
            }
            result.add(oneRecord);
        }
        log.info("读取文件完成!");
        return result;
    }

    /**
     * 获取excel对应的实体列表
     *
     * @param file        导入的excel文件
     * @param headRowNum  表头行数，记住：从0开始
     * @param cls         实体类
     * @param parseIndex  从哪一行开始解析
     * @param excludeAttr 哪些属性不映射
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> getListFromExcel(MultipartFile file, int headRowNum, Class<T> cls, int parseIndex, String... excludeAttr) throws Exception {
        // try(流)是Java7中的try-with-resource语法。当try语句块运行结束时，InputStream 会被自动关闭，只要实现了AutoCloseable接口的类都可以写在try括号里
        try (InputStream ins = file.getInputStream()) {
            List<List<String>> strLists = readFile(ins, headRowNum);
            return getEntityList(strLists, cls, parseIndex, excludeAttr);
        }
    }

    /**
     * 将读取到的excel数据存入List<cls>列表中
     *
     * @param list        字符串
     * @param cls         解析的类对象
     * @param parseIndex  开始解析的行数
     * @param excludeAttr 哪些属性不解析
     * @return 对应的对象列表
     * @throws Exception
     */
    public static <T> List<T> getEntityList(List<List<String>> list, Class<T> cls, int parseIndex, String... excludeAttr) throws Exception {
        List<T> result = new ArrayList<>();
        for (int i = parseIndex; i < list.size(); i++) {
            List<String> oneRecord = list.get(i);
            T instance = cls.newInstance();
            for (int j = 0, tmp = 0; j < oneRecord.size(); j++) {
                String cellValue = oneRecord.get(j);
                if (StrUtil.isBlank(cellValue)) {
                    continue;
                }
                Field[] fields = cls.getDeclaredFields();
                Field field = fields[j];
                for (String s : excludeAttr) {
                    if (field.getName().equals(s)) {
                        tmp++;
                        break;
                    }
                }
                field = fields[j + tmp];
                Object cellVal = field.getType().equals(Date.class) ? new Date(Long.parseLong(cellValue)) : cellValue;
                ReflectUtil.setFieldValue(instance, field.getName(), cellVal);
            }
            result.add(instance);
        }
        return result;
    }

    /**
     * 获取excel表中对应的实体数据
     *
     * @param ins
     * @param headRowNum
     * @param cls
     * @param parseIndex
     * @param excludeAttr
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> getListFromExcel(InputStream ins, int headRowNum, Class<T> cls, int parseIndex, String... excludeAttr) throws Exception {
        List<List<String>> strLists = readFile(ins, headRowNum);
        return getEntityList(strLists, cls, parseIndex, excludeAttr);
    }
}
