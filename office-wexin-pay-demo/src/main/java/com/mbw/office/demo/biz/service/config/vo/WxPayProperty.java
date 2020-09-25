package com.mbw.office.demo.biz.service.config.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 微信支付属性
 *
 * @author Mabowen
 * @date 2020-09-24 16:29
 */
@Data
@Builder
public class WxPayProperty {
    /**
     * 设置微信公众号或者小程序等的appid
     */
    private String appId;

    /**
     * 微信支付商户号
     */
    private String mchId;

    /**
     * 微信支付商户密钥
     */
    private String mchKey;

    /**
     * 是否是服务商商户, true:是
     */
    private boolean isServiceMch;

    /**
     * 服务商模式下的子商户公众账号ID，普通模式请不要配置，请在配置文件中将对应项删除
     */
    private String subAppId;

    /**
     * 服务商模式下的子商户号，普通模式请不要配置，最好是请在配置文件中将对应项删除
     */
    private String subMchId;
}
