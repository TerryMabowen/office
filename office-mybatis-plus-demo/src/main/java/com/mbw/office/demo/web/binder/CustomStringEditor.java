/**
 *
 */
package com.mbw.office.demo.web.binder;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * 自定义字符串编辑器---解决前端传递过来的""字符串和" "以及" sss "
 * "" 和 " " --> null
 * " sss " --> "sss"
 * @author Mabowen
 * @date 2020-07-03 10:08
 */
public class CustomStringEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(@Nullable String text) throws IllegalArgumentException {
        if (!StringUtils.hasText(text)) {
            setValue(null);
        } else {
            setValue(text.trim());
        }
    }
}
