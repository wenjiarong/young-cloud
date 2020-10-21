package org.springyoung.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName AdminApp
 * @Description TODO
 * @Author 小温
 * @Date 2020/10/21 16:37
 * @Version 1.0
 */
@EnableAdminServer
@SpringBootApplication
public class AdminApp {

    public static void main(String[] args) {
        SpringApplication.run(AdminApp.class, args);
    }

}
