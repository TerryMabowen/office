package com.mbw.office.demo.biz.service.config;

import com.google.common.collect.Lists;
import com.mbw.office.demo.biz.service.config.vo.WxPayProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 微信支付配置
 *
 * @author Mabowen
 * @date 2020-09-24 16:28
 */
@Slf4j
@Service
public class WxPayConfig {

    /**
     * 获取微信商户
     * @author Mabowen
     * @date 2020-09-24 16:34
     */
    public List<WxPayProperty> listWxPayMch() {
        List<WxPayProperty> list = Lists.newArrayList();

        //普通商户
//        WxPayProperty p1 = WxPayProperty.builder()
//                .appId("d")
//                .mchId("d")
//                .mchKey("d")
//                .isServiceMch(false)
//                .build();
//        list.add(p1);

        //服务商商户
        WxPayProperty p2 = WxPayProperty.builder()
                .appId("d")
                .mchId("d")
                .mchKey("d")
                .isServiceMch(false)
                .build();
        list.add(p2);

        return list;
    }
}
