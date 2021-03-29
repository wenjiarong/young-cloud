package org.springyoung.canal.annotation;

import org.springframework.context.annotation.Import;
import org.springyoung.canal.config.CanalClientConfigure;
import org.springyoung.canal.config.CanalConfigure;

import java.lang.annotation.*;

/**
 * 开启 Canal 客户端
 *
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({CanalConfigure.class, CanalClientConfigure.class})
public @interface EnableCanalClient {
}