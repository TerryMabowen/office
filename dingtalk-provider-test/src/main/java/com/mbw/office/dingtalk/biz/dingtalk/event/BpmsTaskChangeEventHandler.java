/**
 * 
 */
package com.mbw.office.dingtalk.biz.dingtalk.event;

import com.alibaba.fastjson.JSONObject;
import com.mbw.office.dingtalk.biz.dingtalk.DingTalkService;
import com.mbw.office.dingtalk.biz.dingtalk.EventHandler;
import com.smthit.lang.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Bean
 * {
 * 	"taskId":50675386105,
 * 	"createTime":1547867802000,
 * 	"staffId":"309","bizCategoryId":"",
 * 	"EventType":"bpms_task_change",
 * 	"type":"start",
 * 	"title":"****",
 * 	"processCode":"PROC-SIYJCRSV-Z4N1VBTAZFVRB3YCB7US1-5FTKBCQJ-E",
 * 	"processInstanceId":"7692d81d-0a59-416d-9639-f972c9cdbb21",
 * 	"corpId":"dingd730cb282d30d917"
 * }
 */
@Slf4j
public class BpmsTaskChangeEventHandler implements EventHandler {

	@Autowired
	private DingTalkService dingTalkService;
	
	@Override
	public String getName() {
		return "bpms_task_change";
	}

	@Override
	public void callback(String eventType, JSONObject request) {
		if(!getName().equals(eventType)) {
			throw new ServiceException("事件类型不一致，当前事件处理器无法处理");
		}
		
		String processInstanceId = request.getString("processInstanceId");
		String corpId = request.getString("corpId");
		String processCode = request.getString("processCode");
		
		try {
			dingTalkService.syncSingleProcessInsCallBack(processCode, corpId, processInstanceId);
		} catch (ServiceException exp) {
			log.error(exp.getMessage(), exp);
		}
	}

}