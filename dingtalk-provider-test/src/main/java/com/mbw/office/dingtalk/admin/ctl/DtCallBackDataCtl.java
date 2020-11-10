package com.mbw.office.dingtalk.admin.ctl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCallBackDeleteCallBackRequest;
import com.dingtalk.api.request.OapiCallBackRegisterCallBackRequest;
import com.dingtalk.api.response.OapiCallBackDeleteCallBackResponse;
import com.dingtalk.api.response.OapiCallBackRegisterCallBackResponse;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptor;
import com.google.gson.Gson;
import com.mbw.office.dingtalk.biz.dingtalk.CallbackData;
import com.mbw.office.dingtalk.biz.dingtalk.DingTalkCallbackWorker;
import com.mbw.office.dingtalk.biz.dingtalk.DingTalkService;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 钉钉回调
 *
 * @author Mabowen
 * @date 2020-11-10 15:29
 */
@Slf4j
@RestController
public class DtCallBackDataCtl {
    @Autowired
    private DingTalkService dingTalkService;

    @Autowired
    DingTalkCallbackWorker dingTalkCallbackWorker;

    //TODO
    private String token = "123456";
    private String aesKey = "1234567890123456789012345678901234567890123";

    /**
     * 钉钉事件回调处理
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param request
     * @return
     */

    @SuppressWarnings("serial")
    @RequestMapping(value = "/ding/event_callback/{corpId}")
    @ResponseBody
    public Map<String, ?> dingCallback(@PathVariable(value = "corpId") Long corpId,
                                       @RequestParam(value = "signature", required = false) String signature,
                                       @RequestParam(value = "timestamp", required = false) String timestamp,
                                       @RequestParam(value = "nonce", required = false) String nonce,
                                       @RequestBody(required = false) JSONObject request) {

        //TODO 此处需要变更为生成和消费者模式，使用blockqueue的方式来进行处理，eventHandler.callback 要编程消费线程来处理，因为钉钉会有多次回调，导出同步任务时会有多次调用，需要解决可重入的问题；2019.03.15
        Gson gson = new Gson();

        try {
            log.info("钉钉回调请求:" + request.toJSONString());

            String dtCorpId = "1";
            log.info("企业钉钉:" + dtCorpId);

            DingTalkEncryptor dingTalkEncryptor = new DingTalkEncryptor(token, aesKey, dtCorpId);
            String encrypt = request.getString("encrypt");
            String plainText = dingTalkEncryptor.getDecryptMsg(signature, timestamp, nonce, encrypt);

            JSONObject jsonObject = JSON.parseObject(plainText);
            String eventType = jsonObject.getString("EventType");

            //TODO
            if ("check_url".equals(eventType)) {
                // 加密success，并返回
                Map<String, ?> result = dingTalkEncryptor.getEncryptedMap("success", Calendar.getInstance().getTimeInMillis(), token);
                log.info("check_url : " + gson.toJson(result));
                return result;
            } else {
                CallbackData callbackData = new CallbackData(eventType, jsonObject);
                dingTalkCallbackWorker.addEventData(callbackData);
            }

            return new HashMap<String, Object>() {{
                put("errcode", 0L);
                put("errmsg", "ok");
            }};
        } catch (Throwable exp) {
            log.error(exp.getMessage(), exp);
        }

        return new HashMap<String, Object>() {{
            put("errcode", 500L);
            put("errmsg", "error");
        }};
    }

    /**
     * 注册钉钉回调事件
     *
     * @return
     */
    @RequestMapping("/ding/regist")
    @ResponseBody
    public String regist() {
        //TODO 配置化
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/register_call_back");

        //TODO
        List<String[]> items = dingTalkService.getAllAccessToken();
        for (String[] item : items) {
            try {
                DingTalkClient clientDel = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/delete_call_back");
                OapiCallBackDeleteCallBackRequest requestDel = new OapiCallBackDeleteCallBackRequest();
                requestDel.setHttpMethod("GET");
                OapiCallBackDeleteCallBackResponse responseDel = clientDel.execute(requestDel, item[0]);
                log.info("delete:" + responseDel.getBody());
            } catch (ApiException xp) {
                log.error("删除钉钉回调接口", xp);
            }

            OapiCallBackRegisterCallBackRequest request = new OapiCallBackRegisterCallBackRequest();
            //TODO
            request.setUrl("https://ding.adm.bell.ai/ding/event_callback/" + item[1]);
            request.setAesKey(aesKey);
            request.setToken(token);

            request.setCallBackTag(Arrays.asList("bpms_task_change", "bpms_instance_change"));

            try {
                OapiCallBackRegisterCallBackResponse response = client.execute(request, item[0]);
                log.info("regist:" + response.getBody());
            } catch (ApiException e) {
                log.error(e.getMessage(), e);
            }
        }

        return "ok";
    }
}
