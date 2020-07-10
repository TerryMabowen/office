package com.mbw.office.common.util.io;

import com.mbw.office.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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
            StreamUtils.copy(fis, os);
            fis.close();
            os.close();
        } catch (IOException e) {
            log.error("下载文件异常： " + e.getMessage(), e);
            throw new ServiceException("下载文件异常： " + e.getMessage(), e);
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
    public static void deleteTempDirAndFiles(String path) {
        if (StringUtils.isBlank(path)) {
            throw new ServiceException("临时目录不能为空");
        }

        try {
            File fileDir = new File(path);
            if (fileDir.exists() && fileDir.isDirectory()) {
                FileUtils.deleteDirectory(fileDir);
            }
        } catch (IOException e) {
            log.error("删除临时目录和临时目录下的文件失败：" + e.getMessage(), e);
            throw new ServiceException("删除临时目录和临时目录下的文件失败：" + e.getMessage(), e);
        }
    }
}
