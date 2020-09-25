package com.mbw.office.demo.biz.service;

import com.github.binarywang.wxpay.bean.request.WxPayDownloadBillRequest;
import com.github.binarywang.wxpay.bean.result.WxPayBillResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.demo.biz.service.config.WxPayApiConfigFactory;
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
    private WxPayApiConfigFactory factory;

    public WxPayBillResult downloadBill(String key, WxPayDownloadBillRequest request) {
        try {
            WxPayService wxPayService = factory.getWxPayServiceByKey(key);

            return wxPayService.downloadBill(request);
        } catch (WxPayException e) {
            if ("No Bill Exist".equals(e.getReturnMsg())) {
                log.error("账单不存在");
                return null;
            } else {
                throw new ServiceException("账单下载失败");
            }
        }
    }
}
