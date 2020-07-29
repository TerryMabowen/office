package com.mbw.office.demo.web.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Mabowen
 * @date 2020-07-17 14:57
 */
//@Configuration
//@ConditionalOnClass(WxPayService.class)
//@EnableConfigurationProperties(WxPayProperties.class)
//@AllArgsConstructor
public class WeXinPayConfig {
    private WxPayProperties properties;

//    @Bean
//    @ConditionalOnMissingBean
    public WxPayService wxService() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(StringUtils.trimToNull(properties.getAppId()));
        payConfig.setMchId(StringUtils.trimToNull(properties.getMchId()));
        payConfig.setMchKey(StringUtils.trimToNull(properties.getMchKey()));
        payConfig.setSubAppId(StringUtils.trimToNull(properties.getSubAppId()));
        payConfig.setSubMchId(StringUtils.trimToNull(properties.getSubMchId()));
        payConfig.setKeyPath(StringUtils.trimToNull(properties.getKeyPath()));

        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(false);

        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }
}
