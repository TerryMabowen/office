package com.mbw.office.common.util.validate;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.Result;
import com.baidu.unbiz.fluentvalidator.jsr303.HibernateSupportedValidator;
import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toSimple;

/**
 * @author Mabowen
 * @date 2020-07-20 11:09
 */
@Component
public class ValidatorUtil {
//    @Autowired
//    private Validator validator;
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> Result validateObject(T t) {
        return FluentValidator
                .checkAll()
                .on(t, new HibernateSupportedValidator<T>().setHiberanteValidator(validator))
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

    public static <T> Result validateCollection(Collection<T> t) {
        return FluentValidator
                .checkAll()
                .onEach(t, new HibernateSupportedValidator<T>().setHiberanteValidator(validator))
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
