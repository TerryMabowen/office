package com.mbw.office.common.http;

import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

/**
 * OkHttpInterceptor实现
 *
 * @author Mabowen
 * @date 2020-07-10 15:01
 */
public class OkHttpInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
