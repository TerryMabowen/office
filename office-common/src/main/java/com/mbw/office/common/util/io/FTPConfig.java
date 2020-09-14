package com.mbw.office.common.util.io;

import lombok.Data;

/**
 * FTP配置
 *
 * @author Mabowen
 * @date 2020-09-14 11:32
 */
@Data
public class FTPConfig {
    /**
     * 主机名或者ip地址
     */
    private String host;

    /**
     * ftp服务器端口
     */
    private int port;

    /**
     * ftp用户名
     */
    private String username;

    /**
     * ftp密码
     */
    private String password;

    /**
     * 初始化时ftp服务器路径
     */
    private volatile String ftpBasePath = "";
}
