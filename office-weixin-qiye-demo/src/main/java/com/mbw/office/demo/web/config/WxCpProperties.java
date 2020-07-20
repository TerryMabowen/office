package com.mbw.office.demo.web.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-17 17:31
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "qy.wx")
public class WxCpProperties {
    /**
     * 设置微信企业号的corpId
     */
    private String corpId;

    private List<AppConfig> appConfigs;

    @Getter
    @Setter
    public static class AppConfig {
        /**
         * 设置微信企业应用的AgentId
         */
        private Integer agentId;

        /**
         * 设置微信企业应用的Secret
         */
        private String secret;

        /**
         * 设置微信企业号的token
         */
        private String token;

        /**
         * 设置微信企业号的EncodingAESKey
         */
        private String aesKey;

    }

}
