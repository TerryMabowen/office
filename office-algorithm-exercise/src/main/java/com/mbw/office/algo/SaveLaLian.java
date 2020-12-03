package com.mbw.office.algo;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-12-03 11:46
 */
public class SaveLaLian {
    public void f1() {
        /**
         * 1. 查询这个账套这个部门所有分摊，并按照分摊开始时间升序排序；
         * 2. 判断查询的结果：
         *    2.1 结果为空：新增分总部成本分摊
         *    2.2 结果不为空，判断参数的时间段在现有数据的头还是尾
         *
         */
        try {
            List<AccountSetHeadquartersCostSharePO> headquartersCostShares = AccountSetHeadquartersCostSharePO.Dao.$.createLambdaQuery()
                    .andEq(BaseEntity::getStatus, EnumLogicStatus.NORMAL.getValue())
                    .andEq(AccountSetHeadquartersCostSharePO::getAccountSetId, param.getAccountSetId())
                    .andEq(AccountSetHeadquartersCostSharePO::getDepartmentId, param.getDepartmentId())
                    .asc(AccountSetHeadquartersCostSharePO::getShareStartDate)
                    .select();

            Date defaultEndDate = DateUtils.getDateByFormatString(CommonFormatConstants.DEFAULT_END_DATE, DateUtils.DATE_SHORT);
            if (CollectionUtils.isNotEmpty(headquartersCostShares)) {
                List<AccountSetHeadquartersCostShareVO> costShareList = new BeetlReformer<AccountSetHeadquartersCostSharePO, AccountSetHeadquartersCostShareVO>(AccountSetHeadquartersCostShareVO.class).toVOs(headquartersCostShares);
                AccountSetHeadquartersCostShareVO headquartersCostShare = new AccountSetHeadquartersCostShareVO();
                BeanUtil.copyPropertiesFromBean2Bean(param, headquartersCostShare);
                costShareList.add(headquartersCostShare);

                costShareList.sort(new Comparator<AccountSetHeadquartersCostShareVO>() {
                    @Override
                    public int compare(AccountSetHeadquartersCostShareVO o1, AccountSetHeadquartersCostShareVO o2) {
                        return o1.getShareStartDate().compareTo(o2.getShareStartDate());
                    }
                });

                Date prevEndDate = DateUtil.getMonthEnd(DateUtil.getBeforeMonth(param.getShareStartDate(), 1));

                int index = costShareList.indexOf(headquartersCostShare);
                if (index == 0) {
                    if (defaultEndDate.compareTo(param.getShareEndDate()) == 0) {
                        for (int i = (index + 1); i < costShareList.size(); i++) {
                            AccountSetHeadquartersCostShareVO headquartersCostShareVO = costShareList.get(i);
                            //headquartersCostShareVO 删除
                            deleteHeadquarterCostShare(headquartersCostShareVO);
                        }
                    } else {
                        Date nextBeginDate = DateUtil.getMonthBegin(DateUtil.getAfterMonth(param.getShareEndDate(), 1));
                        AccountSetHeadquartersCostShareVO nextShare = costShareList.get(index + 1);
                        if (nextShare.getShareStartDate().compareTo(param.getShareStartDate()) == 0) {
                            //nextShare 删除
                            deleteHeadquarterCostShare(nextShare);
                            if ((index + 2) < costShareList.size()) {
                                AccountSetHeadquartersCostShareVO againNextShare = costShareList.get(index + 2);
                                againNextShare.setShareStartDate(nextBeginDate);
                                //againNextShare 更新
                                updateHeadquarterCostShare(againNextShare);
                            }
                        } else {
                            nextShare.setShareStartDate(nextBeginDate);
                            //nextShare 更新
                            updateHeadquarterCostShare(nextShare);
                        }
                    }
                } else if (index == (costShareList.size() - 1)) {
                    AccountSetHeadquartersCostShareVO prevShare = costShareList.get(index - 1);
                    if (prevShare.getShareStartDate().compareTo(param.getShareStartDate()) == 0) {
                        //prevShare 删除
                        deleteHeadquarterCostShare(prevShare);
                        if ((index - 2) >= 0) {
                            AccountSetHeadquartersCostShareVO againPrevShare = costShareList.get(index - 2);
                            againPrevShare.setShareEndDate(prevEndDate);
                            //againPrevShare 更新
                            updateHeadquarterCostShare(againPrevShare);
                        }
                    } else {
                        prevShare.setShareEndDate(prevEndDate);
                        //prevShare 更新
                        updateHeadquarterCostShare(prevShare);
                    }
                } else {
                    AccountSetHeadquartersCostShareVO prevShare = costShareList.get((index - 1));
                    if (prevShare.getShareStartDate().compareTo(param.getShareStartDate()) == 0) {
                        //prevShare 删除
                        deleteHeadquarterCostShare(prevShare);
                        if ((index - 2) >= 0) {
                            AccountSetHeadquartersCostShareVO againPrevShare = costShareList.get(index - 2);
                            againPrevShare.setShareEndDate(prevEndDate);
                            //againPrevShare 更新
                            updateHeadquarterCostShare(againPrevShare);
                        }
                    } else {
                        prevShare.setShareEndDate(prevEndDate);
                        //prevShare 更新
                        updateHeadquarterCostShare(prevShare);
                    }

                    if (defaultEndDate.compareTo(param.getShareEndDate()) == 0) {
                        for (int i = (index + 1); i < costShareList.size(); i++) {
                            AccountSetHeadquartersCostShareVO headquartersCostShareVO = costShareList.get(i);
                            //headquartersCostShareVO 删除
                            deleteHeadquarterCostShare(headquartersCostShareVO);
                        }
                    } else {
                        Date nextBeginDate = DateUtil.getMonthBegin(DateUtil.getAfterMonth(param.getShareEndDate(), 1));
                        AccountSetHeadquartersCostShareVO nextShare = costShareList.get(index + 1);
                        if (nextShare.getShareStartDate().compareTo(param.getShareStartDate()) == 0) {
                            //nextShare 删除
                            deleteHeadquarterCostShare(nextShare);
                            if ((index + 2) < costShareList.size()) {
                                AccountSetHeadquartersCostShareVO againNextShare = costShareList.get(index + 2);
                                againNextShare.setShareStartDate(nextBeginDate);
                                //againNextShare 更新
                                updateHeadquarterCostShare(againNextShare);
                            }
                        } else {
                            nextShare.setShareStartDate(nextBeginDate);
                            //nextShare 更新
                            updateHeadquarterCostShare(nextShare);
                        }
                    }
                }
            }

            AccountSetHeadquartersCostSharePO headquartersCostShare = new AccountSetHeadquartersCostSharePO();
            BeanUtil.copyPropertiesFromBean2Bean(param, headquartersCostShare);

            headquartersCostShare.save();
        } catch (Exception e) {
            log.error("创建分总部成本分摊失败：" + e.getMessage());
            throw new ServiceException("创建分总部成本分摊失败：" + e.getMessage(), e);
        }
    }
}
