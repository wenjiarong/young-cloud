package org.springyoung.system.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springyoung.core.constant.YoungServerConstant;

@FeignClient(value = YoungServerConstant.YOUNG_SYSTEM, contextId = "menuClient")
public interface IMenuClient {

    String API_PREFIX = "/client";
    String FIND_USER_PERMISSIONS = API_PREFIX + "/find-user-permissions";

    @GetMapping(FIND_USER_PERMISSIONS)
    String findUserPermissions(@RequestParam Long userId);

}
