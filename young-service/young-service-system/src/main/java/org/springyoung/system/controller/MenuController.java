package org.springyoung.system.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springyoung.core.response.R;
import org.springyoung.system.entity.Menu;
import org.springyoung.system.entity.router.VueRouter;
import org.springyoung.system.service.IMenuService;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Validated
@RestController
@RequestMapping("/menu")
@AllArgsConstructor
public class MenuController {

    private final IMenuService menuService;

    @GetMapping("/{userName}")
    public R<Map<String, Object>> getUserRouters(@NotBlank(message = "{required}") @PathVariable String userName) {
        Map<String, Object> result = new HashMap<>();
        // 构建用户路由对象
        List<VueRouter<Menu>> userRouters = this.menuService.getUserRouters(userName);
        // 获取用户权限信息
        Set<String> userPermissions = this.menuService.findUserPermissions(userName);
        // 组装数据
        result.put("routes", userRouters);
        result.put("permissions", userPermissions);
        return R.data(result);
    }

}