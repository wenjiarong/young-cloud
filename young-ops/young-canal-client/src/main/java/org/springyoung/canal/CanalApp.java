package org.springyoung.canal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springyoung.canal.annotation.EnableCanalClient;
import org.springyoung.common.annotation.EnableYoungLettuceRedis;

/**
 * @ClassName CanalApp
 * @Description TODO
 * @Author 小温
 * @Version 1.0
 */
@EnableYoungLettuceRedis
@EnableCanalClient
@SpringBootApplication
public class CanalApp {

    public static void main(String[] args) {
        SpringApplication.run(CanalApp.class, args);
    }

}
