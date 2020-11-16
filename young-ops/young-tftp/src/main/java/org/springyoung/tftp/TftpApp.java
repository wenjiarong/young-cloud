package org.springyoung.tftp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springyoung.common.annotation.EnableYoungLettuceRedis;
import org.springyoung.tftp.server.TftpServer;

import java.io.File;

/**
 * @ClassName TftpApp
 * @Description TODO
 * @Author 小温
 * @Date 2020/11/13 9:05
 * @Version 1.0
 */
@SpringBootApplication
@EnableYoungLettuceRedis
public class TftpApp {

    public static void main(String[] args) {
        SpringApplication.run(TftpApp.class, args);
    }

}
