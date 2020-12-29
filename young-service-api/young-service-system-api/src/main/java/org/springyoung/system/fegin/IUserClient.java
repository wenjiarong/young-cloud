package org.springyoung.system.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springyoung.core.constant.YoungServerConstant;
import org.springyoung.system.entity.User;

@FeignClient(value = YoungServerConstant.YOUNG_SYSTEM, contextId = "userClient")
public interface IUserClient {

    String API_PREFIX = "/client";
    String GET_USER_BY_USERID = API_PREFIX + "/get-user-by-userid";
    String GET_USER_BY_USERNAME = API_PREFIX + "/get-user-by-username";

    @GetMapping(GET_USER_BY_USERID)
    User getUserByUserId(@RequestParam Long userId);

    @GetMapping(GET_USER_BY_USERNAME)
    User getUserByUserName(@RequestParam String userName);

}
