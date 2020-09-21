package org.springyoung.auth.config;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springyoung.auth.properties.YoungAuthProperties;
import org.springyoung.common.handler.YoungAccessDeniedHandler;
import org.springyoung.common.handler.YoungAuthExceptionEntryPoint;

/**
 * @ClassName 认证服务器本身也可以对外提供REST服务
 * @Description TODO
 * @Author 小温
 * @Date 2020/9/21 10:32
 * @Version 1.0
 */
//YoungResourceServerConfigure继承了ResourceServerConfigurerAdapter，并重写了configure(HttpSecurity http)方法
//@EnableResourceServer用于开启资源服务器相关配置。
@Configuration
@EnableResourceServer
@AllArgsConstructor
public class YoungResourceServerConfigure extends ResourceServerConfigurerAdapter {

    private YoungAccessDeniedHandler accessDeniedHandler;
    private YoungAuthExceptionEntryPoint exceptionEntryPoint;
    private YoungAuthProperties properties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //anonUrls为免认证资源数组，是从YoungAuthProperties配置中读取出来的值经过逗号分隔后的结果
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(properties.getAnonUrl(), ",");
        http.csrf().disable()
                //通过requestMatchers().antMatchers("/**")的配置表明该安全配置对所有请求都生效
                .requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests()
                // 放行验证码权限，免认证资源
                .antMatchers(anonUrls).permitAll()
                .antMatchers("/**").authenticated()
                .and().httpBasic();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        //注入异常处理
        resources.authenticationEntryPoint(exceptionEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }

}