package org.springyoung.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springyoung.common.exception.YoungException;
import org.springyoung.common.response.R;
import org.springyoung.core.mp.support.Query;
import org.springyoung.system.entity.User;
import org.springyoung.system.service.IUserService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @Valid对应实体对象传参时的参数校验；
 * @Validated对应普通参数的校验。 在UserController的deleteUsers方法参数上，使用@NotBlank(message = "{required}")注解标注了userIds参数，
 * 该注解表示请求参数不能为空，提示信息为{required}占位符里的内容。@Validated的作用是让@NotBlank注解生效
 *
 * 当普通类型参数校验不合法时，控制器层会抛出javax.validation.ConstraintViolationException异常
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('user:view')")
    public R userList(Query query, User user) {
        return R.data(userService.findUserDetail(user, query));
    }

    //使用实体对象传参的方式参数校验需要在相应的参数前加上@Valid注解。当校验不通过时，控制器层将抛出BindException类型异常
    @PostMapping
    @PreAuthorize("hasAnyAuthority('user:add')")
    public void addUser(@Valid User user) throws YoungException {
        try {
            this.userService.createUser(user);
        } catch (Exception e) {
            String message = "新增用户失败";
            log.error(message, e);
            throw new YoungException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('user:update')")
    public void updateUser(@Valid User user) throws YoungException {
        try {
            this.userService.updateUser(user);
        } catch (Exception e) {
            String message = "修改用户失败";
            log.error(message, e);
            throw new YoungException(message);
        }
    }

    @DeleteMapping("/{userIds}")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public void deleteUsers(@NotBlank(message = "{required}") @PathVariable String userIds) throws YoungException {
        try {
            String[] ids = userIds.split(StringPool.COMMA);
            this.userService.deleteUsers(ids);
        } catch (Exception e) {
            String message = "删除用户失败";
            log.error(message, e);
            throw new YoungException(message);
        }
    }

}