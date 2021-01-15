package com.mbw.office.common.task;

import cn.hutool.core.util.StrUtil;
import com.mbw.office.common.lang.enums.EnumTaskState;
import com.mbw.office.common.lang.exception.ServiceException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 批量任务抽象类
 *
 * @author Mabowen
 * @date 2021-01-15 18:43
 */
@Data
@Slf4j
public abstract class BatchTask implements Runnable {
    private String name;
    private Long batchTaskId;

    protected BatchTaskPO batchTaskPO;

    protected long startTime = 0;
    protected String memo;
    protected StringBuffer executeResult = new StringBuffer();

    public void doBefore() {
        batchTaskPO = selectBatchTaskById(getBatchTaskId());
        if (batchTaskPO != null) {
            batchTaskPO.setTaskStatus(EnumTaskState.RUNNING.getValue());
            batchTaskPO.setMemo("开始启动执行任务");
            //TODO 更新数据
            // batchTaskPO.update();
        } else {
            throw new ServiceException("任务不存在, 任务ID=" + getBatchTaskId());
        }
    }

    @Override
    public void run() {
        startTime = System.currentTimeMillis();
        try {
            doBefore();
        } catch (Exception exp) {
            log.error(exp.getMessage(), exp);
            return;
        }

        try {
            doRun();
        } catch (Exception exp) {
            log.error(exp.getMessage(), exp);
            executeResult.append(exp.getMessage());
        } finally {

        }

        try {
            doAfter();
        } catch (Exception exp) {
            log.error(exp.getMessage(), exp);
        }
    }

    public abstract void doRun();

    public void doAfter() {
        if (batchTaskPO != null) {
            batchTaskPO.setTaskStatus(StrUtil.isBlank(executeResult.toString()) ? EnumTaskState.SUCCESS_OVER.getValue() : EnumTaskState.FAILURE_OVER.getValue());
            batchTaskPO.setException(StrUtil.isBlank(executeResult.toString()) ? null : executeResult.toString());

            if (startTime == 0) {
                startTime = batchTaskPO.getCreatedAt().getTime();
            }
            long diff = System.currentTimeMillis() - startTime;
            double diffMin = (double) (diff / 1000 / 60);
            batchTaskPO.setMemo(memo + ", 此次任务共执行" + diffMin + "分钟");

            //TODO 更新数据
            // batchTaskPO.update();
        }
    }

    private BatchTaskPO selectBatchTaskById(Long batchTaskId) {
        //TODO 数据库查询
        return null;
    }

    @Data
    private static class BatchTaskPO {
        private Long id;

        /**
         * 任务当前状态
         */
        private Integer taskStatus;

        /**
         * 任务异常状态描述
         */
        private String exception;

        /**
         * 任务执行过程描述
         */
        private String memo;

        /**
         * 任务名称
         */
        private String name;

        /**
         * 任务提交者
         */
        private Long submitUserId;

        /**
         * 提交时间
         */
        private Date submitTime;

        private Integer status;
        private Date createdAt;
        private Date updatedAt;
    }
}
