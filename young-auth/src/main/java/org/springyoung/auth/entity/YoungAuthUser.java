package org.springyoung.auth.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;

/**
 * 自定义的用户实体类，代表从数据库中查询出来的用户
 * 继承org.springframework.security.core.userdetails.User，并添加一些字段，丰富用户信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class YoungAuthUser extends User {

    private static final long serialVersionUID = -6411066541689297219L;

    private Long userId;

    private String account;

    private String userName;

    private String image;

    private String tenantId;

    private String email;

    private String mobile;

    private String sex;

    private Long deptId;

    private String deptName;

    private String roleIds;

    private String roleNames;

    private Date lastLoginTime;

    private String remark;

    private String status;

    public YoungAuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public YoungAuthUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

}