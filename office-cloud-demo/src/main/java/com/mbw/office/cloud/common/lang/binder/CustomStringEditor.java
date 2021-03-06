/**
 * 
 */
package com.mbw.office.cloud.common.lang.binder;

import com.mbw.office.cloud.common.constant.StringInfoConstants;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * @author Mabowen
 * @date 2020-08-03 14:21
 */
public class CustomStringEditor extends PropertyEditorSupport {
	
	@Override
	public void setAsText(@Nullable String text) throws IllegalArgumentException {
		if (!StringUtils.hasText(text) || StringInfoConstants.NULL.equalsIgnoreCase(text)) {
			// Treat empty String as null value.
			setValue(null);
		} else {
			setValue(text.trim());
		}
	}
}
