package com.mbw.office.dingtalk.biz.ding;

import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.dingtalk.biz.ding.vo.DtDepartmentVO;
import com.mbw.office.dingtalk.biz.group.GroupbuyIncomeService;
import com.mbw.office.dingtalk.biz.group.GroupbuyIncomeVO;
import com.mbw.office.dingtalk.biz.vbank.VbankDepartmentService;
import com.mbw.office.dingtalk.biz.vbank.vo.VbankDepartmentVO;
import com.smthit.framework.dal.bettlsql.SqlKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-10-30 10:28
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class DtDepartmentService {
    @Autowired
    private VbankDepartmentService vbankDepartmentService;

    @Autowired
    private GroupbuyIncomeService groupbuyIncomeService;

    public List<DtDepartmentVO> listDtDepartments() {
        try {
            List<DtDepartmentVO> voList = SqlKit.$("dataSource").select("ding.DtDepartment.listDtDepartments", DtDepartmentVO.class);

            return voList;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    public void updateDtDepartmentCode() {
        try {
            List<VbankDepartmentVO> vbankDepartmentVOS = SqlKit.$("vbankDataSource").select("vbank.VbankDepartment.listUcDepartments", VbankDepartmentVO.class);
            List<DtDepartmentVO> dtDepartmentVOS = SqlKit.$("dataSource").select("ding.DtDepartment.listDtDepartments", DtDepartmentVO.class);
            if (CollectionUtils.isNotEmpty(vbankDepartmentVOS) && CollectionUtils.isNotEmpty(dtDepartmentVOS)) {
                Map<String, VbankDepartmentVO> ucDeptMap = vbankDepartmentVOS.stream().filter(vo -> StringUtils.isNotBlank(vo.getDingdingDeptid())).collect(Collectors.toMap(VbankDepartmentVO::getDingdingDeptid, Function.identity()));
                for (DtDepartmentVO dtDepartmentVO : dtDepartmentVOS) {
                    String dingDeptId = dtDepartmentVO.getId().toString();
                    VbankDepartmentVO vbankDepartmentVO = ucDeptMap.get(dingDeptId);
                    if (vbankDepartmentVO != null
                            && StringUtils.isNotBlank(vbankDepartmentVO.getDepartmentCode())
                            && vbankDepartmentVO.getName().equals(dtDepartmentVO.getOaDepartmentName())) {
                        dtDepartmentVO.setOaDepartmentCode(vbankDepartmentVO.getDepartmentCode());
                        GroupbuyIncomeVO groupbuyIncome = groupbuyIncomeService.getGroupbuyIncome(vbankDepartmentVO.getId(), dtDepartmentVO.getLastSyncTime());

//                        JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringUtil.getBean("groupDataSource");
                        //前面实现各有不同主要从配置bean中获取DruidDataSource
//                        DruidDataSource drs = (DruidDataSource) SpringUtil.getBean("groupDataSource");
//                        int oddCount = drs.getPoolingCount() - drs.getActiveCount();
//                        int activeCount = drs.getActiveCount();//剩余连接数
//                        log.info("DruidDataSource oddCount:" + oddCount);
//                        log.info("获取团购收入: " + groupbuyIncome);
                    }
                }
            }
        } catch (Exception e) {
            log.error("更新钉钉部门OA编码失败：" + e.getMessage(), e);
            throw new ServiceException("更新钉钉部门OA编码失败：" + e.getMessage(), e);
        }
    }
}
