package com.mbw.office.demo.biz.weixin.service;

import com.github.binarywang.wxpay.bean.request.WxPayDownloadBillRequest;
import com.github.binarywang.wxpay.bean.result.WxPayBillInfo;
import com.github.binarywang.wxpay.bean.result.WxPayBillResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.common.util.bean.BeanKit;
import com.mbw.office.common.util.date.DateUtil;
import com.mbw.office.demo.biz.weixin.WxConfigFactory;
import com.mbw.office.demo.biz.weixin.model.WxBill;
import com.mbw.office.demo.biz.weixin.model.WxBillDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Mabowen
 * @date 2020-07-17 15:04
 */
@Slf4j
@Service
public class WxBillService {
    @Autowired
    private WxConfigFactory wxConfigFactory;

    public List<WxBill> downloadBill(WxPayDownloadBillRequest request, String appIdMchId) {
        try {
            WxPayService wxPayService = wxConfigFactory.getWxPayService(appIdMchId);

            WxPayBillResult wxPayBillResult = wxPayService.downloadBill(request);
            List<WxPayBillInfo> billInfoList = wxPayBillResult.getBillInfoList();
            Map<String, List<WxPayBillInfo>> billInfoGroup = billInfoList.stream().collect(Collectors.groupingBy(bill -> bill.getAppId() + "_" + bill.getMchId()));

            return billInfoGroup.keySet().stream().map(key -> {
                String[] split = key.split("_");
                String appId = split[0];
                String mchId = split[1];
                List<WxPayBillInfo> wxPayBillInfos = billInfoGroup.get(key);
                //注意BeanUtil.copyPropertiesFromBean2Bean()和new BeetlReformer<>().toVO()不能讲str转换为日期类型
                List<WxBillDetail> wxBillDetails = BeanKit.toBeans(wxPayBillInfos, WxBillDetail.class);
                WxBill wxBill = new WxBill();
                wxBill.setAppId(appId);
                wxBill.setMchId(mchId);
                wxBill.setBillDate(DateUtil.parse(request.getBillDate(), "yyyyMMdd"));
                wxBill.setBillType(request.getBillType());
                wxBill.setRecords(wxBillDetails);
                wxBill.setTotalRecord(wxBillDetails.size());

                BigDecimal totalFee = wxBillDetails.stream().filter(bill -> bill.getTotalFee() != null).map(WxBillDetail::getTotalFee).reduce(BigDecimal.ZERO, BigDecimal::add);
                wxBill.setTotalFee(totalFee);

                BigDecimal totalRefundFee = wxBillDetails.stream().filter(bill -> bill.getSettlementRefundFee() != null).map(WxBillDetail::getSettlementRefundFee).reduce(BigDecimal.ZERO, BigDecimal::add);
                wxBill.setTotalRefundFee(totalRefundFee);

                BigDecimal totalCouponFee = wxBillDetails.stream().filter(bill -> bill.getCouponFee() != null).map(WxBillDetail::getCouponFee).reduce(BigDecimal.ZERO, BigDecimal::add);
                wxBill.setTotalCouponFee(totalCouponFee);

                BigDecimal totalPoundageFee = wxBillDetails.stream().filter(bill -> bill.getPoundage() != null).map(WxBillDetail::getPoundage).reduce(BigDecimal.ZERO, BigDecimal::add);
                wxBill.setTotalPoundageFee(totalPoundageFee);

                BigDecimal totalAmount = wxBillDetails.stream().filter(bill -> bill.getTotalAmount() != null).map(WxBillDetail::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                wxBill.setTotalAmount(totalAmount);

                BigDecimal totalAppliedRefundFee = wxBillDetails.stream().filter(bill -> bill.getAppliedRefundAmount() != null).map(WxBillDetail::getAppliedRefundAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                wxBill.setTotalAppliedRefundFee(totalAppliedRefundFee);

                return wxBill;
            }).collect(Collectors.toList());
        } catch (WxPayException e) {
            if ("No Bill Exist".equals(e.getReturnMsg())) {
                log.info("账单不存在");
                return Collections.emptyList();
            } else {
                log.error(e.getReturnMsg());
                throw new ServiceException(e.getReturnMsg(), e);
            }
        }
    }
}
