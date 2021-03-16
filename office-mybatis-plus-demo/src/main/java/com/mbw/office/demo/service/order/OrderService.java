package com.mbw.office.demo.service.order;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.demo.service.order.vo.OrderPaidRecordVO;
import com.mbw.office.demo.service.order.vo.ProductBillVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-03-05 13:53
 */
@Slf4j
@Service
public class OrderService {
    public List<OrderPaidRecordVO> handleRecords(List<OrderPaidRecordVO> records) {
        List<OrderPaidRecordVO> results = Lists.newArrayList();
        if (CollUtil.isNotEmpty(records)) {
            List<OrderPaidRecordVO> confirmRecords = records.stream().filter(vo -> 1 == vo.getAccountantConfirmState() && 1 == vo.getStatus()).collect(Collectors.toList());
            System.out.println(String.format("已确认的记录总共 %d 条", confirmRecords.size()));
            if (CollUtil.isNotEmpty(confirmRecords)) {
                Map<String, List<OrderPaidRecordVO>> confirmMap = confirmRecords.stream().collect(Collectors.groupingBy(vo -> vo.getId() + "_" + vo.getOrderId()));
                List<OrderPaidRecordVO> confirmList = handleRecordGroup(confirmMap);
                System.out.println(String.format("分组整理后的已确认的记录总共 %d 条", confirmList.size()));
                results.addAll(confirmList);
            }


            List<OrderPaidRecordVO> deletedConfirmRecords = records.stream().filter(vo -> 1 == vo.getAccountantConfirmState() && 0 == vo.getStatus()).collect(Collectors.toList());
            System.out.println(String.format("已确认但是已删除的记录总共 %d 条", deletedConfirmRecords.size()));
            if (CollUtil.isNotEmpty(deletedConfirmRecords)) {
                Map<String, List<OrderPaidRecordVO>> deletedMap = deletedConfirmRecords.stream().collect(Collectors.groupingBy(vo -> vo.getId() + "_" + vo.getOrderId()));
                List<OrderPaidRecordVO> deletedList = handleRecordGroup(deletedMap);
                System.out.println(String.format("分组整理后的已删除的已确认的记录总共 %d 条", deletedList.size()));
                results.addAll(deletedList);
            }

            List<OrderPaidRecordVO> nonConfirmRecords = records.stream().filter(vo -> 1 != vo.getAccountantConfirmState()).collect(Collectors.toList());
            System.out.println(String.format("没有确认的记录总共 %d 条", nonConfirmRecords.size()));
            if (CollUtil.isNotEmpty(nonConfirmRecords)) {
                Map<String, List<OrderPaidRecordVO>> nonConfirmMap = nonConfirmRecords.stream().collect(Collectors.groupingBy(vo -> vo.getId() + "_" + vo.getOrderId()));
                List<OrderPaidRecordVO> nonConfirmList = handleRecordGroup(nonConfirmMap);
                System.out.println(String.format("分组整理后的没有确认的记录总共 %d 条", nonConfirmList.size()));
                results.addAll(nonConfirmList);
            }
        }

        System.out.println(String.format("处理后的结果：%s", results));

        return results;
    }

