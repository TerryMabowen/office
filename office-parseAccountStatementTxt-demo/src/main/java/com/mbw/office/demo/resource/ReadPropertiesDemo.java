package com.mbw.office.demo.resource;

import java.util.ResourceBundle;

/**
 * @author Mabowen
 * @date 2020-07-17 10:41
 */
public class ReadPropertiesDemo {
    public ResourceBundle readProperties(String filename) {
        return ResourceBundle.getBundle(filename);
    }

    public String getValueByKey(ResourceBundle bundle, String key) {
        return bundle.getString(key).trim();
    }

    public String[] getFields(String field) {
        return field.split("\\|");
    }
}
