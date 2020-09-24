package org.springyoung.auth.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * Auth相关的配置类:
 * 自动装配young-auth.properties文件的属性，自定义配置类
 * <p>
 * clients属性类型为上面定义的YoungClientsProperties，
 * 因为一个认证服务器可以根据多种Client来发放对应的令牌，
 * 所以这个属性使用的是数组形式；
 * accessTokenValiditySeconds用于指定access_token的有效时间，默认值为60 * 60 * 24秒；
 * refreshTokenValiditySeconds用于指定refresh_token的有效时间，默认值为60 * 60 * 24 * 7秒。
 *
 * @PropertySource(value = {"classpath:young-auth.properties"})用于指定读取的配置文件路径；
 * @ConfigurationProperties(prefix = "young.auth")指定了要读取的属性的统一前缀名称为young.auth；
 * @SpringBootConfiguration实质上为@Component的派生注解，用于将YoungAuthProperties纳入到IOC容器中
 */
@Data
@SpringBootConfiguration  //@Component的派生注解
@PropertySource(value = {"classpath:young-auth.properties"})
@ConfigurationProperties(prefix = "young.auth")
public class YoungAuthProperties {

    //认证服务器可以根据多种Client来发放对应的令牌
    private YoungClientsProperties[] clients = {};
    //access_token的有效时间
    private int accessTokenValiditySeconds = 60 * 60 * 24;
    //refresh_token的有效时间
    private int refreshTokenValiditySeconds = 60 * 60 * 24 * 7;
    // 免认证路径
    private String anonUrl;

    //验证码配置类
    private YoungValidateCodeProperties code = new YoungValidateCodeProperties();

}