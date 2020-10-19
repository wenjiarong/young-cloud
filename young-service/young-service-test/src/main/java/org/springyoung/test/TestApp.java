package org.springyoung.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springyoung.common.annotation.YoungCloudApplication;

/**
 * @ClassName TestApp
 * @Description TODO
 * @Author 小温
 * @Date 2020/9/21 16:24
 * @Version 1.0
 */
@EnableFeignClients({"org.springyoung"})
@SpringBootApplication
@YoungCloudApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@MapperScan("org.springyoung.test.mapper")
@EnableTransactionManagement
public class TestApp {

    public static void main(String[] args) {
        SpringApplication.run(TestApp.class, args);
    }

}
