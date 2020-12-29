package org.springyoung.core.secure.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springyoung.core.constant.RoleConstant;
import org.springyoung.core.constant.StringPool;
import org.springyoung.core.constant.TokenConstant;
import org.springyoung.core.constant.YoungConstant;
import org.springyoung.core.secure.YoungUser;
import org.springyoung.core.tool.utils.Func;
import org.springyoung.core.tool.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/**
 * Auth工具类
 */
public class AuthUtil {
    private static final String YOUNG_USER_REQUEST_ATTR = "_YOUNG_USER_REQUEST_ATTR_";

    private final static String HEADER = TokenConstant.HEADER;
    private final static String LOGIN_NAME = TokenConstant.LOGIN_NAME;
    private final static String USER_NAME = TokenConstant.USER_NAME;
    private final static String USER_ID = TokenConstant.USER_ID;
    private final static String DEPT_ID = TokenConstant.DEPT_ID;
    private final static String ROLE_IDS = TokenConstant.ROLE_IDS;
    private final static String ROLE_NAMES = TokenConstant.ROLE_NAMES;
    private final static String TENANT_ID = TokenConstant.TENANT_ID;

    /**
     * 获取用户信息
     *
     * @return YoungUser
     */
    public static YoungUser getUser() {
        HttpServletRequest request = WebUtil.getRequest();
        if (request == null) {
            return null;
        }
        // 优先从 request 中获取
        Object youngUser = request.getAttribute(YOUNG_USER_REQUEST_ATTR);
        if (youngUser == null) {
            youngUser = getUser(request);
            if (youngUser != null) {
                // 设置到 request 中
                request.setAttribute(YOUNG_USER_REQUEST_ATTR, youngUser);
            }
        }
        return (YoungUser) youngUser;
    }

    /**
     * 获取用户信息
     *
     * @param request request
     * @return YoungUser
     */
    public static YoungUser getUser(HttpServletRequest request) {
        Claims claims = getClaims(request);
        if (claims == null) {
            return null;
        }
        Long userId = Func.toLong(claims.get(AuthUtil.USER_ID));
        String tenantId = Func.toStr(claims.get(AuthUtil.TENANT_ID));
        Long deptId = Func.toLong(claims.get(AuthUtil.DEPT_ID));
        String roleIds = Func.toStrWithEmpty(claims.get(AuthUtil.ROLE_IDS), StringPool.MINUS_ONE);
        String roleNames = Func.toStrWithEmpty(claims.get(AuthUtil.ROLE_NAMES), StringPool.MINUS_ONE);
        String loginName = Func.toStr(claims.get(AuthUtil.LOGIN_NAME));
        String userName = Func.toStr(claims.get(AuthUtil.USER_NAME));
        YoungUser youngUser = new YoungUser();
        youngUser.setUserId(userId);
        youngUser.setTenantId(tenantId);
        youngUser.setAccount(loginName);
        youngUser.setDeptId(deptId);
        youngUser.setRoleIds(roleIds);
        youngUser.setRoleNames(roleNames);
        youngUser.setUserName(userName);
        return youngUser;
    }

    /**
     * 是否为超管
     *
     * @return boolean
     */
    public static boolean isAdministrator() {
        return StringUtils.containsAny(getUserRole(), RoleConstant.ADMINISTRATOR);
    }

    /**
     * 获取用户角色
     *
     * @return userName
     */
    public static String getUserRole() {
        YoungUser user = getUser();
        return (null == user) ? StringPool.EMPTY : user.getRoleNames();
    }


    /**
     * 获取Claims
     *
     * @param request request
     * @return Claims
     */
    public static Claims getClaims(HttpServletRequest request) {
        String auth = request.getHeader(AuthUtil.HEADER);
        Claims claims = null;
        String token;
        // 获取 Token 参数
        if (StringUtils.isNotBlank(auth)) {
            token = getToken(auth);
        } else {
            String parameter = request.getParameter(AuthUtil.HEADER);
            token = getToken(parameter);
        }
        // 获取 Token 值
        if (StringUtils.isNotBlank(token)) {
            claims = AuthUtil.parseJWT(token);
        }
        return claims;
    }

    /**
     * 获取请求头
     *
     * @return header
     */
    public static String getHeader() {
        return getHeader(Objects.requireNonNull(WebUtil.getRequest()));
    }

    /**
     * 获取请求头
     *
     * @param request request
     * @return header
     */
    public static String getHeader(HttpServletRequest request) {
        return request.getHeader(HEADER);
    }

    /**
     * 获取请求传递的token串
     *
     * @param auth token
     * @return String
     */
    public static String getToken(String auth) {
        if ((auth != null) && (auth.length() > TokenConstant.BEARER.length())) {
            String headStr = auth.substring(0, 6).toLowerCase();
            if (headStr.compareTo(TokenConstant.BEARER) == 0) {
                auth = auth.substring(7);
            }
            return auth;
        }
        return null;
    }

    /**
     * 解析jsonWebToken
     *
     * @param jsonWebToken token串
     * @return Claims
     */
    public static Claims parseJWT(String jsonWebToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(Base64.getDecoder().decode(getBase64Security()))
                    .parseClaimsJws(jsonWebToken).getBody();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 签名加密
     */
    public static String getBase64Security() {
        return Base64.getEncoder().encodeToString(YoungConstant.JWT_KEY.getBytes(StandardCharsets.UTF_8));
    }

}