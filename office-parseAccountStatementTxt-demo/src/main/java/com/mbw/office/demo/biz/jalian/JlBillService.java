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
import com.mbw.office.demo.biz.jalian.model.JlBillDetail;
import com.mbw.office.demo.biz.jalian.model.JlBill;
import com.mbw.office.demo.biz.jalian.resource.ReadPropertiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

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

    public String[] getFields(String fieldName, String field) {
        if (StrUtil.isNotBlank(fieldName) && StrUtil.isNotBlank(field)) {
            ResourceBundle bundle = readPropertiesDemo.readProperties(fieldName);
            String valueByKey = readPropertiesDemo.getValueByKey(bundle, field);
            return readPropertiesDemo.getFields(valueByKey);
        }

        return new String[0];
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

    public JlBill getJlBill(String path) throws IllegalAccessException {
        JlBill jlBill = new JlBill();
        jlBill.setFileName(getFileName(path));
        jlBill.setFilePath(path);
        jlBill.setAppId(getAppId(jlBill.getFileName()));
        jlBill.setBillDate(getBillDate(jlBill.getFileName()));

        List<String> lineList = getLineList(path);
        String[] fields = getFields();
        List<JlBillDetail> data = parse(lineList, fields);
        jlBill.setRecords(data);
        jlBill.setTotalRecord(data.size());
        BigDecimal totalFee = data.stream().map(JlBillDetail::getTradeMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
        jlBill.setTotalFee(totalFee);

        return jlBill;
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
