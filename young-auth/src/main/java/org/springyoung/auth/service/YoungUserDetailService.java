package org.springyoung.auth.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springyoung.auth.entity.YoungAuthUser;

/**
 * 定义一个用于校验用户名密码的类
 * <p>
 * UserDetailService实现了UserDetailsService接口的loadUserByUsername方法。
 * loadUserByUsername方法返回一个UserDetails对象，该对象也是一个接口，
 * 包含一些用于描述用户信息的方法
 *
 * 这些方法的含义如下：
 * getAuthorities获取用户包含的权限，返回权限集合，权限是一个继承了GrantedAuthority的对象；
 * getPassword和getUsername用于获取密码和用户名；
 * isAccountNonExpired方法返回boolean类型，用于判断账户是否未过期，未过期返回true反之返回false；
 * isAccountNonLocked方法用于判断账户是否未锁定；
 * isCredentialsNonExpired用于判断用户凭证是否没过期，即密码是否未过期；
 * isEnabled方法用于判断用户是否可用。
 */
@Service
public class YoungUserDetailService implements UserDetailsService {

    /**
     * 通过查询数据库的方式获取用户
     * 通过用户名从数据库中获取用户信息SystemUser和用户权限集合，
     * 然后通过transSystemUserToAuthUser方法将SystemUser里的值拷贝到YoungAuthUser中并返回
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserManager userManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser systemUser = userManager.findByName(username);
        if (systemUser != null) {
            String permissions = userManager.findUserPermissions(systemUser.getUsername());
            boolean notLocked = false;
            if (StringUtils.equals(SystemUser.STATUS_VALID, systemUser.getStatus())) {
                notLocked = true;
            }
            YoungAuthUser authUser = new YoungAuthUser(systemUser.getUsername(), systemUser.getPassword(), true, true, true, notLocked,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));

            //两个实体类值的拷贝Spring给我们提供了相应的工具类
            BeanUtils.copyProperties(systemUser, authUser);
            return authUser;
        } else {
            throw new UsernameNotFoundException("");
        }
    }

}