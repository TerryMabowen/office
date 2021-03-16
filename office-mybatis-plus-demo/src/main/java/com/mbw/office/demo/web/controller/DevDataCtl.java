package com.mbw.office.demo.web.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mbw.office.common.lang.response.ResponseResults;
import com.mbw.office.common.web.base.BaseDataCtl;
import com.mbw.office.demo.service.order.OrderService;
import com.mbw.office.demo.service.order.vo.OrderPaidRecordVO;
import com.mbw.office.demo.service.order.vo.ProductBillVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-03-05 13:57
 */
@RestController
@RequestMapping("/dev")
public class DevDataCtl extends BaseDataCtl {
    @Autowired
    private OrderService orderService;

    @GetMapping("/f1")
    public ResponseResults f1(@RequestParam(value = "p1", required = false) String orderNo) {
        //1122-0550-8318-2921
        List<OrderPaidRecordVO> records = orderService.listRecord1s();
        List<OrderPaidRecordVO> results = orderService.handleRecords(records);

        if (StrUtil.isNotBlank(orderNo)) {
            return ResponseResults.newSuccess(results.stream().filter(vo -> orderNo.equals(vo.getOrderNumber())).collect(Collectors.toList()));
        }

        return ResponseResults.newSuccess(results);
    }

    @GetMapping("/f2")
    public ResponseResults f2(@RequestParam(value = "p1", required = false) String orderNo) {
        List<OrderPaidRecordVO> records = orderService.listRecord2s();
        List<OrderPaidRecordVO> results = orderService.handleRecords(records);

        if (StrUtil.isNotBlank(orderNo)) {
            return ResponseResults.newSuccess(results.stream().filter(vo -> orderNo.equals(vo.getOrderNumber())).collect(Collectors.toList()));
        }

        return ResponseResults.newSuccess(results);
    }

    @GetMapping("/f3")
    public ResponseResults f3(@RequestParam(value = "p1", required = false) String orderNo) {
        List<OrderPaidRecordVO> records = orderService.listRecord1s();

        if (StrUtil.isNotBlank(orderNo)) {
            return ResponseResults.newSuccess(records.stream().filter(vo -> orderNo.equals(vo.getOrderNumber())).collect(Collectors.toList()));
        }

        return ResponseResults.newSuccess(records);
    }

    @GetMapping("/f4")
    public ResponseResults f4(@RequestParam(value = "p1", required = false) String orderNo) {
        List<OrderPaidRecordVO> records = orderService.listRecord2s();

        if (StrUtil.isNotBlank(orderNo)) {
            return ResponseResults.newSuccess(records.stream().filter(vo -> orderNo.equals(vo.getOrderNumber())).collect(Collectors.toList()));
        }

        return ResponseResults.newSuccess(records);
    }

    @GetMapping("/f5")
    public ResponseResults f5(@RequestParam("p1") Integer type) {
        List<OrderPaidRecordVO> results = new ArrayList<>();
        if (1 == type) {
            List<OrderPaidRecordVO> records = orderService.listRecord1s();
            results = orderService.handleRecords(records);
        } else {
            List<OrderPaidRecordVO> records = orderService.listRecord2s();
            results = orderService.handleRecords(records);
        }

        List<OrderPaidRecordVO> errors = new ArrayList<>();
        if (CollUtil.isNotEmpty(results)) {
            for (OrderPaidRecordVO result : results) {
                BigDecimal productPrice = result.getProductBills().stream()
                        .map(ProductBillVO::getProductPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                if (result.getPaidMoney().compareTo(productPrice) != 0) {
                    errors.add(result);
                }
            }
        }

        return ResponseResults.newSuccess(errors);
    }
}
