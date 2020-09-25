package com.mbw.office.demo.biz.service.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.mbw.office.common.constant.StringInfoConstants;
import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.demo.biz.service.config.vo.WxPayProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信支付api配置工厂
 *
 * @author Mabowen
 * @date 2020-07-28 14:45
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class WxPayApiConfigFactory {
    @Autowired
    private com.mbw.office.demo.biz.service.config.WxPayConfig wxPayConfig;

    private static Map<String, WxPayService> wxPayServiceMap = new HashMap<>();

    @Async
    @Scheduled(initialDelay = 5 * 1000, fixedDelay = 2 * 60 * 60 * 1000)
    public void init() {
        initWxPayServiceMap();
    }

    public synchronized WxPayService getWxPayServiceByKey(String key) {
        if (MapUtils.isEmpty(wxPayServiceMap)) {
            initWxPayServiceMap();
        }

        WxPayService wxPayService = wxPayServiceMap.get(key);
        if (null == wxPayService) {
            //TODO
            initWxPayServiceMap();
            wxPayService = wxPayServiceMap.get(key);
            if (null == wxPayService) {
                log.error("从微信支付配置缓存中获取微信支付服务失败，wxPayService is null");
                throw new ServiceException("从微信支付配置缓存中获取微信支付服务失败，wxPayService is null");
            }
        }

        return wxPayService;
    }

    private void initWxPayServiceMap() {
        log.info("初始化微信支付服务缓存");
        wxPayServiceMap.clear();
        List<WxPayProperty> wxPayProperties = wxPayConfig.listWxPayMch();
        wxPayProperties.forEach(account -> {
            String key = account.getAppId().trim() + StringInfoConstants.UNDERLINE + account.getMchId().trim();

            WxPayConfig payConfig = new WxPayConfig();
            payConfig.setAppId(account.getAppId().trim());
            payConfig.setMchId(account.getMchId().trim());
            payConfig.setMchKey(account.getMchKey().trim());
            // 可以指定是否使用沙箱环境
            payConfig.setUseSandboxEnv(false);
            WxPayService wxPayService = new WxPayServiceImpl();
            wxPayService.setConfig(payConfig);

            if (!wxPayServiceMap.containsKey(key)) {
                wxPayServiceMap.put(key, wxPayService);
            }
        });
    }
}
