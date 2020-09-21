package org.springyoung.gateway.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName YoungGatewayProperties
 * @Description TODO
 * @Author 小温
 * @Date 2020/9/21 9:50
 * @Version 1.0
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:young-gateway.properties"})
@ConfigurationProperties(prefix = "young.gateway")
public class YoungGatewayProperties {

    /**
     * 禁止外部访问的 URI，多个值的话以逗号分隔
     */
    private String forbidRequestUri;

}
