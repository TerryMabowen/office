package com.mbw.office.common.lang.excel;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.export.styler.ExcelExportStylerDefaultImpl;
import org.apache.poi.ss.usermodel.*;

/**
 * 自定义导出excel样式
 *
 * @author Mabowen
 * @date 2020-08-14 14:54
 */
public class CustomExportExcelStyler extends ExcelExportStylerDefaultImpl {
    public static final String EXCEL_COVERT_MONEY = "amount";
    private CellStyle amountCellStyle;

    public CustomExportExcelStyler(Workbook workbook) {
        super(workbook);
        createNumberCellStyler();
    }

    @Override
    public CellStyle getStyles(boolean noneStyler, ExcelExportEntity entity) {
        // 判断是否需要格式化
        if (entity != null && EXCEL_COVERT_MONEY.equals(entity.getDict())) {
            return amountCellStyle;
        }

        return super.getStyles(noneStyler, entity);
    }

    private void createNumberCellStyler() {
        amountCellStyle = workbook.createCellStyle();
        amountCellStyle.setAlignment(HorizontalAlignment.CENTER);
        amountCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        amountCellStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("#,##0.00"));
        amountCellStyle.setWrapText(true);
    }
}
