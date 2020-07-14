package com.mbw.office.common.util.io;

import cn.hutool.core.io.IoUtil;
import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.common.util.validate.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author Mabowen
 * @date 2020-07-10 18:56
 */
@Slf4j
public class FileUtil {
    private static final String ENCODING = "UTF-8";
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String ATTACHMENT = "attachment;filename=";
    private static final String CONTENT_TYPE = "application/octet-stream";

    public static boolean createFile(String dir, String filename) {
        AssertUtil.assertNotEmpty(dir, "目录不能为空");
        AssertUtil.assertNotEmpty(filename, "文件名不能为空");

        try {
            File file = new File(dir, filename);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            return true;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public static File getFile(String path) {
        AssertUtil.assertNotEmpty(path, "文件路径不能为空");

        File file = new File(path);
        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }

    public static File[] getFiles(String dir) {
        AssertUtil.assertNotEmpty(dir, "文件目录不能为空");

        File fileDir = new File(dir);
        if (fileDir.exists() && fileDir.isDirectory()) {
            return fileDir.listFiles();
        } else {
            return null;
        }
    }

    public static boolean copy(File source, File target) {
        AssertUtil.assertNotNull(source, "源文件不能为空");
        AssertUtil.assertNotNull(target, "目标文件不能为空");

        try {
            FileInputStream fis = new FileInputStream(source);
            FileOutputStream fos = new FileOutputStream(target);

            IoUtil.copy(fis, fos);

            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public static void deleteDirectory(File dir) {
        if (dir != null && dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }

            dir.delete();
        }
    }

    /**
     * 删除临时目录和临时目录下的文件
     *
     * @param path 临时目录
     * @return
     * @author Mabowen
     * @date 17:32 2020-06-11
     */
    public static void deleteDirAndFiles(String path) {
        if (StringUtils.isBlank(path)) {
            throw new ServiceException("path can not be empty");
        }

        File file = new File(path);
        if (file.exists()) {
            if (file.isDirectory()) {
                deleteDirectory(file);
            } else {
                file.delete();
            }
        }
    }

    /**
     * 下载单个文件
     *
     * @author Mabowen
     * @date 10:30 2019-10-14
     */
    public static void downloadFile(String filepath, String filename, HttpServletResponse response) {
        try {
            // 设置响应头
            response.setCharacterEncoding(ENCODING);
            response.setContentType(CONTENT_TYPE);
            response.setHeader(CONTENT_DISPOSITION, ATTACHMENT + URLEncoder.encode(filename, ENCODING));

            //确认需要下载的文件是否存在
            File file = new File(filepath);
            if (!file.exists()) {
                throw new ServiceException(filename + "文件不存在");
            }

            if (!file.isFile()) {
                throw new ServiceException(filename + "不是一个文件");
            }

            // 获取输出流
            OutputStream os = response.getOutputStream();
            FileInputStream fis = new FileInputStream(file);
            IoUtil.copy(fis, os);
        } catch (IOException e) {
            log.error("下载文件异常： " + e.getMessage(), e);
            throw new ServiceException("下载文件异常： " + e.getMessage(), e);
        }
    }
}
