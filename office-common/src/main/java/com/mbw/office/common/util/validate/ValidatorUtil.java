package com.mbw.office.common.util.validate;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.Result;
import com.baidu.unbiz.fluentvalidator.jsr303.HibernateSupportedValidator;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toSimple;

/**
 * FluentValidator验证器
 *
 * @author Mabowen
 * @date 2020-07-03 15:14
 */
public class ValidatorUtil {
    private static ValidatorUtil single;

    private Validator validator;

    public static ValidatorUtil getInstance() {
        if (single != null) {
            return single;
        }

        synchronized (ValidatorUtil.class) {
            if (single == null) {
                single = new ValidatorUtil();
            }

            single.init();
        }

        return single;
    }

    public <T> Result validate(T t) {
        return FluentValidator
                .checkAll()
                .on(t, new HibernateSupportedValidator<T>().setHiberanteValidator(validator))
                .doValidate()
                .result(toSimple());
    }

    public <T> Result validateList(Collection<T> t, com.baidu.unbiz.fluentvalidator.Validator<T> v) {
        return FluentValidator
                .checkAll()
                .onEach(t, v)
                .doValidate()
                .result(toSimple());
    }

    private void init() {
        validator = Validation.buildDefaultValidatorFactory()
                .getValidator();
    }
}
