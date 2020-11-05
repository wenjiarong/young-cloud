package org.springyoung.common.handler;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springyoung.core.response.R;
import org.springyoung.core.response.ResultCode;
import org.springyoung.common.utils.YoungUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 资源服务器令牌不正确返回401异常
 */
public class YoungAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    //HttpServletResponse.SC_UNAUTHORIZED，即401。
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        YoungUtil.makeResponse(
                response, MediaType.APPLICATION_JSON_VALUE,
                HttpServletResponse.SC_UNAUTHORIZED, R.fail(ResultCode.INTERNAL_SERVER_ERROR.getCode(), "token无效")
        );
    }

}