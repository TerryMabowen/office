package com.mbw.office.cloud.common.kit.validate;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.Result;
import com.baidu.unbiz.fluentvalidator.jsr303.HibernateSupportedValidator;
import com.mbw.office.cloud.common.constant.StringInfoConstants;

import javax.validation.Validator;
import java.util.Collection;
import java.util.List;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toSimple;

/**
 * @author Mabowen
 * @date 2020-07-20 11:09
 */
public class ValidatorKit {
    public static <T> Result validate(T t, Validator validator, Class<?>... groups) {
        return FluentValidator.checkAll(groups)
                .on(t, new HibernateSupportedValidator<T>().setHiberanteValidator(validator))
                .doValidate()
                .result(toSimple());
    }

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

    /**
     * 转化验证结果为字符串
     * @author Mabowen
     * @date 2020-08-20 16:13
     * @param errors 验证结果集合
     * @return {@link String}
     */
    public static String convertValidateErrors(List<String> errors) {
        if (CollUtil.isNotEmpty(errors)) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < errors.size(); i++) {
                if (i == (errors.size() - 1)) {
                    builder.append(errors.get(i));
                } else {
                    builder.append(errors.get(i)).append(StringInfoConstants.SEMICOLON);
                }
            }
            return builder.toString();
        }

        return StrUtil.EMPTY;
    }
}
