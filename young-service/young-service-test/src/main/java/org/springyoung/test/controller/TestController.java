package org.springyoung.test.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springyoung.common.response.R;
import org.springyoung.system.entity.User;
import org.springyoung.system.fegin.IUserClient;

import java.security.Principal;

@RestController
@AllArgsConstructor
public class TestController {

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
    public R getUserByUserId(@RequestParam String userId) {
        return R.data(userClient.getUserByUserId(Long.valueOf(userId)));
    }

    @GetMapping("/user")
    public R currentUser(Principal principal) {
        return R.data(principal);
    }

}