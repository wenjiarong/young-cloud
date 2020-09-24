package org.springyoung.auth.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springyoung.auth.filter.ValidateCodeFilter;
import org.springyoung.auth.service.YoungUserDetailService;

/**
 * @ClassName web安全配置类, 处理令牌获取的过滤器链
 * @Description TODO
 * @Author 小温
 * @Date 2020/9/21 10:32
 * @Version 1.0
 * <p>
 * 继承了WebSecurityConfigurerAdapter适配器
 * @EnableWebSecurity用于开启了和Web相关的安全配置 YoungSecurityConfigure对/oauth/开头的请求生效，而YoungResourceServerConfigure对所有请求都生效
 * 源码中YoungSecurityConfigure优先级为100，YoungResourceServerConfigure为3
 * 为了让YoungSecurityConfigure的优先级高于YoungResourceServerConfigure
 * 在Spring中，数字越小，优先级越高，加上@Order(2)注解
 * 以/oauth/开头的请求由YoungSecurityConfigure过滤器链处理，剩下的其他请求由YoungResourceServerConfigure过滤器链处理
 */
@Order(2)
@EnableWebSecurity
@AllArgsConstructor
public class YoungSecurityConfigure extends WebSecurityConfigurerAdapter {

    private YoungUserDetailService userDetailService;
    private PasswordEncoder passwordEncoder;
    private ValidateCodeFilter validateCodeFilter;

    //挪动到common模块注解注入，由于system修改密码会使用到
    /*@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

    //注册了一个authenticationManagerBean，因为密码模式需要使用到这个Bean
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //重写了WebSecurityConfigurerAdapter类的configure(HttpSecurity http)方法
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //添加ValidateCodeFilter过滤器 和 用户密码校验过滤器
        //将ValidateCodeFilter过滤器添加到了UsernamePasswordAuthenticationFilter过滤器前
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                //requestMatchers().antMatchers("/oauth/**")的含义是：YoungSecurityConfigure安全配置类只对/oauth/开头的请求有效
                .requestMatchers()
                .antMatchers("/oauth/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
                .and()
                .csrf().disable();
    }

    //指定了userDetailsService和passwordEncoder
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
    }

    /*public static void main(String[] args) {
        String password = "123456";
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode(password));
        System.out.println(encoder.encode(password));
    }*/

}