    private List<OrderPaidRecordVO> handleRecordGroup(Map<String, List<OrderPaidRecordVO>> groupMap) {
        List<OrderPaidRecordVO> results = Lists.newArrayList();
        if (MapUtil.isNotEmpty(groupMap)) {
            for (Map.Entry<String, List<OrderPaidRecordVO>> entry : groupMap.entrySet()) {
                List<OrderPaidRecordVO> value = entry.getValue();
                OrderPaidRecordVO firstRecord = value.get(0);
                OrderPaidRecordVO vo = new OrderPaidRecordVO();
                vo.setId(firstRecord.getId());
                vo.setDepartmentId(firstRecord.getDepartmentId());
                vo.setDepartmentName(firstRecord.getDepartmentName());
                vo.setOrderNumber(firstRecord.getOrderNumber());
                vo.setOrderId(firstRecord.getOrderId());
                vo.setOrderTotalMoney(firstRecord.getOrderTotalMoney());
                vo.setPaidMoney(firstRecord.getPaidMoney());
                vo.setServiceFee(firstRecord.getServiceFee());
                vo.setAccountantConfirmState(firstRecord.getAccountantConfirmState());
                vo.setPaidTime(firstRecord.getPaidTime());
                vo.setRecordRemark(firstRecord.getRecordRemark());
                vo.setStatus(firstRecord.getStatus());
                vo.setUpdateTime(firstRecord.getUpdateTime());
                vo.setProductBills(Lists.newArrayList());

                ProductBillVO courseBill = new ProductBillVO();
                courseBill.setProductType(1);
                courseBill.setProductPrice(BigDecimal.ZERO);

                ProductBillVO goodsBill = new ProductBillVO();
                goodsBill.setProductType(3);
                goodsBill.setProductPrice(BigDecimal.ZERO);

                if (3 == firstRecord.getOrderType()) {
                    //扩单商品金额
                    courseBill.setProductPrice(vo.getPaidMoney());
                } else {
                    for (OrderPaidRecordVO orderPaidRecordVO : value) {
                        if (orderPaidRecordVO.getProductType() != null) {
                            if (vo.getOrderTotalMoney().compareTo(BigDecimal.ZERO) != 0) {
                                BigDecimal coursePrice = BigDecimal.ZERO;
                                BigDecimal goodsPrice = BigDecimal.ZERO;
                                if (1 == orderPaidRecordVO.getProductType()) {
                                    //课程
                                    coursePrice = orderPaidRecordVO.getRealPrice()
                                            .multiply(vo.getPaidMoney())
                                            .multiply(new BigDecimal(orderPaidRecordVO.getProductNumber()))
                                            .divide(vo.getOrderTotalMoney(), 2, BigDecimal.ROUND_HALF_DOWN);
                                } else if (1 != orderPaidRecordVO.getProductType()) {
                                    //商品
                                    goodsPrice = orderPaidRecordVO.getRealPrice()
                                            .multiply(vo.getPaidMoney())
                                            .multiply(new BigDecimal(orderPaidRecordVO.getProductNumber()))
                                            .divide(vo.getOrderTotalMoney(), 2, BigDecimal.ROUND_HALF_DOWN);
                                }

                                courseBill.setProductPrice(courseBill.getProductPrice().add(coursePrice));
                                goodsBill.setProductPrice(goodsBill.getProductPrice().add(goodsPrice));
                                continue;
                            }
                        }

                        courseBill.setProductPrice(vo.getPaidMoney());
                    }
                }

                vo.getProductBills().add(courseBill);
                vo.getProductBills().add(goodsBill);

                results.add(vo);
            }
        }

        return results;
    }

    public List<OrderPaidRecordVO> listRecord1s() {
        List<OrderPaidRecordVO> records = new ArrayList<>();
        try {
            File file = ResourceUtils.getFile("classpath:json/order.json");

            JSONArray jsonArray = JSONUtil.readJSONArray(file, StandardCharsets.UTF_8);
            records = jsonArray.toList(OrderPaidRecordVO.class);
        } catch (FileNotFoundException | IORuntimeException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        System.out.println(String.format("record1总共 %d 条", records.size()));
        return records;
    }

    public List<OrderPaidRecordVO> listRecord2s() {
        List<OrderPaidRecordVO> records = new ArrayList<>();
        try {
            File file = ResourceUtils.getFile("classpath:json/order2.json");

            JSONArray jsonArray = JSONUtil.readJSONArray(file, StandardCharsets.UTF_8);
            records = jsonArray.toList(OrderPaidRecordVO.class);
        } catch (FileNotFoundException | IORuntimeException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        System.out.println(String.format("record2总共 %d 条", records.size()));
        return records;
    }
}
