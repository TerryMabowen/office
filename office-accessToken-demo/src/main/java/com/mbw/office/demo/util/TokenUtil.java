package com.mbw.office.demo.util;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-14 20:28
 */
public class TokenUtil {
    @RequestMapping("/getToken")
    public static String getAccessToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
