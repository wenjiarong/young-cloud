package org.springyoung.auth.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springyoung.auth.service.YoungUserDetailService;
import org.springyoung.auth.support.YoungJwtTokenEnhancer;
import org.springyoung.core.constant.YoungConstant;

/**
 * @ClassName JwtTokenStoreConfiguration
 * @Description TODO
 * @Author 小温
 * @Date 2020/11/10 10:45
 * @Version 1.0
 */
@Configuration
@AllArgsConstructor
public class JwtTokenStoreConfiguration {

    private final YoungUserDetailService userDetailService;

    /**
     * 使用JWT格式令牌
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        DefaultAccessTokenConverter defaultAccessTokenConverter = (DefaultAccessTokenConverter) accessTokenConverter.getAccessTokenConverter();
        DefaultUserAuthenticationConverter userAuthenticationConverter = new DefaultUserAuthenticationConverter();
        userAuthenticationConverter.setUserDetailsService(userDetailService);
        defaultAccessTokenConverter.setUserTokenConverter(userAuthenticationConverter);
        //指定了JWT的密钥，防止我们的令牌在传输途中被篡改
        accessTokenConverter.setSigningKey(YoungConstant.JWT_KEY);
        return accessTokenConverter;
    }

    /**
     * 用于扩展jwt
     */
    @Bean
    @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
    public TokenEnhancer jwtTokenEnhancer() {
        return new YoungJwtTokenEnhancer();
    }

}
