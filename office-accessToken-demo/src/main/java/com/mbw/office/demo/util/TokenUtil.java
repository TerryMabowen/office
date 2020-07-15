package com.mbw.office.demo.util;

import java.util.UUID;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-14 20:28
 */
public class TokenUtil {

    public static String getAccessToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
