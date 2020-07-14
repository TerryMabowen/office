package com.mbw.office.io;

import com.mbw.office.common.util.io.FileUtil;
import org.junit.Test;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-14 09:44
 */
public class IoTest {

    @Test
    public void f1() {
        String path = "/Users/apple_22/Desktop/log4j/dubbo-server-admin";
        FileUtil.deleteDirAndFiles(path);
    }

    @Test
    public void f2() {
        String path = "/Users/apple_22/Desktop/log4j/testIo";
        String filename = "xxx-file.txt";
        FileUtil.createFile(path, filename);
    }
}
