package com.mbw.office.demo.api.ctl;

import cn.hutool.json.JSONObject;
import com.mbw.office.demo.base.BaseApiService;
import com.mbw.office.demo.base.ResponseBase;
import com.mbw.office.demo.entity.AppEntity;
import com.mbw.office.demo.mapper.AppMapper;
import com.mbw.office.demo.service.BaseRedisService;
import com.mbw.office.demo.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-14 20:30
 */
// 创建获取getAccessToken
@RestController
@RequestMapping(value = "/auth")
public class AuthController extends BaseApiService {
    @Autowired
    private BaseRedisService baseRedisService;

    private long TIME_TOKEN = 60 * 60 * 2;

    @Autowired
    private AppMapper appMapper;

    // 使用appId+appSecret 生成AccessToke
    @RequestMapping("/getAccessToken")
    public ResponseBase getAccessToken(AppEntity appEntity) {
        AppEntity appResult = appMapper.findApp(appEntity);
        if (appResult == null) {
            return setResultError("没有对应机构的认证信息");
        }
        int isFlag = appResult.getIsFlag();
        if (isFlag == 1) {
            return setResultError("您现在没有权限生成对应的AccessToken");
        }
        // ### 获取新的accessToken 之前删除之前老的accessToken
        // 从redis中删除之前的accessToken
        String accessToken = appResult.getAccessToken();
        if(accessToken != null) {
            baseRedisService.delKey(accessToken);
        }
        // 生成的新的accessToken
        String newAccessToken = newAccessToken(appResult.getAppId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("accessToken", newAccessToken);
        return setResultSuccessData(jsonObject);
    }

    private String newAccessToken(String appId) {
        // 使用appid+appsecret 生成对应的AccessToken 保存两个小时
        String accessToken = TokenUtil.getAccessToken();
        // 保证在同一个事物redis 事物中
        // 生成最新的token key为accessToken value 为 appid
        baseRedisService.setString(accessToken, appId, TIME_TOKEN);
        // 表中保存当前accessToken
        appMapper.updateAccessToken(accessToken, appId);
        return accessToken;
    }
}
