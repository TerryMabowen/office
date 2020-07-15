package com.mbw.office.common.util.http;

import com.mbw.office.common.lang.exception.ServiceException;
import lombok.Getter;
import lombok.Setter;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;

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
    private static MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
    @Setter
    private static String CHARSET = "UTF-8";

    private OkHttpClient defaultClient;

    @Autowired
    @Getter @Setter
    private OkHttpConfig okHttpConfig;

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

            System.out.println(String.format("RequestBody: %s", request.url().query()));

            Response response = defaultClient.newCall(request).execute();

            if (response.isSuccessful() && response.body() != null) {
                System.out.println(String.format("ResponseBody: %s", response.body().source().readString(Charset.forName(CHARSET))));
                return response.body().source().readString(Charset.forName(CHARSET));
            } else {
                throw new ServiceException(response.message());
            }
        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public String doPost(String url, String jsonParams) {
        try {
            RequestBody body = RequestBody.create(MEDIA_TYPE, jsonParams);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            System.out.println(String.format("RequestBody: %s", request.url().query()));

            Response response = defaultClient.newCall(request).execute();

            if (response.isSuccessful() && response.body() != null) {
                System.out.println(String.format("ResponseBody: %s", response.body().source().readString(Charset.forName(CHARSET))));
                return response.body().source().readString(Charset.forName(CHARSET));
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
