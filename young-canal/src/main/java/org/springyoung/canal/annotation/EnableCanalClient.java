package org.springyoung.canal.annotation;

import org.springframework.context.annotation.Import;
import org.springyoung.canal.config.CanalClientConfiguration;
import org.springyoung.canal.config.CanalConfig;

import java.lang.annotation.*;

/**
 * 开启 Canal 客户端
 *
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({CanalConfig.class, CanalClientConfiguration.class})
public @interface EnableCanalClient {
}