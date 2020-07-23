package com.mbw.office.test.jialian;

import com.mbw.office.demo.biz.jalian.JlBillService;
import com.mbw.office.demo.biz.jalian.model.JlBillDetail;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-17 11:36
 */
public class Parse2BeanTest {
    private String rootPath = "/Users/apple_22/Desktop/F100/钉钉中台-财务系统/每日对账单/";
    private JlBillService parseToBeanDemo = new JlBillService();

    @Test
    public void f1() throws IllegalAccessException {
        String path = rootPath + "51010718062610109608_20190605_5.txt";
        String filename = "field";
        String key = "column_eng_names";

        List<String> lineList = parseToBeanDemo.getLineList(path);

        String[] fields = parseToBeanDemo.getFields(filename, key);

        List<JlBillDetail> dataList = parseToBeanDemo.parse(lineList, fields);

        System.out.println(Arrays.toString(dataList.toArray()));
    }

    @Test
    public void f2() {
        String path = rootPath + "51010718062610109608_20190608_5.txt";
        String filename = "field";
        String key = "column_eng_names";

    }
}
