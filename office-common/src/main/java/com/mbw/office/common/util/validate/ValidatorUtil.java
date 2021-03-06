package com.mbw.office.common.util.validate;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.Result;
import com.baidu.unbiz.fluentvalidator.jsr303.HibernateSupportedValidator;

import javax.validation.Validator;
import java.util.Collection;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toSimple;

/**
 * @author Mabowen
 * @date 2020-07-20 11:09
 */
public class ValidatorUtil {

    public static <T> Result validateObject(T t, Validator validator) {
        return FluentValidator
                .checkAll()
                .on(t, new HibernateSupportedValidator<T>().setHiberanteValidator(validator))
                .doValidate()
                .result(toSimple());
    }

    public static <T> Result validateCollection(Collection<T> t, Validator validator) {
        return FluentValidator
                .checkAll()
                .onEach(t, new HibernateSupportedValidator<T>().setHiberanteValidator(validator))
                .doValidate()
                .result(toSimple());
    }

    public static <T> Result validateObject(T t, com.baidu.unbiz.fluentvalidator.Validator<T> v) {
        return FluentValidator
                .checkAll()
                .on(t, v)
                .doValidate()
                .result(toSimple());
    }

    public static <T> Result validateCollection(Collection<T> t, com.baidu.unbiz.fluentvalidator.Validator<T> v) {
        return FluentValidator
                .checkAll()
                .onEach(t, v)
                .doValidate()
                .result(toSimple());
    }
}
