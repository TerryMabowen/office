package com.mbw.office.cloud.common.lang.binder;

import com.mbw.office.cloud.common.constant.StringInfoConstants;
import com.mbw.office.cloud.common.lang.exception.ServiceException;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * @author Mabowen
 * @date 2020-08-03 14:21
 */
public class CustomBigDecimalEditor extends PropertyEditorSupport {
    private static Pattern AMOUNT_PATTERN = Pattern.compile("^([-+])?([1-9]\\d{0,15}|0)([.]?|(\\.\\d{1,8})?)$");

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.hasText(text) && !StringInfoConstants.NULL.equalsIgnoreCase(text)) {
            if (AMOUNT_PATTERN.matcher(text.replaceAll(StringInfoConstants.ENGLISH_COMMA, StringInfoConstants.EMPTY_STR)).matches()) {
                setValue(new BigDecimal(text.replaceAll(StringInfoConstants.ENGLISH_COMMA, StringInfoConstants.EMPTY_STR)));
            } else {
                throw new ServiceException(String.format("输入的金额%s格式不正确", text));
            }
        } else {
            setValue(null);
        }
    }
}
