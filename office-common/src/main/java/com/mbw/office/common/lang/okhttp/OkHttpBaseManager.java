package com.mbw.office.common.lang.okhttp;

import cn.hutool.core.util.StrUtil;
import com.baidu.unbiz.fluentvalidator.Result;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.common.lang.okhttp.domain.LoginResponseData;
import com.mbw.office.common.util.validate.AssertUtil;
import com.mbw.office.common.util.validate.ValidatorUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * OkHttp 服务
 *
 * @author Mabowen
 * @date 2020-06-02 09:04
 */
@Slf4j
public abstract class OkHttpBaseManager {
    private final static int LIMIT_PER_SECONDS = 100;

    private static String CHARSET = "UTF-8";

    @Autowired
    @Getter
    @Setter
    private OkHttpConfig okHttpConfig;

    private AtomicInteger speedPerSecond = new AtomicInteger(0);
    private long currentSecond = 0;

    protected Request request;
    protected Response response;

    private String accessToken = "";
    protected Integer expiryTime = 0;
    private Integer retryRefreshToken = 0;

    public abstract OkHttpBaseResponse<LoginResponseData> refreshAccessToken();

    protected Object createApiProxy(Class<?> cls) {
        if (StrUtil.isNotBlank(okHttpConfig.getCharset())) {
            CHARSET = okHttpConfig.getCharset();
        }

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
                // 限流
                limiting();

                Request request = chain.request()
                        .newBuilder()
                        .headers(Headers.of(okHttpConfig.getRequestHeaders()))
                        .build();

                long beginTime = System.currentTimeMillis();

                Response response = chain.proceed(request)
                        .newBuilder()
                        .headers(Headers.of(okHttpConfig.getResponseHeaders()))
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
                    System.out.println(String.format("RequestBody: %s", request.url().query()));
                }

                AssertUtil.assertNotNull(response.body(), "response.body()不存在");
                BufferedSource source = response.body().source();
                source.request(Long.MAX_VALUE);

                String body = source.getBuffer()
                        .clone()
                        .readString(Charset.forName(CHARSET));

                System.out.println(String.format("ResponseBody: %s", body));

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
                if (log.isDebugEnabled()) {
                    log.debug("ApiData: " + apiData.toString());
                }

                //同步方式记录事件，请求量大时不适用
                OkHttpEventBusFactory.getInstance()
                        .getDefaultEventBus()
                        .post(apiData);

                //异步方式记录事件
//                OkHttpEventBusFactory.getInstance()
//                        .getDefaultAsyncEventBus()
//                        .post(apiData);

                return response;
            }
        }).connectTimeout(okHttpConfig.getTimeout(), TimeUnit.MILLISECONDS).build();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .setDateFormat("yyyy-MM-dd")
                .create();

//        Gson gson = GsonFactory.getInstance().getGson();

        /**
         * 转换工厂
         * JaxbConverterFactory xml数据
         * GsonConverterFactory json数据
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(okHttpConfig.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit.create(cls);
    }

    protected <T extends OkHttpBaseResponse<R>, R> T execute(Call<T> caller) {
        try {
            retrofit2.Response<T> response = caller.execute();
            if(response.isSuccessful()) {
                if(retryRefreshToken > 0) {
                    retryRefreshToken = 0;
                }
                return response.body();
            } else if(response.code() == HttpStatus.FORBIDDEN.value()) {
                // HttpStatus.FORBIDDEN.value() == 403
                if(retryRefreshToken < 3) {
                    retryRefreshToken++;
                    //Token失效，重新触发一次调用，refresh会清除retryRefreshToken，触发 retryRefreshToken = 0
                    refreshAccessToken();
                    response = caller.execute();
                } else {
                    throw new ServiceException("身份接口调用异常, 可能是Token失效  http code = " + response.code());
                }
                return response.body();
            } else {
                throw new ServiceException("接口调用异常,  http code = " + response.code());
            }
        } catch(IOException exp) {
            throw new ServiceException("接口调用异常" + exp.getMessage(), exp);
        }
    }

    /**
     * 	检测token是否失效,如果失效更换Token
     */
    public void scheduleRefreshToken() {
        //提60S刷新Token
        log.info("Token过期时间还差： " + (int)(System.currentTimeMillis()/1000 - expiryTime));
        if(System.currentTimeMillis()/1000 - expiryTime > -60 * 60) {
            refreshAccessToken();
        }
    }

    /**
     * 参数校验
     * @param t
     * @return
     */
    protected <T> Result validate(T t) {
        return ValidatorUtil.getInstance()
                .validateObject(t);
    }

    /**
     * 限流 每秒100次
     */
    private void limiting() {
        if(currentSecond != System.currentTimeMillis() / 1000) {
            currentSecond = System.currentTimeMillis() / 1000;
            speedPerSecond.set(0);
        }

        speedPerSecond.addAndGet(1);
        if(speedPerSecond.intValue() > LIMIT_PER_SECONDS) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new ServiceException(e.getMessage(), e);
            }
        }
    }

    public synchronized String getAccessToken() {
        return accessToken;
    }

    public synchronized void setAccessToken(String token) {
        this.accessToken = token;
    }
}
