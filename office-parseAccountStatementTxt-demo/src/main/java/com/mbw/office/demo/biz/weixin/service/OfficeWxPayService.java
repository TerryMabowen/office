package com.mbw.office.demo.biz.weixin.service;

import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.demo.biz.weixin.WxConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-08-17 16:10
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class OfficeWxPayService {
    @Autowired
    private WxConfigFactory wxConfigFactory;

    /**
     * 查询订单
     * @author Mabowen
     * @date 2020-08-17 16:13
     * @param transactionId 微信订单ID
     * @param outTradeNo 商户订单号
     */
    public List<WxPayOrderQueryResult> getWxOrderByNo(String transactionId, String outTradeNo) {
        //通过查询数据库或者根据传递的参数操作
        List<WxConfigFactory.AppConfig> list = new ArrayList<>();
        list.add(new WxConfigFactory.AppConfig("wxba30f2ef0e485274", "1540637701", "A4659A63476446D29526D8FC78BJLEGO"));
        list.add(new WxConfigFactory.AppConfig("wx8c9148cee130fba4", "1540637701", "A4659A63476446D29526D8FC78BJLEGO"));

        try {
            List<WxPayOrderQueryResult> results = new ArrayList<>();
            for (WxConfigFactory.AppConfig appConfig : list) {
                String key = appConfig.getAppId() + "_" + appConfig.getMchId();
                WxPayService wxPayService = wxConfigFactory.getWxPayService(key);
                WxPayOrderQueryResult wxPayOrderQueryResult = wxPayService.queryOrder(transactionId, outTradeNo);
                if ("SUCCESS".equalsIgnoreCase(wxPayOrderQueryResult.getReturnCode())) {
                    results.add(wxPayOrderQueryResult);
                } else {
                    throw new ServiceException(wxPayOrderQueryResult.getReturnMsg());
                }
            }
            return results;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
