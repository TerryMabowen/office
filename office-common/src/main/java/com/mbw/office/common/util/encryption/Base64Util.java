package com.mbw.office.common.util.encryption;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.mbw.office.common.exception.ServiceException;

import java.util.Base64;

/**
 * base64解码和编码
 *
 * @author Mabowen
 * @date 2020-07-10 13:27
 */
public class Base64Util {
    // 编码的对象
    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    // 解码的对象
    private static final Base64.Decoder DECODER = Base64.getDecoder();

    /**
     * Base64编码
     * @author Mabowen
     * @date 18:04 2020-04-09
     * @param buf
     * @return
     */
    public static byte[] encode(byte[] buf) {
        if (ArrayUtil.isEmpty(buf)) {
            throw new ServiceException("编码文件不能为空");
        }

        return ENCODER.encode(buf);
    }

    /**
     * Base64解码
     * @author Mabowen
     * @date 17:57 2020-04-09
     * @param text
     * @return
     */
    public static byte[] decode(String text) {
        if (StrUtil.isBlank(text)) {
            throw new ServiceException("需要解码的文字不能为空");
        }

        return DECODER.decode(text);
    }
}
