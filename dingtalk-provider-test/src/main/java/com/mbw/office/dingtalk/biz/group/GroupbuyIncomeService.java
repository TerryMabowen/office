package com.mbw.office.dingtalk.biz.group;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Maps;
import com.mbw.office.common.lang.helper.ApplicationContextHelper;
import com.mbw.office.common.util.validate.AssertUtil;
import com.smthit.framework.dal.bettlsql.SqlKit;
import com.smthit.lang.exception.ServiceException;
import com.smthit.lang.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 团购收入
 *
 * @author Mabowen
 * @date 2020-09-02 13:39
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class GroupbuyIncomeService {
    /**
     * 获取团购收入
     * @author Mabowen
     * @date 2020-09-02 13:47
     * @param oaDeptId
     * @param statisticsDate
     * @return {@link GroupbuyIncomeVO}
     */
    public GroupbuyIncomeVO getGroupbuyIncome(Integer oaDeptId, Date statisticsDate) {
        try {
            AssertUtil.assertNotNull(oaDeptId, "OA部门ID不能为空");
            AssertUtil.assertNotNull(statisticsDate, "统计时间不能为空");
            Date queryStartTime = DateUtils.getDayBegin(statisticsDate);
            Date queryEndTime = DateUtils.getDayEnd(statisticsDate);
            Map<String, Object> params = Maps.newHashMap();
            params.put("oaDeptId", oaDeptId);
            params.put("queryStartTime", queryStartTime);
            params.put("queryEndTime", queryEndTime);

            GroupbuyIncomeVO groupbuyIncomeVO = new GroupbuyIncomeVO();
            BigDecimal totalMoney = BigDecimal.ZERO;

            DruidDataSource groupDataSource = (DruidDataSource) ApplicationContextHelper.getBean("groupDataSource");
            log.info("DruidDataSource activeCount:" + groupDataSource.getActiveCount() + ", oddCount: " + groupDataSource.getPoolingCount());

            //团购收入
            GroupbuyIncomeVO groupOrderIncome = SqlKit.$("groupDataSource").selectSingle("groupbuy.groupbuyIncome.getGroupOrderIncome", params, GroupbuyIncomeVO.class);
            //近卫军收入
            GroupbuyIncomeVO grdOrderIncome = SqlKit.$("groupDataSource").selectSingle("groupbuy.groupbuyIncome.getGrdOrderIncome", params, GroupbuyIncomeVO.class);
            if (null == groupOrderIncome && null == grdOrderIncome) {
                return null;
            } else if (groupOrderIncome != null && grdOrderIncome != null) {
                if (null == groupOrderIncome.getPrice() && null == grdOrderIncome.getGrdPrice()) {
                    return null;
                }
            }

            if (groupOrderIncome != null && groupOrderIncome.getPrice() != null) {
                totalMoney = totalMoney.add(groupOrderIncome.getPrice());
            }
            if (grdOrderIncome != null && grdOrderIncome.getGrdPrice() != null) {
                totalMoney = totalMoney.add(grdOrderIncome.getGrdPrice());
            }

            groupbuyIncomeVO.setGroupBuyTotalMoney(totalMoney);
            return groupbuyIncomeVO;
        } catch (Exception e) {
            log.error("获取团购收入失败，" + e.getMessage());
            throw new ServiceException("获取团购收入失败，" + e.getMessage(), e);
        }
    }
}
