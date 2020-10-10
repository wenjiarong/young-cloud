package org.springyoung.system.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springyoung.core.constant.YoungServerConstant;
import org.springyoung.system.fegin.fallback.MenuClientFallback;

@FeignClient(value = YoungServerConstant.YOUNG_SERVER_SYSTEM, contextId = "menuClient",
        fallbackFactory = MenuClientFallback.class)
public interface IMenuClient {

    String API_PREFIX = "/client";
    String FIND_USER_PERMISSIONS = API_PREFIX + "/find-user-permissions";

    @GetMapping(FIND_USER_PERMISSIONS)
    String findUserPermissions(@RequestParam String userName);

}
