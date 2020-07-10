package com.mbw.office;

import cn.hutool.core.io.file.FileReader;
import org.junit.Test;

import java.util.List;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-01 14:03
 */
public class CommonTest {
    @Test
    public void f1() {
        FileReader fileReader = new FileReader("id.md");
        String readString = fileReader.readString();
        List<String> strings = fileReader.readLines();
    }
}
