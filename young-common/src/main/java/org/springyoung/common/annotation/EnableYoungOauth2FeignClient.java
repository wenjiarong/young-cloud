package org.springyoung.common.annotation;

import org.springframework.context.annotation.Import;
import org.springyoung.common.config.YoungOAuth2FeignConfigure;

import java.lang.annotation.*;

/**
 * 定义一个@Enable类型注解，让该配置类YoungOAuth2FeignConfigure生效
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(YoungOAuth2FeignConfigure.class)
public @interface EnableYoungOauth2FeignClient {

}