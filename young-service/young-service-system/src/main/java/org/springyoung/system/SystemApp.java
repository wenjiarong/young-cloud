package org.springyoung.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springyoung.common.annotation.YoungCloudApplication;

/**
 * @ClassName SystemApp
 * @Description TODO
 * @Author 小温
 * @Date 2020/9/21 16:16
 * @Version 1.0
 */
@SpringBootApplication
@YoungCloudApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableTransactionManagement
public class SystemApp {

    public static void main(String[] args) {
        SpringApplication.run(SystemApp.class, args);
    }

}
