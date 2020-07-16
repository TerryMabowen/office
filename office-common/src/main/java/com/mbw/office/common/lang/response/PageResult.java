package com.mbw.office.common.lang.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 分页数据响应类
 *
 * @author Mabowen
 * @date 2020-05-19 17:17
 */
@ApiModel(value = "分页查询返回信息")
public class PageResult implements Serializable {
    private static final long serialVersionUID = -8322865242392387264L;

    /**
     * 网络请求的成功的状态
     */
    public static final int OK = 0;
    public static final int SUCCESS = 200;
    public static final int DEFAULT_ERROR = 500;
    public static final int FORBIDDEN = 403;
    public static final int PARAM_ERROR = 400;
    /**
     * 属性
     */
    @ApiModelProperty(value = "请求是否成功；true：成功；false：失败")
    private boolean success;
    @ApiModelProperty(value = "请求状态码；200：请求成功；500：服务器错误；403：无权限访问；400：参数错误")
    private Integer code;
    @ApiModelProperty(value = "返回信息")
    private String message;
    @ApiModelProperty(value = "返回数据总数")
    private long count;
    @ApiModelProperty(value = "返回数据")
    private Object data;

    public PageResult() {
    }

    public static PageResult newFailed() {
        return newFailed("Failed");
    }

    public static PageResult newFailed(String errorMsg) {
        PageResult responsePage = new PageResult();
        responsePage.setSuccess(false);
        responsePage.setCode(DEFAULT_ERROR);
        responsePage.setMessage(errorMsg);
        responsePage.setCount(0);
        responsePage.setData(null);
        return responsePage;
    }

    public static PageResult newSuccess() {
        return newSuccess("Success", null, 0);
    }

    public static PageResult newSuccess(String msg) {
        return newSuccess(msg, null, 0);
    }

    public static PageResult newSuccess(Object data, long count) {
        return newSuccess("Success", data, count);
    }

    public static PageResult newSuccess(String msg, Object data, long count) {
        PageResult responsePage = new PageResult();
        responsePage.setSuccess(true);
        responsePage.setCode(SUCCESS);
        responsePage.setMessage(msg);
        responsePage.setData(data);
        responsePage.setCount(count);
        return responsePage;
    }

    public boolean isSuccess() {
        return success;
    }

    public PageResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public PageResult setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public PageResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public long getCount() {
        return count;
    }

    public PageResult setCount(long count) {
        this.count = count;
        return this;
    }

    public Object getData() {
        return data;
    }

    public PageResult setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PageResult)) {
            return false;
        }

        PageResult that = (PageResult) o;

        if (success != that.success) {
            return false;
        }
        if (count != that.count) {
            return false;
        }
        if (!code.equals(that.code)) {
            return false;
        }
        if (!message.equals(that.message)) {
            return false;
        }
        return data.equals(that.data);
    }

    @Override
    public int hashCode() {
        int result = (success ? 1 : 0);
        result = 31 * result + code.hashCode();
        result = 31 * result + message.hashCode();
        result = 31 * result + (int) (count ^ (count >>> 32));
        result = 31 * result + data.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
