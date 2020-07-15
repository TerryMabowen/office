package com.mbw.office.common.lang.http;

import com.mbw.office.common.lang.exception.ServiceException;
import lombok.Setter;
import okhttp3.*;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * OkHttpClient
 *
 * @author Mabowen
 * @date 2020-07-10 14:02
 */
public class OkHttpClientFactory {
    private static OkHttpClientFactory factory;
    @Setter
    private MediaType mediaType = MediaType.get("application/json; charset=utf-8");
    @Setter
    private String charset = "UTF-8";

    private OkHttpClient defaultClient;

    public static OkHttpClientFactory getInstance() {
        if (factory != null) {
            return factory;
        }

        synchronized (OkHttpClientFactory.class) {
            if (factory == null) {
                factory = new OkHttpClientFactory();

                factory.init();
            }
        }

        return factory;
    }

    public String doGet(String url) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            Response response = defaultClient.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body().source().readString(Charset.forName(charset));
            } else {
                throw new ServiceException(response.message());
            }
        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public String doPost(String url, String jsonParams) {
        try {
            RequestBody body = RequestBody.create(mediaType, jsonParams);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            Response response = defaultClient.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body().source().readString(Charset.forName(charset));
            } else {
                throw new ServiceException(response.message());
            }
        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private void init() {
        defaultClient = new OkHttpClient.Builder().build();
    }
}
