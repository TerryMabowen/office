package com.mbw.office.demo.web.controller.exception;

import com.mbw.office.common.exception.ServiceException;
import com.mbw.office.common.response.ResponseResults;
import com.mbw.office.common.util.json.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

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
            return handleException(exception, ERROR_403_VIEW);
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
            return handleException(exception, ERROR_500_VIEW);
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
            return handleException(exception, ERROR_500_VIEW);
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
        printWriter.write(JacksonUtil.beanToJson(ResponseResults.newFailed(exception.getMessage())));
        printWriter.flush();
        printWriter.close();
    }

    /**
     * 处理请求异常
     *
     * @author haoyun.zheng
     */
    private ModelAndView handleException(Exception exception, String exceptionView) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(String.format("Exception: %s", exception.getMessage()), exception);
        modelAndView.addObject("exception", exception.getMessage());
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
