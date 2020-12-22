package org.springyoung.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springyoung.common.annotation.EnableYoungLettuceRedis;

/**
 * @ClassName FileManageApp
 * @Description TODO
 * @Author 小温
 * @Date 2020/11/13 9:05
 * @Version 1.0
 */
@SpringBootApplication
@EnableYoungLettuceRedis
public class FileManageApp {

    public static void main(String[] args) {
        SpringApplication.run(FileManageApp.class, args);
    }

}
