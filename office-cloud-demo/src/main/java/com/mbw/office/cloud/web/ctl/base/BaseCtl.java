package com.mbw.office.cloud.web.ctl.base;

import com.mbw.office.cloud.common.lang.binder.CustomBigDecimalEditor;
import com.mbw.office.cloud.common.lang.binder.CustomIntegerEditor;
import com.mbw.office.cloud.common.lang.binder.CustomLongEditor;
import com.mbw.office.cloud.common.lang.binder.CustomStringEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Mabowen
 * @date 2020-08-04 10:16
 */
public class BaseCtl {
    @ModelAttribute("module")
    public String module() {
        return "";
    }


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
}
