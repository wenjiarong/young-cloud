package org.springyoung.core.secure.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springyoung.core.constant.RoleConstant;
import org.springyoung.core.constant.StringPool;
import org.springyoung.core.constant.TokenConstant;
import org.springyoung.core.jwt.props.JwtProperties;
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
    private final static String ACCOUNT = TokenConstant.ACCOUNT;
    private final static String USER_NAME = TokenConstant.USER_NAME;
    private final static String NICK_NAME = TokenConstant.NICK_NAME;
    private final static String USER_ID = TokenConstant.USER_ID;
    private final static String DEPT_ID = TokenConstant.DEPT_ID;
    private final static String POST_ID = TokenConstant.POST_ID;
    private final static String ROLE_ID = TokenConstant.ROLE_ID;
    private final static String ROLE_NAME = TokenConstant.ROLE_NAME;
    private final static String TENANT_ID = TokenConstant.TENANT_ID;
    private final static String OAUTH_ID = TokenConstant.OAUTH_ID;
    private final static String CLIENT_ID = TokenConstant.CLIENT_ID;

    /**
     * jwt配置
     */
    private static JwtProperties jwtProperties;

    public static JwtProperties getJwtProperties() {
        return jwtProperties;
    }

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
        String clientId = Func.toStr(claims.get(AuthUtil.CLIENT_ID));
        Long userId = Func.toLong(claims.get(AuthUtil.USER_ID));
        String tenantId = Func.toStr(claims.get(AuthUtil.TENANT_ID));
        String oauthId = Func.toStr(claims.get(AuthUtil.OAUTH_ID));
        String deptId = Func.toStrWithEmpty(claims.get(AuthUtil.DEPT_ID), StringPool.MINUS_ONE);
        String postId = Func.toStrWithEmpty(claims.get(AuthUtil.POST_ID), StringPool.MINUS_ONE);
        String roleId = Func.toStrWithEmpty(claims.get(AuthUtil.ROLE_ID), StringPool.MINUS_ONE);
        String account = Func.toStr(claims.get(AuthUtil.ACCOUNT));
        String roleName = Func.toStr(claims.get(AuthUtil.ROLE_NAME));
        String userName = Func.toStr(claims.get(AuthUtil.USER_NAME));
        String nickName = Func.toStr(claims.get(AuthUtil.NICK_NAME));
        YoungUser youngUser = new YoungUser();
        youngUser.setClientId(clientId);
        youngUser.setUserId(userId);
        youngUser.setTenantId(tenantId);
        youngUser.setOauthId(oauthId);
        youngUser.setAccount(account);
        youngUser.setDeptId(deptId);
        youngUser.setPostId(postId);
        youngUser.setRoleId(roleId);
        youngUser.setRoleName(roleName);
        youngUser.setUserName(userName);
        youngUser.setNickName(nickName);
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
     * 获取用户id
     *
     * @return userId
     */
    public static Long getUserId() {
        YoungUser user = getUser();
        return (null == user) ? -1 : user.getUserId();
    }

    /**
     * 获取用户id
     *
     * @param request request
     * @return userId
     */
    public static Long getUserId(HttpServletRequest request) {
        YoungUser user = getUser(request);
        return (null == user) ? -1 : user.getUserId();
    }

    /**
     * 获取用户账号
     *
     * @return userAccount
     */
    public static String getUserAccount() {
        YoungUser user = getUser();
        return (null == user) ? StringPool.EMPTY : user.getAccount();
    }

    /**
     * 获取用户账号
     *
     * @param request request
     * @return userAccount
     */
    public static String getUserAccount(HttpServletRequest request) {
        YoungUser user = getUser(request);
        return (null == user) ? StringPool.EMPTY : user.getAccount();
    }

    /**
     * 获取用户名
     *
     * @return userName
     */
    public static String getUserName() {
        YoungUser user = getUser();
        return (null == user) ? StringPool.EMPTY : user.getUserName();
    }

    /**
     * 获取用户名
     *
     * @param request request
     * @return userName
     */
    public static String getUserName(HttpServletRequest request) {
        YoungUser user = getUser(request);
        return (null == user) ? StringPool.EMPTY : user.getUserName();
    }

    /**
     * 获取昵称
     *
     * @return userName
     */
    public static String getNickName() {
        YoungUser user = getUser();
        return (null == user) ? StringPool.EMPTY : user.getNickName();
    }

    /**
     * 获取昵称
     *
     * @param request request
     * @return userName
     */
    public static String getNickName(HttpServletRequest request) {
        YoungUser user = getUser(request);
        return (null == user) ? StringPool.EMPTY : user.getNickName();
    }

    /**
     * 获取用户部门
     *
     * @return userName
     */
    public static String getDeptId() {
        YoungUser user = getUser();
        return (null == user) ? StringPool.EMPTY : user.getDeptId();
    }

    /**
     * 获取用户部门
     *
     * @param request request
     * @return userName
     */
    public static String getDeptId(HttpServletRequest request) {
        YoungUser user = getUser(request);
        return (null == user) ? StringPool.EMPTY : user.getDeptId();
    }

    /**
     * 获取用户岗位
     *
     * @return userName
     */
    public static String getPostId() {
        YoungUser user = getUser();
        return (null == user) ? StringPool.EMPTY : user.getPostId();
    }

    /**
     * 获取用户岗位
     *
     * @param request request
     * @return userName
     */
    public static String getPostId(HttpServletRequest request) {
        YoungUser user = getUser(request);
        return (null == user) ? StringPool.EMPTY : user.getPostId();
    }

    /**
     * 获取用户角色
     *
     * @return userName
     */
    public static String getUserRole() {
        YoungUser user = getUser();
        return (null == user) ? StringPool.EMPTY : user.getRoleName();
    }

    /**
     * 获取用角色
     *
     * @param request request
     * @return userName
     */
    public static String getUserRole(HttpServletRequest request) {
        YoungUser user = getUser(request);
        return (null == user) ? StringPool.EMPTY : user.getRoleName();
    }

    /**
     * 获取租户ID
     *
     * @return tenantId
     */
    public static String getTenantId() {
        YoungUser user = getUser();
        return (null == user) ? StringPool.EMPTY : user.getTenantId();
    }

    /**
     * 获取租户ID
     *
     * @param request request
     * @return tenantId
     */
    public static String getTenantId(HttpServletRequest request) {
        YoungUser user = getUser(request);
        return (null == user) ? StringPool.EMPTY : user.getTenantId();
    }

    /**
     * 获取第三方认证ID
     *
     * @return tenantId
     */
    public static String getOauthId() {
        YoungUser user = getUser();
        return (null == user) ? StringPool.EMPTY : user.getOauthId();
    }

    /**
     * 获取第三方认证ID
     *
     * @param request request
     * @return tenantId
     */
    public static String getOauthId(HttpServletRequest request) {
        YoungUser user = getUser(request);
        return (null == user) ? StringPool.EMPTY : user.getOauthId();
    }

    /**
     * 获取客户端id
     *
     * @return clientId
     */
    public static String getClientId() {
        YoungUser user = getUser();
        return (null == user) ? StringPool.EMPTY : user.getClientId();
    }

    /**
     * 获取客户端id
     *
     * @param request request
     * @return clientId
     */
    public static String getClientId(HttpServletRequest request) {
        YoungUser user = getUser(request);
        return (null == user) ? StringPool.EMPTY : user.getClientId();
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
        return Base64.getEncoder().encodeToString(getJwtProperties().getSignKey().getBytes(StandardCharsets.UTF_8));
    }

}