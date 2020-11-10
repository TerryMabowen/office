/**
 * 
 */
package com.mbw.office.dingtalk.biz.dingtalk;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Bean
 * 钉钉事件回调接口
 */
public interface EventHandler {
	public String getName();
	public void callback(String eventType, JSONObject request);
}