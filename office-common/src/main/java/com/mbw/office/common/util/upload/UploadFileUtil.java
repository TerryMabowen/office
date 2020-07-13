package com.mbw.office.common.util.upload;

import java.util.UUID;

/**
 * 上传文件的工具类
 *
 * @author Mabowen
 * @date 2020-07-10 20:41
 */
public class UploadFileUtil {

    public static String getUuid1() {
        return UUID.randomUUID().toString();
    }

    public static String getUuid2() {
        return getUuid1().replace("-", "");
    }
}
