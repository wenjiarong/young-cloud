package org.springyoung.test.config;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springyoung.common.handler.YoungAccessDeniedHandler;
import org.springyoung.common.handler.YoungAuthExceptionEntryPoint;
import org.springyoung.test.properties.YoungTestProperties;

@Configuration
@EnableResourceServer
@AllArgsConstructor
public class YoungTestResourceServerConfigure extends ResourceServerConfigurerAdapter {

    private final YoungAccessDeniedHandler accessDeniedHandler;
    private final YoungAuthExceptionEntryPoint exceptionEntryPoint;
    private final YoungTestProperties properties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(properties.getAnonUrl(), ",");

        http.csrf().disable()
                .requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests()
                //在资源服务器配置类里添加swagger免认证路径配置
                .antMatchers(anonUrls).permitAll()
                .antMatchers("/**").authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(exceptionEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }

}