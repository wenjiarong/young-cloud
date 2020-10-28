package org.springyoung.demo.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:young-demo.properties"})
@ConfigurationProperties(prefix = "young.demo")
public class YoungDemoProperties {

    /**
     * 免认证 URI，多个值的话以逗号分隔
     */
    private String anonUrl;

    private YoungSwaggerProperties swagger = new YoungSwaggerProperties();

}