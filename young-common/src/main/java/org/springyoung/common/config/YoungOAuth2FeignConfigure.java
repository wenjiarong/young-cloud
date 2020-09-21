package org.springyoung.common.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.Base64Utils;
import org.springyoung.common.constant.YoungConstant;

/**
 * 拦截Feign请求，手动往请求头上加入令牌
 * <p>
 * 我们通过SecurityContextHolder从请求上下文中获取了OAuth2AuthenticationDetails类型对象，
 * 并通过该对象获取到了请求令牌，然后在请求模板对象requestTemplate的头部手动将令牌添加上去
 */
public class YoungOAuth2FeignConfigure {

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor() {
        return requestTemplate -> {
            // 添加 gatewayToken
            String gatewayToken = new String(Base64Utils.encode(YoungConstant.GATEWAY_TOKEN_VALUE.getBytes()));
            requestTemplate.header(YoungConstant.GATEWAY_TOKEN_HEADER, gatewayToken);

            Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
            if (details instanceof OAuth2AuthenticationDetails) {
                String authorizationToken = ((OAuth2AuthenticationDetails) details).getTokenValue();
                requestTemplate.header(HttpHeaders.AUTHORIZATION, "bearer " + authorizationToken);
            }
        };
    }

}