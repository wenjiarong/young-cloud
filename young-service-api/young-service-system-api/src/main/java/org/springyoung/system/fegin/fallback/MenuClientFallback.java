package org.springyoung.system.fegin.fallback;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springyoung.system.fegin.IMenuClient;

/**
 * @ClassName: MenuClientFallback
 * @Description: alibabacloud
 * @Author: 温家荣
 * @Date: 2020/9/30 11:41
 * @Version: 1.0
 */
@Slf4j
@Component
public class MenuClientFallback implements FallbackFactory<IMenuClient> {

    @Override
    public IMenuClient create(Throwable throwable) {
        return new IMenuClient() {
            @Override
            public String findUserPermissions(String userName) {
                log.error("调用system服务根据用户名称获取权限出错", throwable);
                return null;
            }
        };
    }

}
