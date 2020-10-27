package org.springyoung.test.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:young-test.properties"})
@ConfigurationProperties(prefix = "young.test")
public class YoungTestProperties {

    /**
     * 免认证 URI，多个值的话以逗号分隔
     */
    private String anonUrl;

    private YoungSwaggerProperties swagger = new YoungSwaggerProperties();

}