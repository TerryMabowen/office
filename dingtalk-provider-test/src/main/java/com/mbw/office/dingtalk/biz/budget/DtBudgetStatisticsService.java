package com.mbw.office.dingtalk.biz.budget;

import ai.bell.dingtalk.gw.client.DTClientFactory;
import ai.bell.dingtalk.gw.provider.spi.budget.statistics.IBudgetStatisticsService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 钉钉中台预算统计服务
 *
 * @author Mabowen
 * @date 2020-08-24 16:22
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class DtBudgetStatisticsService {
    @Getter
    private IBudgetStatisticsService budgetStatisticsService;

    public DtBudgetStatisticsService(DTClientFactory dtClientFactory) {
        this.budgetStatisticsService = dtClientFactory.getBudgetStatisticsService();
    }
}
