package com.mbw.office.dingtalk.biz.vbank;

import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.dingtalk.biz.vbank.vo.VbankDepartmentVO;
import com.smthit.framework.dal.bettlsql.SqlKit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-10-30 10:27
 */
@Service
@Transactional(readOnly = true)
public class VbankDepartmentService {

    public List<VbankDepartmentVO> listUcDepartments() {
        try {
            List<VbankDepartmentVO> voList = SqlKit.$("vbankDataSource").select("vbank.VbankDepartment.listUcDepartments", VbankDepartmentVO.class);

            return voList;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
