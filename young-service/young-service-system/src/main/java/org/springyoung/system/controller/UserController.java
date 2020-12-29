package org.springyoung.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springyoung.common.exception.YoungException;
import org.springyoung.core.boot.ctrl.YoungController;
import org.springyoung.core.mp.support.Query;
import org.springyoung.core.secure.YoungUser;
import org.springyoung.core.tool.api.R;
import org.springyoung.system.entity.LoginLog;
import org.springyoung.system.entity.User;
import org.springyoung.system.service.ILoginLogService;
import org.springyoung.system.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Valid对应实体对象传参时的参数校验；
 * @Validated对应普通参数的校验。 在UserController的deleteUsers方法参数上，使用@NotBlank(message = "{required}")注解标注了userIds参数，
 * 该注解表示请求参数不能为空，提示信息为{required}占位符里的内容。@Validated的作用是让@NotBlank注解生效
 * <p>
 * 当普通类型参数校验不合法时，控制器层会抛出javax.validation.ConstraintViolationException异常
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController extends YoungController {

    private final IUserService userService;
    private final ILoginLogService loginLogService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('user:view')")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "用户列表")
    public R userList(Query query, User user) {
        return R.data(userService.findUserDetail(user, query));
    }

    //使用实体对象传参的方式参数校验需要在相应的参数前加上@Valid注解。当校验不通过时，控制器层将抛出BindException类型异常
    @PostMapping
    @PreAuthorize("hasAnyAuthority('user:add')")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "用户新增")
    public R addUser(@Valid User user) throws YoungException {
        try {
            this.userService.createUser(user);
            return R.data(true);
        } catch (Exception e) {
            String message = "新增用户失败";
            log.error(message, e);
            throw new YoungException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('user:update')")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "用户修改")
    public R updateUser(@Valid User user) throws YoungException {
        try {
            this.userService.updateUser(user);
            return R.data(true);
        } catch (Exception e) {
            String message = "修改用户失败";
            log.error(message, e);
            throw new YoungException(message);
        }
    }

    @DeleteMapping("/{userIds}")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "用户删除")
    public R deleteUsers(@NotBlank(message = "{required}") @PathVariable String userIds) throws YoungException {
        try {
            String[] ids = userIds.split(StringPool.COMMA);
            this.userService.deleteUsers(ids);
            return R.data(true);
        } catch (Exception e) {
            String message = "删除用户失败";
            log.error(message, e);
            throw new YoungException(message);
        }
    }

    @GetMapping("/index")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "用户首页")
    public R index() {
        YoungUser user = getUser();
        Map<String, Object> data = new HashMap<>(5);
        // 获取系统访问记录
        Long totalVisitCount = loginLogService.findTotalVisitCount();
        data.put("totalVisitCount", totalVisitCount);
        Long todayVisitCount = loginLogService.findTodayVisitCount();
        data.put("todayVisitCount", todayVisitCount);
        Long todayIp = loginLogService.findTodayIp();
        data.put("todayIp", todayIp);
        // 获取近期系统访问记录
        List<Map<String, Object>> lastTenVisitCount = loginLogService.findLastTenDaysVisitCount(null);
        data.put("lastTenVisitCount", lastTenVisitCount);
        User param = new User();
        param.setId(user.getUserId());
        List<Map<String, Object>> lastTenUserVisitCount = loginLogService.findLastTenDaysVisitCount(param);
        data.put("lastTenUserVisitCount", lastTenUserVisitCount);
        return R.data(data);
    }

    @GetMapping("success")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "登录成功")
    public void loginSuccess(HttpServletRequest request) {
        YoungUser user = getUser();
        Long userId = user.getUserId();
        // update last login time
        this.userService.updateLoginTime(userId);
        // save login log
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(userId);
        loginLog.setSystemBrowserInfo(request.getHeader("user-agent"));
        this.loginLogService.saveLoginLog(loginLog);
    }

}