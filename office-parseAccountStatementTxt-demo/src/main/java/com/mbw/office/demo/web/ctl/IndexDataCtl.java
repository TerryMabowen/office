package com.mbw.office.demo.web.ctl;

import com.github.binarywang.wxpay.bean.request.WxPayDownloadBillRequest;
import com.github.binarywang.wxpay.bean.result.WxPayBillResult;
import com.mbw.office.common.lang.response.ResponseResults;
import com.mbw.office.demo.biz.jalian.JlBillService;
import com.mbw.office.demo.biz.jalian.model.AccountStatementData;
import com.mbw.office.demo.biz.weixin.service.WeiXinPayService;
import com.mbw.office.demo.web.ctl.fb.BillFB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-17 15:02
 */
@RestController
@RequestMapping("/index")
public class IndexDataCtl {
    private static final String ROOT_PATH = "/Users/apple_22/Desktop/F100/钉钉中台-财务系统/每日对账单/";

    @Autowired
    private WeiXinPayService weiXinPayService;

    @Autowired
    private JlBillService jlBillService;

    @GetMapping("/jlBill")
    public ResponseResults jlBills(@RequestParam("filename") String filename) {
        try {
            List<String> lineList = jlBillService.getLineList(ROOT_PATH + filename);
//            String[] fields = jlBillService.getFields("field", "column_eng_names");
            String[] fields = jlBillService.getFields();
            List<AccountStatementData> statementData = jlBillService.parse(lineList, fields);
            return ResponseResults.newSuccess()
                    .setData(statementData);
        } catch (Exception e) {
            return ResponseResults.newFailed()
                    .setMessage(e.getMessage());
        }
    }

    /**
     * <pre>
     * 下载对账单
     * 商户可以通过该接口下载历史交易清单。比如掉单、系统错误等导致商户侧和微信侧数据不一致，通过对账单核对后可校正支付状态。
     * 注意：
     * 1、微信侧未成功下单的交易不会出现在对账单中。支付成功后撤销的交易会出现在对账单中，跟原支付单订单号一致，bill_type为REVOKED；
     * 2、微信在次日9点启动生成前一天的对账单，建议商户10点后再获取；
     * 3、对账单中涉及金额的字段单位为“元”。
     * 4、对账单接口只能下载三个月以内的账单。
     * 接口链接：https://api.mch.weixin.qq.com/pay/downloadbill
     * 详情请见: <a href="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_6">下载对账单</a>
     * </pre>
     *
     * @param fb      billDate   对账单日期 bill_date	下载对账单的日期，格式：20140603
     *                billType   账单类型   bill_type	ALL，返回当日所有订单信息，默认值，SUCCESS，返回当日成功支付的订单，REFUND，返回当日退款订单
     *                tarType    压缩账单    tar_type	非必传参数，固定值：GZIP，返回格式为.gzip的压缩包账单。不传则默认为数据流形式。
     *                deviceInfo 设备号	   device_info	非必传参数，终端设备号
     * @return 保存到本地的临时文件
     */
    @GetMapping("/wxBill")
    public ResponseResults downloadBill(BillFB fb) {
        try {
            WxPayDownloadBillRequest request = new WxPayDownloadBillRequest();
            request.setBillDate(fb.getBillDate());
            request.setBillType(fb.getBillType());

            WxPayBillResult wxPayBillResult = weiXinPayService.downloadBill(request);

            return ResponseResults.newSuccess()
                    .setData(wxPayBillResult);
        } catch (Exception e) {
            return ResponseResults.newFailed()
                    .setMessage(e.getMessage());
        }
    }
}
