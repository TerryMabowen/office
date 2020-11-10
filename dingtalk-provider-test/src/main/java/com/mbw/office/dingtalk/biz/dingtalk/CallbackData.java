package com.mbw.office.dingtalk.biz.dingtalk;

import com.alibaba.fastjson.JSONObject;
import com.smthit.lang2.uuid.SmthitUUID;
import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author wuf
 */
@Data
@ToString
public class CallbackData {
    private Long callbackId;
    private String eventType;
    private JSONObject data;

    public CallbackData(String eventType, JSONObject data) {
        this.eventType = eventType;
        this.data = data;
        this.callbackId = SmthitUUID.defaultWorker().nextId();
    }

}
