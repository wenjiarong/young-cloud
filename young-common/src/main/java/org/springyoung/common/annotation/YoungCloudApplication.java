package org.springyoung.common.annotation;

import org.springframework.context.annotation.Import;
import org.springyoung.common.selector.YoungCloudApplicationSelector;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(YoungCloudApplicationSelector.class)
public @interface YoungCloudApplication {

}