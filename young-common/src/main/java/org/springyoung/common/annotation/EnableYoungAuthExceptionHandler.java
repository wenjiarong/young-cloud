package org.springyoung.common.annotation;

import org.springframework.context.annotation.Import;
import org.springyoung.common.config.YoungAuthExceptionConfigure;

import java.lang.annotation.*;

/**
 *  定义一个@Enable类型注解，让该配置类YoungAuthExceptionConfigure生效
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(YoungAuthExceptionConfigure.class)
public @interface EnableYoungAuthExceptionHandler {

}