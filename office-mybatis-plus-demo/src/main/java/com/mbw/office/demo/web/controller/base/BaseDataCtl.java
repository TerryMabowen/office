package com.mbw.office.demo.web.controller.base;

import com.baidu.unbiz.fluentvalidator.Result;
import com.mbw.office.common.util.validate.ValidatorUtil;
import com.mbw.office.demo.web.binder.CustomStringEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @author Mabowen
 * @date 2020-07-01 18:37
 */
public class BaseDataCtl {

    /**
     * 通过binder处理请求参数
     * @param binder 连接器
     * @date 2020-07-01 18:37
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new CustomStringEditor());
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    /**
     * 参数校验
     * @param t 参数
     * @date 2020-07-01 18:37
     */
    protected <T> Result validate(T t) {
        return ValidatorUtil.getInstance()
                .validateObject(t);
    }
}
