package com.mbw.office.demo.biz.department;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.common.util.collection.CollectionUtil;
import com.mbw.office.common.util.date.DateUtil;
import com.mbw.office.common.util.io.FileUtil;
import com.mbw.office.common.util.json.GsonUtil;
import com.mbw.office.demo.biz.department.bo.JlBillDetailBO;
import com.mbw.office.demo.biz.department.bo.WxBillDetailBO;
import com.mbw.office.demo.biz.department.domain.Department;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mabowen
 * @date 2020-08-06 10:24
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class BaseDataService {
    @Getter
    private List<Department> departments = new ArrayList<>();

    @Getter
    private List<JlBillDetailBO> jlBills = new ArrayList<>();

    @Getter
    private Map<Long, List<JlBillDetailBO>> jlBillMap = new HashMap<>();

    @Getter
    private List<WxBillDetailBO> wxBills = new ArrayList<>();

    @Getter
    private Map<Long, List<WxBillDetailBO>> wxBillMap = new HashMap<>();

    @PostConstruct
    public void init() {
        log.warn("Begin departments and jlBills and wxBills init, {} start", DateUtil.formatDefault(new Date()));
        listDepartments();
        listJlBillsFromFile();
        listWxBillsFromFile();
        listJlBills();
        listWxBills();
        log.warn("End departments and jlBills and wxBills init, {} end", DateUtil.formatDefault(new Date()));
    }

    private void listDepartments() {
        File file = FileUtil.getFile("/Users/apple_22/Desktop/MYSELF_project/TerryMabowen/office-parent/office-parseAccountStatementTxt-demo/src/main/resources/json/departments.json");
        String json = FileUtil.readFileToString(file, "UTF-8").replaceAll("[\r\n]", "");
        List<Map<String, Object>> maps = GsonUtil.jsonToListMap(json);
        List<Department> departments = new ArrayList<>();
        if (CollUtil.isNotEmpty(maps)) {
            for (Map<String, Object> map : maps) {
                Department department = BeanUtil.mapToBean(map, Department.class, true);
                departments.add(department);
            }
        }

        this.departments = departments;
    }

    private void listJlBillsFromFile() {
        File file = FileUtil.getFile("/Users/apple_22/Desktop/MYSELF_project/TerryMabowen/office-parent/office-parseAccountStatementTxt-demo/src/main/resources/json/jlBillDetails.json");
        String json = FileUtil.readFileToString(file, "UTF-8").replaceAll("[\r\n]", "");
        List<Map<String, Object>> maps = GsonUtil.jsonToListMap(json);
        List<JlBillDetailBO> bos = new ArrayList<>();
        if (CollUtil.isNotEmpty(maps)) {
            for (Map<String, Object> map : maps) {
                JlBillDetailBO bo = BeanUtil.mapToBean(map, JlBillDetailBO.class, true);
                bos.add(bo);
            }
        }

        this.jlBills = bos;
    }

    private void listWxBillsFromFile() {
        File file = FileUtil.getFile("/Users/apple_22/Desktop/MYSELF_project/TerryMabowen/office-parent/office-parseAccountStatementTxt-demo/src/main/resources/json/wxBillDetails.json");
        String json = FileUtil.readFileToString(file, "UTF-8").replaceAll("[\r\n]", "");
        List<Map<String, Object>> maps = GsonUtil.jsonToListMap(json);
        List<WxBillDetailBO> bos = new ArrayList<>();
        if (CollUtil.isNotEmpty(maps)) {
            for (Map<String, Object> map : maps) {
                WxBillDetailBO bo = BeanUtil.mapToBean(map, WxBillDetailBO.class, true);
                bos.add(bo);
            }
        }

        this.wxBills = bos;
    }

    private void listJlBills() {
        List<JlBillDetailBO> bos = getJlBills();
        if (CollectionUtil.isNotEmpty(bos)) {
            Random rd = new Random();
            for (JlBillDetailBO bo : bos) {
                int index = rd.nextInt(149);
                Department department = getDepartmentByIndex(index);
                if (department != null) {
                    bo.setDepartmentId(department.getId());
                    bo.setDepartmentName(department.getName());
                }
            }
        }

        Map<Long, List<JlBillDetailBO>> jlBillMap = bos.stream().collect(Collectors.groupingBy(JlBillDetailBO::getDepartmentId));

        if (CollUtil.isNotEmpty(jlBillMap)) {
            this.jlBillMap = jlBillMap;
        }
    }

    private void listWxBills() {
        List<WxBillDetailBO> bos = getWxBills();
        Random rd = new Random();
        if (CollectionUtil.isNotEmpty(bos)) {
            for (WxBillDetailBO bo : bos) {
                int index = rd.nextInt(149);
                Department department = getDepartmentByIndex(index);
                if (department != null) {
                    bo.setDepartmentId(department.getId());
                    bo.setDepartmentName(department.getName());
                }
            }
        }

        Map<Long, List<WxBillDetailBO>> wxBillMap = bos.stream().collect(Collectors.groupingBy(WxBillDetailBO::getDepartmentId));
        if (CollUtil.isNotEmpty(wxBillMap)) {
            this.wxBillMap = wxBillMap;
        }
    }

    private Department getDepartmentByIndex(int index) {
        try {
            List<Department> departments = getDepartments();
            return departments.get(index);
        } catch (Exception e) {
            throw new ServiceException("departments have not index: " + index, e);
        }
    }
}
