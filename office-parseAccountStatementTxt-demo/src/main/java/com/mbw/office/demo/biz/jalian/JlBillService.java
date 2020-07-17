package com.mbw.office.demo.biz.jalian;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.mbw.office.common.util.reflection.ReflectUtil;
import com.mbw.office.common.util.string.StringUtil;
import com.mbw.office.demo.biz.jalian.file.ReadTxtService;
import com.mbw.office.demo.biz.jalian.model.AccountStatementData;
import com.mbw.office.demo.biz.jalian.resource.ReadPropertiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
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

    public List<AccountStatementData> parse(List<String> lineList, String[] fields) throws IllegalAccessException {
        List<AccountStatementData> dataList = new ArrayList<>();
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

                AccountStatementData data = BeanUtil.mapToBean(lineMap, AccountStatementData.class, true);

                if (ReflectUtil.judgeAllFieldsIsNotNull(data)) {
                    dataList.add(data);
                }
            }
        }

        return dataList;
    }
}
