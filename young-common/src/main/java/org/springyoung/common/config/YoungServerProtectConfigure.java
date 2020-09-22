package org.springyoung.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springyoung.common.interceptor.YoungServerProtectInterceptor;

/**
 * 要让该过滤器生效我们需要定义一个配置类来将它注册到Spring IOC容器中
 * 我们在该配置类里注册了youngServerProtectInterceptor，并且将它加入到了Spring的拦截器链中
 */
public class YoungServerProtectConfigure implements WebMvcConfigurer {

    /**
     * 因为各个微服务系统里可以自定义的PasswordEncoder，
     * 所以这里使用了@ConditionalOnMissingBean(value = PasswordEncoder.class)
     * 注解标注，表示当IOC容器里没有PasswordEncoder类型的Bean的时候，
     * 则将BCryptPasswordEncoder注册到IOC容器中
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(value = PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HandlerInterceptor youngServerProtectInterceptor() {
        return new YoungServerProtectInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(youngServerProtectInterceptor());
    }

}