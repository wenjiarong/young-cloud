package org.springyoung.core.tool.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Objects;
import java.util.function.Predicate;

public class WebUtil extends WebUtils {
    private static final Logger log = LoggerFactory.getLogger(WebUtil.class);
    public static final String USER_AGENT_HEADER = "user-agent";
    private static final String[] IP_HEADER_NAMES = new String[]{"x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
    private static final Predicate<String> IP_PREDICATE = (ip) -> {
        return StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip);
    };

    public WebUtil() {
    }

    @Nullable
    public static String getCookieVal(String name) {
        HttpServletRequest request = getRequest();
        Assert.notNull(request, "request from RequestContextHolder is null");
        return getCookieVal(request, name);
    }

    @Nullable
    public static String getCookieVal(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        return cookie != null ? cookie.getValue() : null;
    }

    public static void removeCookie(HttpServletResponse response, String key) {
        setCookie(response, key, (String)null, 0);
    }

    public static void setCookie(HttpServletResponse response, String name, @Nullable String value, int maxAgeInSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeInSeconds);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return requestAttributes == null ? null : ((ServletRequestAttributes)requestAttributes).getRequest();
    }

    public static String getHeader(String name) {
        HttpServletRequest request = getRequest();
        return ((HttpServletRequest)Objects.requireNonNull(request)).getHeader(name);
    }

    public static Enumeration<String> getHeaders(String name) {
        HttpServletRequest request = getRequest();
        return ((HttpServletRequest)Objects.requireNonNull(request)).getHeaders(name);
    }

    public static Enumeration<String> getHeaderNames() {
        HttpServletRequest request = getRequest();
        return ((HttpServletRequest)Objects.requireNonNull(request)).getHeaderNames();
    }

    public static String getParameter(String name) {
        HttpServletRequest request = getRequest();
        return ((HttpServletRequest)Objects.requireNonNull(request)).getParameter(name);
    }

    public static String getRequestBody(ServletInputStream servletInputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(servletInputStream, StandardCharsets.UTF_8));
            String line;
            while((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException var16) {
            var16.printStackTrace();
        } finally {
            if (servletInputStream != null) {
                try {
                    servletInputStream.close();
                } catch (IOException var15) {
                    var15.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException var14) {
                    var14.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

}
