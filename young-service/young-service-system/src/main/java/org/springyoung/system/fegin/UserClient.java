package org.springyoung.system.fegin;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springyoung.system.entity.User;
import org.springyoung.system.service.IUserService;

/**
 * @ClassName: UserClient
 * @Description: alibabacloud
 * @Author: 温家荣-wjr
 * @Date: 2020/9/30 14:26
 * @Version: 1.0
 */
@RestController
@AllArgsConstructor
public class UserClient implements IUserClient {

    private final IUserService userService;

    @Override
    @GetMapping(GET_USER_BY_USERID)
    public User getUserByUserId(Long userId) {
        return userService.getById(userId);
    }

}
