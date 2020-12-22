package org.springyoung.auth.filter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springyoung.auth.service.ValidateCodeService;
import org.springyoung.common.exception.ValidateCodeException;
import org.springyoung.core.tool.api.R;
import org.springyoung.core.tool.api.ResultCode;
import org.springyoung.common.utils.YoungUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 定义一个过滤器，用于拦截请求并校验验证码的正确性
 * <p>
 * ValidateCodeFilter继承Spring Boot提供的OncePerRequestFilter，
 * 该过滤器实现了javax.servlet.filter接口，顾名思义，它可以确保我们的逻辑只被执行一次：
 */
@Slf4j
@Component
@AllArgsConstructor
public class ValidateCodeFilter extends OncePerRequestFilter {

    private final ValidateCodeService validateCodeService;

    /**
     * 在ValidateCodeFilter的doFilterInternal方法中，我们编写了验证码校验逻辑：
     * 当拦截的请求URI为/oauth/token，请求方法为POST并且
     * 请求参数grant_type为password的时候（对应密码模式获取令牌请求），
     * 需要进行验证码校验。验证码校验调用的是之前定义的ValidateCodeService的check方法。
     * 当验证码调用通过时调用filterChain.doFilter(httpServletRequest, httpServletResponse)，
     * 让过滤器链继续往下执行，校验不通过时直接返回错误响应。
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String header = httpServletRequest.getHeader("Authorization");
        //获取了ClientId后，我们判断ClientId是否为swagger，是的话无需进行图形验证码校验
        String clientId = getClientId(header, httpServletRequest);

        RequestMatcher matcher = new AntPathRequestMatcher("/oauth/token", HttpMethod.POST.toString());
        if (matcher.matches(httpServletRequest)
                && StringUtils.equalsIgnoreCase(httpServletRequest.getParameter("grant_type"), "password")
                //获取了ClientId后，我们判断ClientId是否为swagger，是的话无需进行图形验证码校验
                && !StringUtils.equalsAnyIgnoreCase(clientId, "swagger")) {
            try {
                //校验验证码
                validateCode(httpServletRequest);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } catch (ValidateCodeException e) {
                YoungUtil.makeResponse(httpServletResponse, MediaType.APPLICATION_JSON_VALUE,
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR, R.fail(ResultCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage()));
                log.error(e.getMessage(), e);
            }
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    //校验验证码
    private void validateCode(HttpServletRequest httpServletRequest) throws ValidateCodeException {
        String code = httpServletRequest.getParameter("code");
        String key = httpServletRequest.getParameter("key");
        validateCodeService.check(key, code);
    }

    //getClientId这个方法用于从请求头部获取ClientId信息，这段代码是从Spring Cloud OAuth2源码中拷贝过来的
    private String getClientId(String header, HttpServletRequest request) {
        String clientId = "";
        try {
            byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
            byte[] decoded;
            decoded = Base64.getDecoder().decode(base64Token);

            String token = new String(decoded, StandardCharsets.UTF_8);
            int delim = token.indexOf(":");
            if (delim != -1) {
                clientId = new String[]{token.substring(0, delim), token.substring(delim + 1)}[0];
            }
        } catch (Exception ignore) {
        }
        return clientId;
    }

}