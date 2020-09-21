package org.springyoung.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springyoung.common.annotation.YoungCloudApplication;

/**
 * @ClassName: auth
 * @Description: 认证授权中心
 * @Author: 温家荣-wjr
 * @Date: 2020/9/01 15:54
 * @Version: 1.0
 */
@SpringBootApplication
@YoungCloudApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableTransactionManagement
public class AuthApp {

    public static void main(String[] args) {
        SpringApplication.run(AuthApp.class, args);
    }

}
