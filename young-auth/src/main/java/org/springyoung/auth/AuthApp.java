package org.springyoung.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springyoung.common.annotation.EnableYoungLettuceRedis;
import org.springyoung.common.annotation.YoungCloudApplication;

/**
 * @ClassName: auth
 * @Description: 认证授权中心
 * @Author: 温家荣
 * @Date: 2020/9/01 15:54
 * @Version: 1.0
 */
@EnableYoungLettuceRedis
@SpringBootApplication
@YoungCloudApplication
@EnableFeignClients
public class AuthApp {

    public static void main(String[] args) {
        SpringApplication.run(AuthApp.class, args);
    }

}
