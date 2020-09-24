package org.springyoung.common.annotation;

import org.springframework.context.annotation.Import;
import org.springyoung.common.config.YoungLettuceRedisConfigure;

import java.lang.annotation.*;

/**
 * 定义一个@Enable类型注解，让该配置类YoungLettuceRedisConfigure生效
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(YoungLettuceRedisConfigure.class)
public @interface EnableYoungLettuceRedis {

}