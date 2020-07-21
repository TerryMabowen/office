package com.mbw.office.common.util.validate;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.Result;
import com.baidu.unbiz.fluentvalidator.jsr303.HibernateSupportedValidator;
import lombok.Getter;

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
@Deprecated
public class ValidatorFactory {
    private static ValidatorFactory single;

    @Getter
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static ValidatorFactory getInstance() {
        if (single != null) {
            return single;
        }

        synchronized (ValidatorFactory.class) {
            if (single == null) {
                single = new ValidatorFactory();
            }
        }

        return single;
    }

    public <T> Result validateObject(T t) {
        return FluentValidator
                .checkAll()
                .on(t, new HibernateSupportedValidator<T>().setHiberanteValidator(validator))
                .doValidate()
                .result(toSimple());
    }

    public <T> Result validateObject(T t, com.baidu.unbiz.fluentvalidator.Validator<T> v) {
        return FluentValidator
                .checkAll()
                .on(t, v)
                .doValidate()
                .result(toSimple());
    }

    public <T> Result validateCollection(Collection<T> t) {
        return FluentValidator
                .checkAll()
                .onEach(t, new HibernateSupportedValidator<T>().setHiberanteValidator(validator))
                .doValidate()
                .result(toSimple());
    }

    public <T> Result validateCollection(Collection<T> t, com.baidu.unbiz.fluentvalidator.Validator<T> v) {
        return FluentValidator
                .checkAll()
                .onEach(t, v)
                .doValidate()
                .result(toSimple());
    }
}
