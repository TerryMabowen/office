package com.mbw.office.common.task;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.*;

/**
 * 批量任务管理
 *
 * @author Mabowen
 * @date 2021-01-15 18:43
 */
@Slf4j
@Component
public class BatchTaskManager {
    private ThreadPoolExecutor executor;
    private int poolSize = 2;

    private BlockingQueue<BatchTaskWrapper> basket = new LinkedBlockingQueue<BatchTaskWrapper>();

    @PostConstruct
    public void init() {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolSize);
        executor.execute(new Consumer(executor));
    }

    @PreDestroy
    public void close() {
        executor.shutdown();
    }

    /**
     * 调度一个任务
     *
     * @param task
     */
    public void scheduleTask(BatchTask task) {
        if (task == null) {
            return;
        }

        basket.add(new BatchTaskWrapper(task, null));
    }

    /**
     * 当前执行的任务数
     *
     * @return
     */
    public int getActiveTreadCount() {
        return executor.getActiveCount();
    }

    private class Consumer implements Runnable {
        private ThreadPoolExecutor executor;

        Consumer(ThreadPoolExecutor executor) {
            this.executor = executor;
        }

        @Override
        public void run() {
            log.debug("Consumer Started!");

            while (true) {
                try {
                    if (executor.getQueue().size() >= poolSize) {
                        Thread.sleep(5000);
                        continue;
                    }

                    BatchTaskWrapper batchTaskWrapper = basket.take();

                    Future<?> future = executor.submit(batchTaskWrapper.batchTask);
                    batchTaskWrapper.setFuture(future);
                } catch (InterruptedException e) {
                    break;
                } catch (Exception exp) {
                    log.error(exp.getMessage(), exp);
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    @Data
    public static class BatchTaskWrapper {
        BatchTask batchTask;
        Future<?> future;

        BatchTaskWrapper(BatchTask batchTask, Future<?> future) {
            this.batchTask = batchTask;
            this.future = future;
        }
    }
}
