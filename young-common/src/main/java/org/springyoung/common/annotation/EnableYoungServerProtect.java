package org.springyoung.common.annotation;

import org.springframework.context.annotation.Import;
import org.springyoung.common.config.YoungServerProtectConfigure;

import java.lang.annotation.*;

/**
 * 定义一个@Enable注解来驱动它,让该配置类YoungServerProtectConfigure生效
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(YoungServerProtectConfigure.class)
public @interface EnableYoungServerProtect {

}