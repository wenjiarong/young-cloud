package org.springyoung.common.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springyoung.common.config.YoungAuthExceptionConfigure;
import org.springyoung.common.config.YoungOAuth2FeignConfigure;
import org.springyoung.common.config.YoungServerProtectConfigure;

public class YoungCloudApplicationSelector implements ImportSelector {

    /**
     * @EnableYoungServerProtect，开启微服务防护，避免客户端绕过网关直接请求微服务；
     * @EnableYoungOauth2FeignClient，开启带令牌的Feign请求，避免微服务内部调用出现401异常；
     * @EnableYoungAuthExceptionHandler，认证类型异常翻译。
     * 这三个功能都是微服务提供者必备的功能，所以我们可以定义一个注解将这三个功能整合在一起。
     *
     * 因为这三个注解都是通过@Enable类型注解来将配置类注册到IOC容器中，
     * 所以我们现在要做的就是将这三个配置类一次性都注册到IOC容器中。
     * 在Spring中，要将多个类进行注册，可以使用selector的方式
     *
     * 通过selectImports方法，我们一次性导入了YoungAuthExceptionConfigure、YoungOAuth2FeignConfigure和YoungServerProtectConfigure这三个配置类
     * @param annotationMetadata
     * @return
     */
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                YoungAuthExceptionConfigure.class.getName(),
                YoungOAuth2FeignConfigure.class.getName(),
                YoungServerProtectConfigure.class.getName()
        };
    }

}