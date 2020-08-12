package com.mbw.office.demo.biz.department;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.common.util.collection.CollectionUtil;
import com.mbw.office.common.util.date.DateUtil;
import com.mbw.office.demo.biz.department.bo.JlBillDetailBO;
import com.mbw.office.demo.biz.department.bo.WxBillDetailBO;
import com.mbw.office.demo.biz.department.domain.Department;
import com.mbw.office.demo.biz.department.dto.DepartmentAmountReceivableDTO;
import com.mbw.office.demo.biz.department.vo.DepartmentAmountReceivableVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mabowen
 * @date 2020-08-06 10:42
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class DepartmentReceivableService {
    @Autowired
    private BaseDataService baseDataService;

    private String[] months = {"2020-01", "2020-02", "2020-03", "2020-04", "2020-05", "2020-06", "2020-07", "2020-08", "2020-09", "2020-10", "2020-11", "2020-12"};

    public List<DepartmentAmountReceivableVO> listDepartmentReceivables(String month) {
        List<Department> departments = baseDataService.getDepartments();
        List<DepartmentAmountReceivableVO> vos = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(departments)) {
            for (Department department : departments) {
                DepartmentAmountReceivableVO vo = getDepartmentReceivables(month, department);
                vos.add(vo);
            }
        }
        return vos;
    }

    public List<DepartmentAmountReceivableVO> listDepartmentReceivables() {
        List<Department> departments = baseDataService.getDepartments();
        List<DepartmentAmountReceivableVO> vos = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(departments)) {
            for (Department department : departments) {
                List<DepartmentAmountReceivableVO> dvos = new ArrayList<>();
                for (String month : months) {
                    DepartmentAmountReceivableVO vo = getDepartmentReceivables(month, department);
                    dvos.add(vo);
                }

                if (CollectionUtil.isNotEmpty(dvos)) {
                    vos.addAll(dvos);
                }
            }
        }

        //排序
        return vos.stream()
                .sorted(
                    Comparator.comparing(DepartmentAmountReceivableVO::getDepartmentId)
                            .thenComparing(DepartmentAmountReceivableVO::getStartDate)
                            .reversed()
                ).collect(Collectors.toList());
    }

    private DepartmentAmountReceivableVO getDepartmentReceivables(String month, Department department) {
        DepartmentAmountReceivableVO vo = new DepartmentAmountReceivableVO();
        Date monthBegin = DateUtil.parseShort(DateUtil.getMonthBegin(DateUtil.parse(month, "yyyy-MM")));
        Date monthEnd = DateUtil.parseShort(DateUtil.getMonthEnd(DateUtil.parse(month, "yyyy-MM")));
        vo.setDepartmentId(department.getId());
        vo.setDepartmentName(department.getName());
        vo.setStartDate(monthBegin);
        vo.setEndDate(monthEnd);
        vo.setBudgetRuleType(department.getType());
        vo.setBudgetRuleDesc(department.getInChargeName());

        DepartmentAmountReceivableDTO dto = BeanUtil.toBean(vo, DepartmentAmountReceivableDTO.class);

        try {
            getDepartmentAmountReceivable(dto);
        } catch (Exception e) {
            log.error("getDepartmentAmountReceivable error: " + e.getMessage(), e);
            throw new ServiceException("getDepartmentAmountReceivable error: " + e.getMessage(), e);
        }

        vo.setTotalAmountReceivable(dto.getAmountReceivable());
        vo.setTotalRecords(dto.getTotalRecords());

        return vo;
    }

    private void getDepartmentAmountReceivable(DepartmentAmountReceivableDTO dto) {
        //TODO 以下通过sql根据条件，部门id，开始时间，结束时间查询, 这里模拟一些数据
        List<JlBillDetailBO> jlBills = baseDataService.getJlBillMap().get(dto.getDepartmentId());
        BigDecimal totalTradeMoney = BigDecimal.ZERO;
        long count = 0;
        if (CollUtil.isNotEmpty(jlBills)) {
            for (JlBillDetailBO jlBill : jlBills) {
                if (jlBill.getSettlementDate().compareTo(dto.getStartDate()) >= 0 && jlBill.getSettlementDate().compareTo(dto.getEndDate()) <= 0) {
                    if (jlBill.getTradeMoney() != null) {
                        totalTradeMoney = totalTradeMoney.add(jlBill.getTradeMoney());
                    }

                    count++;
                }
            }
        }

        List<WxBillDetailBO> wxBills = baseDataService.getWxBillMap().get(dto.getDepartmentId());
        if (CollUtil.isNotEmpty(wxBills)) {
            for (WxBillDetailBO wxBill : wxBills) {
                if (wxBill.getSettlementDate().compareTo(dto.getStartDate()) >= 0 && wxBill.getSettlementDate().compareTo(dto.getEndDate()) <= 0) {
                    if (wxBill.getTotalFee() != null) {
                        totalTradeMoney = totalTradeMoney.add(wxBill.getTotalFee());
                    }

                    count++;
                }
            }
        }

        dto.setAmountReceivable(totalTradeMoney);
        dto.setTotalRecords(count);
    }
}
