package com.mbw.office.dingtalk.biz.dingtalk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.smthit.lang.exception.ServiceException;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 钉钉服务
 *
 * @author Mabowen
 * @date 2020-11-10 15:49
 */
@Slf4j
@Service
public class DingTalkService {
    public void syncSingleProcessInsCallBack(String processCode, String corpId, String processInstanceId) {

    }

    public void rebuildFormDetail(String processCode, String processInstanceId) {

    }

    public List<String[]> getAllAccessToken() {
        List<String[]> result = new ArrayList<String[]>();

        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey("dingd730cb282d30d917");
        request.setAppsecret("hnTAClwTtllL8IpzYJ9fz2mbFc240N-WbC-vvphcaOjCpG81LFrtalhlzoIHFnRg");

        String accessToken = "";
        String[] item = new String[2];

        try {
            OapiGettokenResponse response = client.execute(request);
            accessToken = response.getAccessToken();
        } catch (ApiException e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }

        item[0] = accessToken;
        item[1] = "1";
        result.add(item);

        return result;
    }
}
