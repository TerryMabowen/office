package com.mbw.office.common.util.io;

import com.mbw.office.common.lang.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * FTP工具类
 * 跨服务器操作文件---上传、下载、删除、读取
 *
 * @author Mabowen
 * @date 2020-09-14 10:28
 */
public class FTPUtil {
    /**
     * 字符集
     */
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 超时时间
     */
    private static final int DEFAULT_TIMEOUT = 60 * 1000;

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

    /**
     * ftpClient对象
     */
    private FTPClient ftpClient;

    /**
     * 构造函数
     * host        主机名或者ip地址
     * username    ftp 用户名
     * password    ftp 密码
     * ftpBasePath 初始化时ftp服务器路径
     * @param config ftp配置
     */
    private FTPUtil(FTPConfig config) {
        this(config, DEFAULT_CHARSET);
    }

    /**
     * 构造函数
     * host        主机名或者ip地址
     * port        ftp 端口
     * username    ftp 用户名
     * password    ftp 密码
     * ftpBasePath 初始化时ftp服务器路径
     *
     * @param config ftp配置
     * @param charset 字符集
     */
    private FTPUtil(FTPConfig config, String charset) {
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding(charset);
        this.host = StringUtils.isBlank(config.getHost()) ? "localhost" : config.getHost();
        this.port = (config.getPort() <= 0) ? 21 : config.getPort();
        this.username = StringUtils.isBlank(config.getUsername()) ? "anonymous" : config.getUsername();
        this.password = config.getPassword();
        this.ftpBasePath = config.getFtpBasePath();
        setTimeout(DEFAULT_TIMEOUT, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    /**
     * 创建默认的ftp客户端
     *
     * @param config ftp配置
     */
    public static FTPUtil createFtpCli(FTPConfig config) {
        return new FTPUtil(config);
    }

    /**
     * 创建自定义属性的ftp客户端
     *
     * @param config ftp配置
     * @param charset     字符集
     */
    public static FTPUtil createFtpCli(FTPConfig config, String charset) {
        return new FTPUtil(config, charset);
    }

    /**
     * 设置超时时间
     *
     * @param defaultTimeout 默认超时时间
     * @param connectTimeout 连接超时时间
     * @param dataTimeout    数据超时时间
     */
    private void setTimeout(int defaultTimeout, int connectTimeout, int dataTimeout) {
        ftpClient.setDefaultTimeout(defaultTimeout);
        ftpClient.setConnectTimeout(connectTimeout);
        ftpClient.setDataTimeout(dataTimeout);
    }

    /**
     * 连接到ftp
     */
    public void connect() {
        try {
            ftpClient.connect(host, port);

            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                disconnect();
                throw new IOException("Can't connect to server :" + host);
            }

            if (!ftpClient.login(username, password)) {
                disconnect();
                throw new IOException("Can't login to server :" + host);
            }

            // set data transfer mode.
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // Use passive mode to pass firewalls.
            ftpClient.enterLocalPassiveMode();

            initFtpBasePath();
        } catch (IOException e) {
            throw new ServiceException("ftp connect error: " + e.getMessage(), e);
        }
    }

    /**
     * 连接ftp时保存刚登陆ftp时的路径
     */
    private void initFtpBasePath() {
        try {
            if (StringUtils.isEmpty(ftpBasePath)) {
                synchronized (this) {
                    if (StringUtils.isEmpty(ftpBasePath)) {
                        ftpBasePath = ftpClient.printWorkingDirectory();
                    }
                }
            }
        } catch (IOException e) {
            throw new ServiceException("initFtpBasePath error: " + e.getMessage(), e);
        }
    }

    /**
     * ftp是否处于连接状态，是连接状态返回<tt>true</tt>
     *
     * @return boolean  是连接状态返回<tt>true</tt>
     */
    public boolean isConnected() {
        return ftpClient.isConnected();
    }

    /**
     * 上传文件到对应目录下
     *
     * @param fileName    文件名
     * @param inputStream 文件输入流
     * @param uploadDir   上传文件的父路径
     * @return java.lang.String
     */
    public String uploadFile(String fileName, InputStream inputStream, String uploadDir) {
        try {
            changeWorkingDirectory(ftpBasePath);
            SimpleDateFormat dateFormat = new SimpleDateFormat("/yyyy/MM/dd");
            makeDirs(uploadDir);
            storeFile(fileName, inputStream);
            return uploadDir + "/" + fileName;
        } catch (Exception e) {
            throw new ServiceException("uploadFile error: " + e.getMessage(), e);
        }
    }

    /**
     * 从ftp下载文件到指定输出流中
     *
     * @param filename 文件路径及名称
     * @param outputStream 输出流
     */
    public void downloadFile(String filename, OutputStream outputStream) {
        try {
            changeWorkingDirectory(ftpBasePath);
            retrieveFile(filename, outputStream);
        } catch (Exception e) {
            throw new ServiceException("downloadFileFromDailyDir error: " + e.getMessage(), e);
        }
    }

    /**
     * 获取ftp上指定文件名到输出流中
     *
     * @param ftpFileName 文件在ftp上的路径  如绝对路径 /home/ftpuser/123.txt 或者相对路径 123.txt
     * @param out         输出流
     */
    public void retrieveFile(String ftpFileName, OutputStream out) {
        try {
            FTPFile[] fileInfoArray = ftpClient.listFiles(ftpFileName);
            if (fileInfoArray == null || fileInfoArray.length == 0) {
                throw new FileNotFoundException("File '" + ftpFileName + "' was not found on FTP server.");
            }

            FTPFile fileInfo = fileInfoArray[0];
            if (fileInfo.getSize() > Integer.MAX_VALUE) {
                throw new IOException("File '" + ftpFileName + "' is too large.");
            }

            if (!ftpClient.retrieveFile(ftpFileName, out)) {
                throw new IOException("Error loading file '" + ftpFileName + "' from FTP server. Check FTP permissions and path.");
            }
            out.flush();
        } catch (IOException e) {
            throw new ServiceException("retrieveFile error: " + e.getMessage(), e);
        } finally {
            closeStream(out);
        }
    }


    /**
     * 将输入流存储到指定的ftp路径下
     *
     * @param ftpFileName 文件在ftp上的路径 如绝对路径 /home/ftpuser/123.txt 或者相对路径 123.txt
     * @param in          输入流
     */
    private void storeFile(String ftpFileName, InputStream in) {
        try {
            if (!ftpClient.storeFile(ftpFileName, in)) {
                throw new IOException("Can't upload file '" + ftpFileName + "' to FTP server. Check FTP permissions and path.");
            }
        } catch (IOException e) {
            throw new ServiceException("storeFile error: " + e.getMessage(), e);
        } finally {
            closeStream(in);
        }
    }

    /**
     * 根据文件ftp路径名称删除文件
     *
     * @param ftpFileName 文件ftp路径名称
     */
    public void deleteFile(String ftpFileName) {
        try {
            if (!ftpClient.deleteFile(ftpFileName)) {
                throw new IOException("Can't remove file '" + ftpFileName + "' from FTP server.");
            }
        } catch (IOException e) {
            throw new ServiceException("deleteFile error: " + e.getMessage(), e);
        }
    }

    /**
     * 上传文件到ftp
     *
     * @param ftpFileName 上传到ftp文件路径名称
     * @param localFile   本地文件路径名称
     */
    public void upload(String ftpFileName, File localFile) {
        InputStream in = null;
        try {
            if (!localFile.exists()) {
                throw new IOException("Can't upload '" + localFile.getAbsolutePath() + "'. This file doesn't exist.");
            }

            in = new BufferedInputStream(new FileInputStream(localFile));
            if (!ftpClient.storeFile(ftpFileName, in)) {
                throw new IOException("Can't upload file '" + ftpFileName + "' to FTP server. Check FTP permissions and path.");
            }
        } catch (IOException e) {
            throw new ServiceException("upload error: " + e.getMessage(), e);
        } finally {
            closeStream(in);
        }
    }

    /**
     * 上传文件夹到ftp上
     *
     * @param remotePath ftp上文件夹路径名称
     * @param localPath  本地上传的文件夹路径名称
     */
    public void uploadDir(String remotePath, String localPath) {
        try {
            localPath = localPath.replace("\\\\", "/");
            File file = new File(localPath);
            if (file.exists()) {
                if (!ftpClient.changeWorkingDirectory(remotePath)) {
                    ftpClient.makeDirectory(remotePath);
                    ftpClient.changeWorkingDirectory(remotePath);
                }
                File[] files = file.listFiles();
                if (null != files) {
                    for (File f : files) {
                        if (f.isDirectory() && !".".equals(f.getName()) && !"..".equals(f.getName())) {
                            uploadDir(remotePath + "/" + f.getName(), f.getPath());
                        } else if (f.isFile()) {
                            upload(remotePath + "/" + f.getName(), f);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new ServiceException("uploadDir error: " + e.getMessage(), e);
        }
    }

    /**
     * 下载ftp文件到本地上
     *
     * @param ftpFileName ftp文件路径名称
     * @param localFile   本地文件路径名称
     */
    public void download(String ftpFileName, File localFile) {
        OutputStream out = null;
        try {
            FTPFile[] fileInfoArray = ftpClient.listFiles(ftpFileName);
            if (fileInfoArray == null || fileInfoArray.length == 0) {
                throw new FileNotFoundException("File " + ftpFileName + " was not found on FTP server.");
            }

            FTPFile fileInfo = fileInfoArray[0];
            if (fileInfo.getSize() > Integer.MAX_VALUE) {
                throw new IOException("File " + ftpFileName + " is too large.");
            }

            out = new BufferedOutputStream(new FileOutputStream(localFile));
            if (!ftpClient.retrieveFile(ftpFileName, out)) {
                throw new IOException("Error loading file " + ftpFileName + " from FTP server. Check FTP permissions and path.");
            }
            out.flush();
        } catch (IOException e) {
            throw new ServiceException("download error: " + e.getMessage(), e);
        } finally {
            closeStream(out);
        }
    }


    /**
     * 改变工作目录
     *
     * @param dir ftp服务器上目录
     * @return boolean 改变成功返回true
     */
    public boolean changeWorkingDirectory(String dir) {
        if (!ftpClient.isConnected()) {
            return false;
        }

        try {
            return ftpClient.changeWorkingDirectory(dir);
        } catch (IOException e) {
            System.out.println("changeWorkingDirectory error: " + e.getMessage());
        }

        return false;
    }

    /**
     * 下载ftp服务器下文件夹到本地
     *
     * @param remotePath ftp上文件夹路径名称
     * @param localPath  本地上传的文件夹路径名称
     */
    public void downloadDir(String remotePath, String localPath) {
        try {
            localPath = localPath.replace("\\\\", "/");
            File file = new File(localPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            FTPFile[] ftpFiles = ftpClient.listFiles(remotePath);
            for (int i = 0; ftpFiles != null && i < ftpFiles.length; i++) {
                FTPFile ftpFile = ftpFiles[i];
                if (ftpFile.isDirectory() && !".".equals(ftpFile.getName()) && !"..".equals(ftpFile.getName())) {
                    downloadDir(remotePath + "/" + ftpFile.getName(), localPath + "/" + ftpFile.getName());
                } else {
                    download(remotePath + "/" + ftpFile.getName(), new File(localPath + "/" + ftpFile.getName()));
                }
            }
        } catch (IOException e) {
            throw new ServiceException("downloadDir error: " + e.getMessage(), e);
        }
    }


    /**
     * 列出ftp上文件目录下的文件
     *
     * @param filePath ftp上文件目录
     */
    public List<String> listFileNames(String filePath) {
        List<String> fileList = new ArrayList<>();
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles(filePath);
            if (ftpFiles != null) {
                for (int i = 0; i < ftpFiles.length; i++) {
                    FTPFile ftpFile = ftpFiles[i];
                    if (ftpFile.isFile()) {
                        fileList.add(ftpFile.getName());
                    }
                }
            }
        } catch (IOException e) {
            throw new ServiceException("listFileNames error: " + e.getMessage(), e);
        }

        return fileList;
    }

    /**
     * 发送ftp命令到ftp服务器中
     *
     * @param args ftp命令
     */
    public void sendSiteCommand(String args) {
        try {
            if (!ftpClient.isConnected()) {
                ftpClient.sendSiteCommand(args);
            }
        } catch (IOException e) {
            throw new ServiceException("sendSiteCommand error: " + e.getMessage(), e);
        }
    }

    /**
     * 获取当前所处的工作目录
     *
     * @return java.lang.String 当前所处的工作目录
     */
    public String printWorkingDirectory() {
        if (!ftpClient.isConnected()) {
            return "";
        }
        try {
            return ftpClient.printWorkingDirectory();
        } catch (IOException e) {
            // do nothing
            System.out.println("printWorkingDirectory error: " + e.getMessage());
        }

        return "";
    }

    /**
     * 切换到当前工作目录的父目录下
     *
     * @return boolean 切换成功返回true
     */
    public boolean changeToParentDirectory() {
        if (!ftpClient.isConnected()) {
            return false;
        }

        try {
            return ftpClient.changeToParentDirectory();
        } catch (IOException e) {
            // do nothing
            System.out.println("changeToParentDirectory error: " + e.getMessage());
        }

        return false;
    }

    /**
     * 返回当前工作目录的上一级目录
     *
     * @return java.lang.String 当前工作目录的父目录
     */
    public String printParentDirectory() {
        if (!ftpClient.isConnected()) {
            return "";
        }

        String w = printWorkingDirectory();
        changeToParentDirectory();
        String p = printWorkingDirectory();
        changeWorkingDirectory(w);

        return p;
    }

    /**
     * 创建目录
     *
     * @param pathname 路径名
     * @return boolean 创建成功返回true
     */
    public boolean makeDirectory(String pathname) {
        try {
            return ftpClient.makeDirectory(pathname);
        } catch (IOException e) {
            throw new ServiceException("makeDirectory error: " + e.getMessage(), e);
        }
    }

    /**
     * 创建多个目录
     *
     * @param pathname 路径名
     */
    public void makeDirs(String pathname) {
        try {
            pathname = pathname.replace("\\\\", "/");
            String[] pathnameArray = pathname.split("/");
            for (String each : pathnameArray) {
                if (StringUtils.isNotEmpty(each)) {
                    ftpClient.makeDirectory(each);
                    ftpClient.changeWorkingDirectory(each);
                }
            }
        } catch (IOException e) {
            throw new ServiceException("makeDirs error: " + e.getMessage(), e);
        }
    }

    /**
     * 关闭流
     *
     * @param stream 流
     */
    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException ex) {
                // do nothing
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * 关闭ftp连接
     */
    public void disconnect() {
        if (null != ftpClient && ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException ex) {
                // do nothing
                System.out.println(ex.getMessage());
            }
        }
    }
}
