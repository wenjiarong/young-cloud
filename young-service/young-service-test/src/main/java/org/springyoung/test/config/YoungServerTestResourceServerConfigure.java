package org.springyoung.test.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springyoung.common.handler.YoungAccessDeniedHandler;
import org.springyoung.common.handler.YoungAuthExceptionEntryPoint;

@Configuration
@EnableResourceServer
@AllArgsConstructor
public class YoungServerTestResourceServerConfigure extends ResourceServerConfigurerAdapter {

    private final YoungAccessDeniedHandler accessDeniedHandler;
    private final YoungAuthExceptionEntryPoint exceptionEntryPoint;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers("/**").authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(exceptionEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }

}