package org.springyoung.system.fegin.fallback;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springyoung.system.entity.User;
import org.springyoung.system.fegin.IUserClient;

/**
 * @ClassName: UserClientFallback
 * @Description: alibabacloud
 * @Author: 温家荣
 * @Date: 2020/9/30 11:41
 * @Version: 1.0
 */
@Slf4j
@Component
public class UserClientFallback implements FallbackFactory<IUserClient> {

    @Override
    public IUserClient create(Throwable throwable) {
        return new IUserClient() {
            @Override
            public User getUserByUserId(Long userId) {
                log.error("调用system服务根据用户ID获取用户信息出错", throwable);
                return null;
            }

            @Override
            public User getUserByUserName(String userName) {
                log.error("调用system服务根据用户名称获取用户信息出错", throwable);
                return null;
            }
        };
    }

}
