package com.mbw.office.test.jialian.file;

import com.mbw.office.demo.biz.jalian.file.ReadTxtService;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-17 10:29
 */
public class ReadTxtTest {
    private String rootPath = "/Users/apple_22/Desktop/F100/钉钉中台-财务系统/每日对账单/";
    private ReadTxtService readTxtDemo = new ReadTxtService();
    @Test
    public void f1() {
        String path = rootPath + "51010718062610109608_20190605_5.txt";
        try {
            File txtFile = readTxtDemo.getTxtFile(path);
            List<String> lineList = readTxtDemo.readTxt(txtFile);
            readTxtDemo.parseLine(lineList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void f2() {
        String path = rootPath + "51010718062610109608_20190606_5.txt";
        try {
            File txtFile = readTxtDemo.getTxtFile(path);
            List<String> lineList = readTxtDemo.readTxt(txtFile);
            readTxtDemo.parseLine(lineList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
