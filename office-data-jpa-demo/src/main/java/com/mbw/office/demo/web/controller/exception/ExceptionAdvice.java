package com.mbw.office.demo.web.controller.exception;

import com.google.gson.GsonBuilder;
import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.common.lang.response.ResponseResults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 控制器异常处理
 *
 * @author haoyun.zheng mabowen
 */
@Slf4j
@ControllerAdvice(basePackages = {"com.mbw.office.sso.web.controller"})
public class ExceptionAdvice {
    final static String DEFAULT_ERROR_MSG = "当前请求出现错误,请重试或者联系管理员";

    private static final String ERROR_403_VIEW = "errors/403.html";
    private static final String ERROR_500_VIEW = "errors/500.html";

    @ExceptionHandler(AuthenticationException.class)
    public Object authorizationException(AuthenticationException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug(String.format("访问权限异常: %s", exception.getMessage()), exception);
        }
        if (isAjaxRequest(request)) {
            handleAjaxException(response, exception);
            return null;
        } else {
            return handleException(response, exception, ERROR_403_VIEW);
        }
    }

    @ExceptionHandler(ServiceException.class)
    public Object serviceException(ServiceException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug(String.format("serviceException: %s", exception.getMessage()), exception);
        }
        if (isAjaxRequest(request)) {
            handleAjaxException(response, exception);
            return null;
        } else {
            return handleException(response, exception, ERROR_500_VIEW);
        }
    }

    @ExceptionHandler(Exception.class)
    public Object exception(Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug(String.format("Exception: %s", exception.getMessage()), exception);
        }
        if (isAjaxRequest(request)) {
            handleAjaxException(response, exception);
            return null;
        } else {
            return handleException(response, exception, ERROR_500_VIEW);
        }
    }

    /**
     * 处理Ajax请求异常
     *
     * @author haoyun.zheng
     */
    private void handleAjaxException(HttpServletResponse response, Exception exception) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        PrintWriter printWriter = response.getWriter();
        printWriter.write(new GsonBuilder().create()
                .toJson(ResponseResults.newFailed()
                        .setMessage(exception.toString())
                        .setData(exception)
                )
        );
        printWriter.flush();
        printWriter.close();
    }

    /**
     * 处理请求异常
     *
     * @author haoyun.zheng
     */
    private ModelAndView handleException(HttpServletResponse response, Exception exception, String exceptionView) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", exception);
        modelAndView.setStatus(HttpStatus.OK);
        modelAndView.setViewName(exceptionView);

        return modelAndView;
    }

    /**
     * 判断某个请求是否是ajax请求
     *
     * @return 返回true, 是ajax请求，false则不是
     * @author haoyun.zheng
     */
    private boolean isAjaxRequest(HttpServletRequest request) {
        return request.getHeader("X-Requested-With") != null &&
                "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

}
