/**
 *
 */
package com.mbw.office.dingtalk.biz.dingtalk;

import com.mbw.office.dingtalk.biz.dingtalk.event.DingTalkEventHandlerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Bean
 *
 */
@Slf4j
@Component
public class DingTalkCallbackWorker {
    @Autowired
    private DingTalkEventHandlerFactory dingTalkEventHandlerFactory;

    @Autowired
    private DtCallbackService dtCallbackService;

    private final BlockingQueue<CallbackData> queue;
    private Thread consumerThread;

    public DingTalkCallbackWorker() {
        queue = new ArrayBlockingQueue<CallbackData>(2000);
        consumerThread = new Thread(new Consumer(queue));
        consumerThread.start();
    }

    public void addEventData(CallbackData data) {
        //1.先落地
        dtCallbackService.addDtCallback(data);

        boolean flag = queue.offer(data);
        if (flag) {
            log.info("成功入队, size: " + queue.size() + ",  数据:" + data);
        } else {
            log.error("失败入队, size: " + queue.size() + "remainingCapacity: " + queue.remainingCapacity() + ",  数据:" + data);
        }
    }

    public class Consumer implements Runnable {
        private BlockingQueue<CallbackData> queue;

        public Consumer(BlockingQueue<CallbackData> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    log.info("Consume Execute, Size: " + queue.size() + "remainingCapacity:" + queue.remainingCapacity());
                    consume(queue.take());
                } // 当队列空时，消费者阻塞等待
            } catch (InterruptedException ex) {
                log.error(ex.getMessage(), ex);
            } catch (Exception exp) {
                log.error("钉钉事件消费线程,异常:" + exp);
            }
        }

        void consume(CallbackData data) {
            log.info("处理回调钉钉回调事件: " + data);
            EventHandler eventHandler = dingTalkEventHandlerFactory.getEventHandler(data.getEventType());
            if (eventHandler != null) {
                try {
                    eventHandler.callback(data.getEventType(), data.getData());
                    dtCallbackService.updateCallback(data.getCallbackId(), true, "成功");
                } catch (Exception exp) {
                    dtCallbackService.updateCallback(data.getCallbackId(), false, exp.getMessage());
                }

            } else {
                log.error("找不到对应的事件处理器:" + data.getEventType());
                dtCallbackService.updateCallback(data.getCallbackId(), true, "非审批中数据忽略");
            }

        }
    }
}