package org.springyoung.test.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springyoung.core.boot.ctrl.YoungController;
import org.springyoung.core.response.R;
import org.springyoung.core.secure.YoungUser;
import org.springyoung.system.fegin.IUserClient;
import org.springyoung.test.service.ITradeLogService;

import java.security.Principal;

@RestController
@AllArgsConstructor
@Slf4j
public class TestController extends YoungController {

    private final IUserClient userClient;

    @GetMapping("/test1")
    @PreAuthorize("hasAnyAuthority('user:add')")
    public R test1() {
        return R.data("拥有'user:add'权限");
    }

    @GetMapping("/test2")
    @PreAuthorize("hasAnyAuthority('user:update')")
    public R test2() {
        return R.data("拥有'user:update'权限");
    }

    @GetMapping("/test3")
    public R getUserByUserId() {
        YoungUser user = getUser();
        log.info("远程调用system服务测试");
        return R.data(userClient.getUserByUserId(user.getUserId()));
    }

}