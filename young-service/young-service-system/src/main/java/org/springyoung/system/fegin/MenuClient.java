package org.springyoung.system.fegin;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springyoung.system.service.IMenuService;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName: MenuClient
 * @Description: alibabacloud
 * @Author: 温家荣
 * @Date: 2020/9/30 14:26
 * @Version: 1.0
 */
@RestController
@AllArgsConstructor
public class MenuClient implements IMenuClient {

    private final IMenuService menuService;

    @Override
    @GetMapping(FIND_USER_PERMISSIONS)
    public String findUserPermissions(String userName) {
        Set<String> userPermissions = menuService.findUserPermissions(userName);
        return userPermissions.stream().collect(Collectors.joining(","));
    }

}
