package com.mbw.office.demo.biz.jalian;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.mbw.office.common.util.date.DateUtil;
import com.mbw.office.common.util.decimal.BigDecimalUtil;
import com.mbw.office.common.util.reflection.ReflectUtil;
import com.mbw.office.common.util.string.StringUtil;
import com.mbw.office.demo.biz.jalian.file.ReadTxtService;
import com.mbw.office.demo.biz.jalian.model.JlBill;
import com.mbw.office.demo.biz.jalian.model.JlBillDetail;
import com.mbw.office.demo.biz.jalian.resource.ReadPropertiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mabowen
 * @date 2020-07-17 10:46
 */
@Slf4j
@Service
public class JlBillService {
    @Autowired
    private ReadPropertiesService readPropertiesDemo;

    @Autowired
    private ReadTxtService readTxtDemo;

    public List<JlBill> getJlBill(String path) {
        try {
            String fileName = getFileName(path);
            String appId = getAppId(fileName);
            Date billDate = getBillDate(fileName);

            List<JlBillDetail> jlBills = getJlBills(path);
            Map<String, List<JlBillDetail>> jlBillGroup = jlBills.stream().collect(Collectors.groupingBy(bill -> appId + "_" + bill.getMchId()));

            return jlBillGroup.keySet().stream().map(key -> {
                String[] split = key.split("_");
                String mcnId = split[1];
                List<JlBillDetail> jlBillDetails = jlBillGroup.get(key);
                JlBill jlBill = new JlBill();
                jlBill.setAppId(appId);
                jlBill.setMchId(mcnId);
                jlBill.setFileName(fileName);
                jlBill.setFilePath(path);
                jlBill.setBillDate(billDate);
                jlBill.setRecords(jlBillDetails);
                jlBill.setTotalRecord(jlBillDetails.size());

                BigDecimal totalFee = jlBillDetails.stream().filter(bill -> bill.getTradeMoney() != null).map(JlBillDetail::getTradeMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
                jlBill.setTotalFee(totalFee);

                BigDecimal totalCommissions = jlBillDetails.stream().filter(bill -> bill.getCommissions() != null).map(JlBillDetail::getCommissions).reduce(BigDecimal.ZERO, BigDecimal::add);
                jlBill.setTotalCommissions(totalCommissions);

                BigDecimal totalChannelCommissions = jlBillDetails.stream().filter(bill -> bill.getChannelCommissions() != null).map(JlBillDetail::getChannelCommissions).reduce(BigDecimal.ZERO, BigDecimal::add);
                jlBill.setTotalChannelCommissions(totalChannelCommissions);

                BigDecimal totalExtraCommissions = jlBillDetails.stream().filter(bill -> bill.getExtraCommissions() != null).map(JlBillDetail::getExtraCommissions).reduce(BigDecimal.ZERO, BigDecimal::add);
                jlBill.setTotalExtraCommissions(totalExtraCommissions);

                BigDecimal totalChannelFee = jlBillDetails.stream().filter(bill -> bill.getChannelFee() != null).map(JlBillDetail::getChannelFee).reduce(BigDecimal.ZERO, BigDecimal::add);
                jlBill.setTotalChannelFee(totalChannelFee);

                return jlBill;
            }).collect(Collectors.toList());

        } catch (Exception e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<JlBillDetail> getJlBills(String path) throws IllegalAccessException {
        List<String> lineList = getLineList(path);
        String[] fields = getFields();
        return parse(lineList, fields);
    }

    public List<String> getLineList(String path) {
        List<String> lineList = new ArrayList<>();
        try {
            File txtFile = readTxtDemo.getTxtFile(path);
            lineList = readTxtDemo.readTxt(txtFile);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return lineList;
    }

    public String[] getFields() {
        return readPropertiesDemo.getFields();
    }

    public List<JlBillDetail> parse(List<String> lineList, String[] fields) throws IllegalAccessException {
        List<JlBillDetail> dataList = new ArrayList<>();
        if (CollUtil.isNotEmpty(lineList) && ArrayUtil.isNotEmpty(fields)) {
            for (String line : lineList) {
                Map<String, Object> lineMap = new HashMap<>();
                String[] split = line.split("\\|");
                if (split.length > 0) {
                    for (int i = 0; i < fields.length; i++) {
                        String field = fields[i];
                        String key = StringUtil.underline2Camel(field);
                        String value = split[i];
                        if (StrUtil.isNotBlank(key) && !lineMap.containsKey(key)) {
                            lineMap.put(key, StrUtil.isNotBlank(value) ? value : null);
                        }
                    }
                }

                JlBillDetail data = BeanUtil.mapToBean(lineMap, JlBillDetail.class, true);
                if (data.getTradeMoney() != null) {
                    data.setTradeMoney(BigDecimalUtil.pointsToYuan(data.getTradeMoney()));
                }
                if (data.getCommissions() != null) {
                    data.setCommissions(BigDecimalUtil.pointsToYuan(data.getCommissions()));
                }
                if (data.getChannelCommissions() != null) {
                    data.setChannelCommissions(BigDecimalUtil.pointsToYuan(data.getChannelCommissions()));
                }
                if (data.getExtraCommissions() != null) {
                    data.setExtraCommissions(BigDecimalUtil.pointsToYuan(data.getExtraCommissions()));
                }
                if (data.getChannelFee() != null) {
                    data.setChannelFee(BigDecimalUtil.pointsToYuan(data.getChannelFee()));
                }

                if (ReflectUtil.judgeAllFieldsIsNotNull(data)) {
                    dataList.add(data);
                }
            }
        }

        return dataList;
    }

    public String[] getFields(String fieldName, String field) {
        if (StrUtil.isNotBlank(fieldName) && StrUtil.isNotBlank(field)) {
            ResourceBundle bundle = readPropertiesDemo.readProperties(fieldName);
            String valueByKey = readPropertiesDemo.getValueByKey(bundle, field);
            return readPropertiesDemo.getFields(valueByKey);
        }

        return new String[0];
    }

    private String getFileName(String path) {
        if (StrUtil.isNotBlank(path) && path.lastIndexOf(File.separator) != -1) {
            return path.substring(path.lastIndexOf(File.separator) + 1);
        }

        return StrUtil.EMPTY;
    }

    private String getAppId(String fileName) {
        if (StrUtil.isNotBlank(fileName) && fileName.contains("_")) {
            return fileName.substring(0, fileName.indexOf("_"));
        }

        return StrUtil.EMPTY;
    }

    private Date getBillDate(String fileName) {
        if (StrUtil.isNotBlank(fileName) && fileName.indexOf("_", 0) != -1 && fileName.lastIndexOf("_") != -1) {
            String billDate = fileName.substring(fileName.indexOf("_") + 1, fileName.lastIndexOf("_"));
            return DateUtil.parse(billDate, "yyyyMMdd");
        }

        return null;
    }
}
