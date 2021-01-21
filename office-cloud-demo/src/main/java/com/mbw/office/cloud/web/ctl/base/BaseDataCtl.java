package com.mbw.office.cloud.web.ctl.base;

import com.baidu.unbiz.fluentvalidator.Result;
import com.mbw.office.cloud.common.kit.validate.ValidatorKit;
import com.mbw.office.cloud.common.lang.binder.CustomBigDecimalEditor;
import com.mbw.office.cloud.common.lang.binder.CustomIntegerEditor;
import com.mbw.office.cloud.common.lang.binder.CustomLongEditor;
import com.mbw.office.cloud.common.lang.binder.CustomStringEditor;
import com.mbw.office.cloud.common.lang.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Mabowen
 * @date 2020-08-04 10:16
 */
public class BaseDataCtl {
    @Autowired
    private Validator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Integer.class, new CustomIntegerEditor());
        binder.registerCustomEditor(Long.class, new CustomLongEditor());
        binder.registerCustomEditor(String.class, new CustomStringEditor());
        binder.registerCustomEditor(BigDecimal.class, new CustomBigDecimalEditor());
    }

    protected <T> Result validate(T t) {
        return ValidatorKit.validateObject(t, validator);
    }

    protected <T> Result validateByGroups(T t, Class<?>... groups) {
        return ValidatorKit.validate(t, validator, groups);
    }

    protected <T> void validateForExc(T t) {
        Result result = ValidatorKit.validateObject(t, validator);
        if (!result.isSuccess()) {
            throw new ServiceException("验证失败：" + ValidatorKit.convertValidateErrors(result.getErrors()));
        }
    }

    protected <T> void validateByGroupsForExc(T t, Class<?>... groups) {
        Result result = ValidatorKit.validate(t, validator, groups);
        if (!result.isSuccess()) {
            throw new ServiceException("验证失败：" + ValidatorKit.convertValidateErrors(result.getErrors()));
        }
    }
}
