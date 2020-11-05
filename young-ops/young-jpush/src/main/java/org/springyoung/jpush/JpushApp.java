package org.springyoung.jpush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springyoung.common.annotation.EnableYoungLettuceRedis;

/**
 * @ClassName JpushApp
 * @Description TODO
 * @Author 小温
 * @Date 2020/11/5 11:11
 * @Version 1.0
 */
@SpringBootApplication
@EnableYoungLettuceRedis
public class JpushApp {

    public static void main(String[] args) {
        SpringApplication.run(JpushApp.class, args);
    }

}
