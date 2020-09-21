package org.springyoung.common.handler;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springyoung.common.response.R;
import org.springyoung.common.response.ResultCode;
import org.springyoung.common.utils.YoungUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 资源服务器,用户无权限返回403异常
 */
public class YoungAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        YoungUtil.makeResponse(
                response, MediaType.APPLICATION_JSON_UTF8_VALUE,
                HttpServletResponse.SC_FORBIDDEN, R.fail(ResultCode.INTERNAL_SERVER_ERROR.getCode(), "没有权限访问该资源"));
    }

}