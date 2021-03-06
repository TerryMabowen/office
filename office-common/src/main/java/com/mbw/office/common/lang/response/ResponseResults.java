package com.mbw.office.common.lang.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Collection;

/**
 * 后端返回前端数据响应类
 *
 * @author Mabowen
 * @date 2020-05-19 17:15
 */
@ApiModel(value = "接口返回说明")
public class ResponseResults implements Serializable {
    private static final long serialVersionUID = 5390343053421148656L;

    /**
     * 网络请求的状态码
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
    @ApiModelProperty(value = "返回数据")
    private Object data;
    @ApiModelProperty(value = "返回信息")
    private String message;
    @ApiModelProperty(value = "请求状态码；200：请求成功；500：服务器错误；403：无权限访问；400：参数错误")
    private Integer code;

    private Integer count = 0;

    public ResponseResults() {
    }

    public static ResponseResults newSuccess() {
        return newSuccess("Success", null);
    }

    public static ResponseResults newSuccess(String message) {
        return newSuccess(message, null);
    }

    public static ResponseResults newSuccess(Object data) {
        return newSuccess("Success", data);
    }

    public static ResponseResults newSuccess(String message, Object data) {
        ResponseResults rd = new ResponseResults();
        rd.setSuccess(true);
        rd.setCode(SUCCESS);
        rd.setMessage(message);
        rd.setData(data);
        int count = 0;
        if (data instanceof Collection) {
            count = ((Collection) data).size();
        } else {
            if (data != null) {
                count = 1;
            }
        }

        rd.setCount(count);
        return rd;
    }

    public static ResponseResults newFailed() {
        return newFailed("Failed", null);
    }

    public static ResponseResults newFailed(String message) {
        return newFailed(message, null);
    }

    public static ResponseResults newFailed(Object data) {
        return newFailed("Failed", data);
    }

    public static ResponseResults newFailed(String message, Object data) {
        ResponseResults rd = new ResponseResults();
        rd.setSuccess(false);
        rd.setMessage(message);
        rd.setData(data);
        rd.setCode(DEFAULT_ERROR);
        return rd;
    }

    public boolean isSuccess() {
        return success;
    }

    public ResponseResults setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResponseResults setData(Object data) {
        this.data = data;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseResults setMessage(String message) {
        this.message = message;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public ResponseResults setCode(Integer code) {
        this.code = code;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResponseResults)) {
            return false;
        }

        ResponseResults that = (ResponseResults) o;

        if (success != that.success) {
            return false;
        }
        if (!data.equals(that.data)) {
            return false;
        }
        if (!message.equals(that.message)) {
            return false;
        }
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        int result = (success ? 1 : 0);
        result = 31 * result + data.hashCode();
        result = 31 * result + message.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ResponseResults{" +
                "success=" + success +
                ", code=" + code +
                ", message=" + message + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
