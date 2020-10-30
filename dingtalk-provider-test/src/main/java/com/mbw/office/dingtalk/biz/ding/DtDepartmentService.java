package com.mbw.office.dingtalk.biz.ding;

import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.dingtalk.biz.ding.vo.DtDepartmentVO;
import com.mbw.office.dingtalk.biz.vbank.VbankDepartmentService;
import com.mbw.office.dingtalk.biz.vbank.vo.VbankDepartmentVO;
import com.smthit.framework.dal.bettlsql.SqlKit;
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
@Service
@Transactional(readOnly = true)
public class DtDepartmentService {
    @Autowired
    private VbankDepartmentService vbankDepartmentService;

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
            List<VbankDepartmentVO> vbankDepartmentVOS = vbankDepartmentService.listUcDepartments();
            List<DtDepartmentVO> dtDepartmentVOS = listDtDepartments();
            if (CollectionUtils.isNotEmpty(vbankDepartmentVOS) && CollectionUtils.isNotEmpty(dtDepartmentVOS)) {
                Map<String, VbankDepartmentVO> ucDeptMap = vbankDepartmentVOS.stream().filter(vo -> StringUtils.isNotBlank(vo.getDingdingDeptid())).collect(Collectors.toMap(VbankDepartmentVO::getDingdingDeptid, Function.identity()));
//                for (DtDepartmentVO dtDepartmentVO : dtDepartmentVOS) {
//                    String dingDeptId = dtDepartmentVO.getId().toString();
//                    VbankDepartmentVO vbankDepartmentVO = ucDeptMap.get(dingDeptId);
//                    if (vbankDepartmentVO != null
//                            && StringUtils.isNotBlank(vbankDepartmentVO.getDepartmentCode())
//                            && vbankDepartmentVO.getName().equals(dtDepartmentVO.getOaDepartmentName())) {
//                        dtDepartmentVO.setOaDepartmentCode(vbankDepartmentVO.getDepartmentCode());
//                        SqlKit.$("dataSource").update("ding.DtDepartment.updateDtDepartmentCode", dtDepartmentVO);
//                    }
//                }

            }
        } catch (Exception e) {
            throw new ServiceException("更新钉钉部门OA编码失败：" + e.getMessage(), e);
        }
    }
}
