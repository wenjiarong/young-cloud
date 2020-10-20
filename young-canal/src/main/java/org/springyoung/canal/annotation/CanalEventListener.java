package org.springyoung.canal.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * canal 监听器注解，继承 @Component
 *
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface CanalEventListener {

    @AliasFor(annotation = Component.class)
    String value() default "";

}