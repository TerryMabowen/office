package com.mbw.office.demo.weixin.service;

import com.github.binarywang.wxpay.bean.result.WxPayBillResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Mabowen
 * @date 2020-07-17 15:04
 */
@Slf4j
@Service
public class WeiXinPayService {
    @Autowired
    private WxPayService wxPayService;

    public void downloadBill(String billDate, String billType, String tarType, String deviceInfo) throws WxPayException {
        WxPayBillResult wxPayBillResult = wxPayService.downloadBill(billDate, billType, tarType, deviceInfo);

        System.out.println(wxPayBillResult);
    }
}
