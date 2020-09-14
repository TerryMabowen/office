package com.mbw.office.io;

import com.mbw.office.common.util.io.FTPConfig;
import com.mbw.office.common.util.io.FTPUtil;
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

    @Test
    public void f3() {
        FTPConfig config = new FTPConfig();
//        config.setHost("http://172.16.100.7");
        config.setHost("http://s.legotown.cn");
        config.setPort(21);
//        config.setUsername("apple_22");
//        config.setPassword("8277348");
        config.setUsername("root");
        config.setPassword("Bell.Ai");
        config.setFtpBasePath("/data/");
        FTPUtil ftpCli = FTPUtil.createFtpCli(config);
        ftpCli.connect();
        System.out.println(ftpCli.isConnected());
        ftpCli.disconnect();
    }
}
