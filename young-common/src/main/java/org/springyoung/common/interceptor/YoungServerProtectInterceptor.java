package org.springyoung.common.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springyoung.core.constant.YoungConstant;
import org.springyoung.common.response.R;
import org.springyoung.common.utils.YoungUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * YoungServerProtectInterceptor实现了HandlerInterceptor的preHandle方法，
 * 该拦截器可以拦截所有Web请求。在preHandle方法中，
 * 我们通过HttpServletRequest获取请求头中的gateway Token，
 * 并校验其正确性，当校验不通过的时候返回403错误。
 */
public class YoungServerProtectInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 从请求头中获取 gateway Token
        String token = request.getHeader(YoungConstant.GATEWAY_TOKEN_HEADER);
        String gatewayToken = new String(Base64Utils.encode(YoungConstant.GATEWAY_TOKEN_VALUE.getBytes()));
        // 校验 gateway Token的正确性
        if (StringUtils.equals(gatewayToken, token)) {
            return true;
        } else {
            YoungUtil.makeResponse(response, MediaType.APPLICATION_JSON_VALUE, HttpServletResponse.SC_FORBIDDEN, R.fail("请通过网关获取资源"));
            return false;
        }
    }

}