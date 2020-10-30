package com.mbw.office.dingtalk.admin.ctl;

import ai.bell.dingtalk.gw.provider.spi.bu.vo.BusinessUnitVO;
import ai.bell.dingtalk.gw.provider.spi.budget.statistics.dto.BudgetStatisticsSearchDTO;
import ai.bell.dingtalk.gw.provider.spi.budget.statistics.vo.BudgetStatisticsDailyVO;
import ai.bell.dingtalk.gw.provider.spi.budget.statistics.vo.BudgetStatisticsMonthlyVO;
import com.mbw.office.common.lang.response.ResponseResults;
import com.mbw.office.common.web.base.BaseDataCtl;
import com.mbw.office.dingtalk.biz.bu.DtBusinessUnitService;
import com.mbw.office.dingtalk.biz.budget.DtBudgetStatisticsService;
import com.mbw.office.dingtalk.biz.ding.DtDepartmentService;
import com.mbw.office.dingtalk.biz.ding.vo.DtDepartmentVO;
import com.mbw.office.dingtalk.biz.vbank.VbankDepartmentService;
import com.mbw.office.dingtalk.biz.vbank.vo.VbankDepartmentVO;
import com.smthit.dubbo.ResponseBean;
import com.smthit.dubbo.data.PageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-17 15:02
 */
@Slf4j
@RestController
@RequestMapping("/dev")
public class DevDataCtl extends BaseDataCtl {
    @Autowired
    private DtBusinessUnitService dtBusinessUnitService;

    @Autowired
    private DtBudgetStatisticsService dtBudgetStatisticsService;

    @Autowired
    private DtDepartmentService dtDepartmentService;

    @Autowired
    private VbankDepartmentService vbankDepartmentService;

    @GetMapping("/bu/list")
    public ResponseResults f1() {
        try {
            List<BusinessUnitVO> businessUnits = dtBusinessUnitService.listBusinessUnits();
            return ResponseResults.newSuccess(businessUnits);
        } catch (Exception e) {
            return ResponseResults.newFailed(e.getMessage());
        }
    }

    @GetMapping("/daily/page")
    public PageData f2(BudgetStatisticsSearchDTO params) {
        try {
            ResponseBean<PageData<List<BudgetStatisticsDailyVO>>> responseBean = dtBudgetStatisticsService.getBudgetStatisticsService().pageBudgetDepartmentDailyStatistics(params);
            if (responseBean.getCode() != ResponseBean.OK) {
                log.error("分页查询日预算统计失败，" + responseBean.toString());
                return PageData.newFailed().setMsg(responseBean.toString());
            }
            return PageData.newSuccess()
                    .setCount(responseBean.getData().getCount())
                    .setData(responseBean.getData().getData());
        } catch (Exception e) {
            log.error("分页查询日预算统计异常，" + e.getMessage(), e);
            return PageData.newFailed()
                    .setMsg("分页查询日预算统计异常，" + e.getMessage());
        }
    }

    @GetMapping("/month/page")
    public PageData f3(BudgetStatisticsSearchDTO params) {
        try {
            ResponseBean<PageData<List<BudgetStatisticsMonthlyVO>>> responseBean = dtBudgetStatisticsService.getBudgetStatisticsService().pageCurrentMonthDepartmentBudgetStatistics(params);
            if (responseBean.getCode() != ResponseBean.OK) {
                log.error("分页查询当月预算统计失败，" + responseBean.toString());
                return PageData.newFailed().setMsg(responseBean.toString());
            }
            return PageData.newSuccess()
                    .setCount(responseBean.getData().getCount())
                    .setData(responseBean.getData().getData());
        } catch (Exception e) {
            log.error("分页查询当月预算统计异常，" + e.getMessage(), e);
            return PageData.newFailed()
                    .setMsg("分页查询当月预算统计异常，" + e.getMessage());
        }
    }

    @GetMapping("ding/depts")
    public ResponseResults f4() {
        try {
            List<DtDepartmentVO> dtDepartmentVOS = dtDepartmentService.listDtDepartments();
            return ResponseResults.newSuccess(dtDepartmentVOS);
        } catch (Exception e) {
            return ResponseResults.newFailed(e.getMessage());
        }
    }

    @GetMapping("uc/depts")
    public ResponseResults f5() {
        try {
            List<VbankDepartmentVO> vbankDepartmentVOS = vbankDepartmentService.listUcDepartments();
            return ResponseResults.newSuccess(vbankDepartmentVOS);
        } catch (Exception e) {
            return ResponseResults.newFailed(e.getMessage());
        }
    }

    @PostMapping("update/code")
    public ResponseResults f6() {
        try {
            dtDepartmentService.updateDtDepartmentCode();
            return ResponseResults.newSuccess("更新成功");
        } catch (Exception e) {
            return ResponseResults.newFailed(e.getMessage());
        }
    }
}
