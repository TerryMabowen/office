package com.mbw.office.test.jialian.resource;

import com.mbw.office.demo.jalian.resource.ReadPropertiesDemo;
import org.junit.Test;

import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-17 11:33
 */
public class ReadPropertiesTest {
    private ReadPropertiesDemo readPropertiesDemo = new ReadPropertiesDemo();

    @Test
    public void f1() {
        String filename = "field";
        String key = "column_eng_names";
        ResourceBundle bundle = readPropertiesDemo.readProperties(filename);
        String valueByKey = readPropertiesDemo.getValueByKey(bundle, key);
        String[] fields = readPropertiesDemo.getFields(valueByKey);
        System.out.println(Arrays.toString(fields));
    }
}
