package com.mbw.office.common.util.upload;

import cn.hutool.core.util.StrUtil;
import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.common.util.date.DateUtil;
import com.mbw.office.common.util.io.FileUtil;
import com.mbw.office.common.util.validate.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 上传文件的工具类
 *
 * @author Mabowen
 * @date 2020-07-10 20:41
 */
@Slf4j
public class UploadFileUtil {

    /**
     * 上传文件
     * @author Mabowen
     * @date 19:46 2020-07-13
     * @param file 上传的文件
     * @param rootPath 根目录
     * @return
     */
    public String uploadFile(MultipartFile file, String rootPath) {
        AssertUtil.assertNotNull(file, "上传文件不能为空");

        //如果fileType为空，则默认为common
        if(StrUtil.isNotBlank(rootPath) && !rootPath.endsWith(File.separator)){
            rootPath = rootPath + File.separator;
        }

        //获取原文件的文件名
        String oldName = file.getOriginalFilename();
        if (StrUtil.isBlank(oldName)) {
            throw new ServiceException("上传文件名不能为空");
        }

        //原文件的类型 格式为.jpg 或 .png 或 ......
        String suffix = oldName.substring(oldName.lastIndexOf("."));

        String currentTimestamp = DateUtil.getCurrentTimestamp();
        String currentDate = DateUtil.getCurrentDate();

        //将文件名修改为时间戳，避免原文件出现文件名过长情况
        String newName = "File-" + currentTimestamp  + suffix;

        // 如果目录不存在则创建
        if (FileUtil.createFile(rootPath + currentDate, newName)) {
            File tempFile = FileUtil.getFile(rootPath + currentDate + newName);
            try{
                if (tempFile != null) {
                    //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
                    file.transferTo(tempFile);
                } else {
                    throw new ServiceException(String.format("上传%s文件失败，失败原因是%s", oldName, "创建文件失败"));
                }
            }catch (IOException e){
                throw new ServiceException(String.format("上传%s文件失败，失败原因是%s", oldName, e.getMessage()), e);
            }
        }

        //返回虚构目录结构的文件路径
        return "imaginary" + File.separator + currentDate + File.separator + newName;
    }
}
