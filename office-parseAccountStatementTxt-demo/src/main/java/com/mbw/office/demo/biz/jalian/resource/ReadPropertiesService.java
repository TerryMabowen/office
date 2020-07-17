package com.mbw.office.demo.biz.jalian.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;

/**
 * @author Mabowen
 * @date 2020-07-17 10:41
 */
@Service
public class ReadPropertiesService {
    @Value("${jialian.column.englist.fields}")
    private String jlFields;

    public ResourceBundle readProperties(String filename) {
        return ResourceBundle.getBundle(filename);
    }

    public String getValueByKey(ResourceBundle bundle, String key) {
        return bundle.getString(key).trim();
    }

    public String[] getFields(String field) {
        return field.split("\\|");
    }

    public String[] getFields() {
        return jlFields.split("\\|");
    }
}
