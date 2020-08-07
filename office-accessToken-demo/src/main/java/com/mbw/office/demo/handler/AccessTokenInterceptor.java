package com.mbw.office.demo.handler;

import com.mbw.office.common.util.json.JacksonUtil;
import com.mbw.office.demo.base.BaseApiService;
import com.mbw.office.demo.service.BaseRedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-14 20:21
 */
@Component
public class AccessTokenInterceptor extends BaseApiService implements HandlerInterceptor {
    @Autowired
    private BaseRedisService baseRedisService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
            throws Exception {
        System.out.println("---------------------开始进入请求地址拦截----------------------------");
        String accessToken = httpServletRequest.getParameter("accessToken");
        // 判断accessToken是否空
        if (StringUtils.isEmpty(accessToken)) {
            // 参数Token accessToken
            resultError(" this is parameter accessToken null ", httpServletResponse);
            return false;
        }
        String appId = (String) baseRedisService.getString(accessToken);
        if (StringUtils.isEmpty(appId)) {
            // accessToken 已经失效!
            resultError(" this is accessToken Invalid ", httpServletResponse);
            return false;
        }
        // 正常执行业务逻辑...
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("--------------处理请求完成后视图渲染之前的处理操作---------------");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
        System.out.println("---------------视图渲染之后的操作-------------------------0");
    }

    // 返回错误提示
    public void resultError(String errorMsg, HttpServletResponse httpServletResponse) throws IOException {
        PrintWriter printWriter = httpServletResponse.getWriter();
        printWriter.write(JacksonUtil.beanToJson(setResultError(errorMsg)));
    }
}
