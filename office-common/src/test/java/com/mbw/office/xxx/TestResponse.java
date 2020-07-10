package com.mbw.office.xxx;

import lombok.Data;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-10 17:11
 */
@Data
public class TestResponse<T> {
    private String message;
    private Integer state;
    private Integer status;
    private String com;
    private String condition;
    private Integer ischeck;
    private String nu;
    private T data;
}
