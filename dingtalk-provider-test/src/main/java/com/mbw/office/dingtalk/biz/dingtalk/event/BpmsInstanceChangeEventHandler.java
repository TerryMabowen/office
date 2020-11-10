/**
 *
 */
package com.mbw.office.dingtalk.biz.dingtalk.event;

import com.alibaba.fastjson.JSONObject;
import com.mbw.office.dingtalk.biz.dingtalk.DingTalkService;
import com.mbw.office.dingtalk.biz.dingtalk.EventHandler;
import com.smthit.lang.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Bean
 * {
 *   "EventType": "bpms_task_change",
 *   "processInstanceId": "ce133dd0-5b22-9516-925779977e9c",
 *   "finishTime": 1495605749000,
 *   "corpId": "ding2c01651",
 *   "title": "自测-1016",
 *   "type": "finish",
 *   "result": "refuse",
 *   "remark": "拒绝理由",
 *   "createTime": 1495593189000,
 *   "bizCategoryId": "bizCategoryId",
 *   "staffId": "manager75"
 *}
 **/
public class BpmsInstanceChangeEventHandler implements EventHandler {
    @Autowired
    private DingTalkService dingTalkService;

    @Override
    public String getName() {
        return "bpms_instance_change";
    }

    @Override
    public void callback(String eventType, JSONObject request) {
        if (!getName().equals(eventType)) {
            throw new ServiceException("事件类型不一致，当前事件处理器无法处理");
        }

        String processInstanceId = request.getString("processInstanceId");
        String corpId = request.getString("corpId");
        String processCode = request.getString("processCode");
        String type = request.getString("type");

        try {
            dingTalkService.syncSingleProcessInsCallBack(processCode, corpId, processInstanceId);

            //开始事件发生时，自动重建表单
            if ("start".equals(type)) {
                dingTalkService.rebuildFormDetail(processCode, processInstanceId);
            }
        } catch (Exception exp) {
            throw new ServiceException(exp.getMessage(), exp);
        }
    }

}