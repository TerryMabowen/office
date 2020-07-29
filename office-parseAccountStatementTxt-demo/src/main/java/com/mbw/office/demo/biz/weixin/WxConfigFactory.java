package com.mbw.office.demo.biz.weixin;

import cn.hutool.core.map.MapUtil;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.common.util.validate.AssertUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信配置工厂
 * 获取单例WxPayService
 * @author Mabowen
 * @date 2020-07-29 11:23
 */
@Slf4j
@Service
public class WxConfigFactory {

    private static Map<String, WxPayService> wxPayServiceMap = new HashMap<>();

    @PostConstruct
    public void init() {
        initWxPayServiceMap();
    }

    /**
     * 获取单例WxPayService
     * @param key
     * @return
     */
    public synchronized WxPayService getWxPayService(String key) {
        AssertUtil.assertNotEmpty(key, "key cannot be empty");

        if (MapUtil.isEmpty(wxPayServiceMap)) {
            initWxPayServiceMap();
        }

        WxPayService wxPayService = wxPayServiceMap.get(key);
        if (null == wxPayService) {
            initWxPayServiceMap();
            wxPayService = wxPayServiceMap.get(key);
            if (null == wxPayService) {
                log.error("从微信支付配置缓存中获取微信支付服务失败, wxPayService is null");
                throw new ServiceException("从微信支付配置缓存中获取微信支付服务失败, wxPayService is null");
            }
        }

        return wxPayService;
    }

    private void initWxPayServiceMap() {
        wxPayServiceMap.clear();
        //查询数据库获取
        List<AppConfig> list = new ArrayList<>();
        list.add(new AppConfig("wxba30f2ef0e485274", "1540637701", "A4659A63476446D29526D8FC78BJLEGO"));
        list.add(new AppConfig("wx8c9148cee130fba4", "1540637701", "A4659A63476446D29526D8FC78BJLEGO"));

        for (AppConfig appConfig : list) {
            String appId = appConfig.getAppId().trim();
            String mchId = appConfig.getMchId().trim();
            String key = appConfig.getAppId() + "_" + appConfig.getMchId();
            WxPayConfig payConfig = new WxPayConfig();
            payConfig.setAppId(appId);
            payConfig.setMchId(mchId);
            payConfig.setMchKey(appConfig.getMchKey().trim());
            // 可以指定是否使用沙箱环境
            payConfig.setUseSandboxEnv(false);

            WxPayService wxPayService = new WxPayServiceImpl();
            wxPayService.setConfig(payConfig);

            if (!wxPayServiceMap.containsKey(key)) {
                wxPayServiceMap.put(key, wxPayService);
            }
        }
    }

    @Data
    @AllArgsConstructor
    public static class AppConfig {
        /**
         * 微信公众号或者小程序等的appid
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
    }
}
