package org.springyoung.system.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:young-server-system.properties"})
@ConfigurationProperties(prefix = "young.server.system")
public class YoungServerSystemProperties {

    /**
     * 免认证 URI，多个值的话以逗号分隔
     */
    private String anonUrl;

}