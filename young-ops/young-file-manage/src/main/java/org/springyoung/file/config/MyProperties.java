package org.springyoung.file.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName MyProperties
 * @Description TODO
 * @Author 小温
 * @Date 2020/12/14 14:59
 * @Version 1.0
 */
@Data
@SpringBootConfiguration
@ConfigurationProperties(prefix = "file.server")
public class MyProperties {

    private String dir;

    @Value("${file.server.user.config}")
    private String userCfg;

    @Value("${file.server.ftp.client.port}")
    private Integer clientPort;

    @Value("${file.server.ftp.ctrl.port}")
    private Integer ctrlPort;

}
