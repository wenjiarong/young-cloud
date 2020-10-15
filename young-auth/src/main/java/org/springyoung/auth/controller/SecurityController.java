package org.springyoung.auth.controller;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springyoung.auth.service.ValidateCodeService;
import org.springyoung.common.exception.ValidateCodeException;
import org.springyoung.common.exception.YoungAuthException;
import org.springyoung.common.response.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@RestController
@AllArgsConstructor
public class SecurityController {

    private final ConsumerTokenServices consumerTokenServices;
    private final ValidateCodeService validateCodeService;

    /**
     * 用于客户端调用生成验证码
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ValidateCodeException
     */
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException, ValidateCodeException {
        validateCodeService.create(request, response);
    }

    /**
     * 获取用户信息:用户获取当前登录用户
     *
     * @param principal
     * @return
     */
    @GetMapping("/user")
    public Principal currentUser(Principal principal) {
        return principal;
    }

    /**
     * 退出登录:signout方法通过ConsumerTokenServices来注销当前Token
     *
     * @param request
     * @return
     * @throws YoungAuthException
     */
    @DeleteMapping("/signout")
    public R signout(HttpServletRequest request) throws YoungAuthException {
        String authorization = request.getHeader("Authorization");
        String token = StringUtils.replace(authorization, "bearer ", "");
        if (!consumerTokenServices.revokeToken(token)) {
            throw new YoungAuthException("退出登录失败");
        }
        return R.data("退出登录成功");
    }

}