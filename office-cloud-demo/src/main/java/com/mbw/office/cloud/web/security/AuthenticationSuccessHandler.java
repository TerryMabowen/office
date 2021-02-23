package com.mbw.office.cloud.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbw.office.cloud.biz.security.dto.AuthUserDTO;
import com.mbw.office.cloud.biz.security.vo.AuthUserDetailVO;
import com.mbw.office.cloud.common.kit.json.JacksonKit;
import com.mbw.office.cloud.common.lang.response.ResponseResults;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Base64;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-02-22 17:13
 */
//@Component
public class AuthenticationSuccessHandler extends WebFilterChainServerAuthenticationSuccessHandler {
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        ServerHttpResponse response = exchange.getResponse();
        //设置headers
        HttpHeaders httpHeaders = response.getHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
        httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        //设置body
        ResponseResults wsResponse = ResponseResults.newSuccess();
        byte[] dataBytes = {};
        ObjectMapper mapper = JacksonKit.getObjectMapper();
        try {
            AuthUserDTO user = (AuthUserDTO) authentication.getPrincipal();
            AuthUserDetailVO userDetails = buildUser(user);
            byte[] authorization = (userDetails.getUsername() + ":" + userDetails.getPassword()).getBytes();
            String token = Base64.getEncoder().encodeToString(authorization);
            httpHeaders.add(HttpHeaders.AUTHORIZATION, token);
            wsResponse.setData(userDetails);
            dataBytes = mapper.writeValueAsBytes(wsResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
            dataBytes = JacksonKit.beanToJson(ResponseResults.newFailed().setMessage("授权异常")).getBytes();
        }
        DataBuffer bodyDataBuffer = response.bufferFactory().wrap(dataBytes);
        return response.writeWith(Mono.just(bodyDataBuffer));
    }


    private AuthUserDetailVO buildUser(AuthUserDTO user) {
        AuthUserDetailVO userDetails = new AuthUserDetailVO();
        userDetails.setUsername(user.getUsername());
        userDetails.setPassword(user.getPassword().substring(user.getPassword().lastIndexOf("}") + 1, user.getPassword().length()));
        return userDetails;
    }
}
