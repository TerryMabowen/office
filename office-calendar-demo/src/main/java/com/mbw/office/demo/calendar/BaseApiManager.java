package com.mbw.office.demo.calendar;

import cn.hutool.core.util.StrUtil;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.common.lang.okhttp.OkHttpApiData;
import com.mbw.office.common.lang.okhttp.OkHttpEventBusFactory;
import com.mbw.office.common.util.validate.AssertUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * @author Mabowen
 * @date 2020-07-15 15:32
 */
@Slf4j
public abstract class BaseApiManager {
    private final static String CHARSET = "GBK";

    @Getter@Setter
    private BaseHttpConfig httpConfig;

    protected CalendarRequestProxy createApiProxy() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .headers(Headers.of(httpConfig.getRequestHeaders()))
                        .build();

                long beginTime = System.currentTimeMillis();

                Response response = chain.proceed(request)
                        .newBuilder()
                        .headers(Headers.of(httpConfig.getResponseHeaders()))
                        .build();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Sink sink = Okio.sink(baos);
                BufferedSink bufferedSink = Okio.buffer(sink);

                if(request.body() != null) {
                    request.body().writeTo(bufferedSink);
                }

                bufferedSink.close();
                sink.close();

                if (StrUtil.isNotBlank(baos.toString(CHARSET))) {
                    System.out.println(String.format("RequestBody: %s", baos.toString(CHARSET)));
                } else {
                    System.out.println(String.format("RequestBody: %s", request.url().encodedQuery()));
                }

                AssertUtil.assertNotNull(response.body(), "response.body()不存在");
                BufferedSource source = response.body().source();
                source.request(Long.MAX_VALUE);

                String body = source.getBuffer()
                        .clone()
                        .readString(Charset.forName(CHARSET));

                OkHttpApiData apiData = OkHttpApiData.builder()
                        .url(request.url().encodedPath())
                        .query(request.url().query())
                        .requestBody(StrUtil.isNotBlank(baos.toString(CHARSET)) ? baos.toString(CHARSET) : request.url().encodedQuery())
                        .responseBody(body)
                        .httpMethod(request.method())
                        .httpStatus(response.code())
                        .httpMessage(response.message())
                        .duration(System.currentTimeMillis() - beginTime)
                        .build();

                System.out.println(String.format("ResponseBody: %s", body));

                if (log.isDebugEnabled()) {
                    log.debug("ApiData: " + apiData.toString());
                }

                //同步方式记录事件，请求量大时不适用
                OkHttpEventBusFactory.getInstance()
                        .getDefaultEventBus()
                        .post(apiData);

                return response;
            }
        }).connectTimeout(httpConfig.getTimeout(), TimeUnit.MILLISECONDS).build();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .setDateFormat("yyyy-MM-dd")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(httpConfig.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit.create(CalendarRequestProxy.class);
    }

    protected <T extends BaseApiResponse<R>, R> T execute(retrofit2.Call<T> caller) {
        try {
            retrofit2.Response<T> response = caller.execute();
            if(response.isSuccessful()) {
                return response.body();
            } else {
                throw new ServiceException("接口调用异常,  http code = " + response.code());
            }
        } catch(IOException exp) {
            throw new ServiceException("接口调用异常" + exp.getMessage(), exp);
        }
    }
}
