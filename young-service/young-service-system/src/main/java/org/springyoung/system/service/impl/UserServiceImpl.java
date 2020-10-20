package org.springyoung.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springyoung.core.mp.support.Condition;
import org.springyoung.core.mp.support.Query;
import org.springyoung.system.entity.User;
import org.springyoung.system.entity.UserRole;
import org.springyoung.system.mapper.UserMapper;
import org.springyoung.system.service.IUserRoleService;
import org.springyoung.system.service.IUserService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final IUserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public IPage<User> findUserDetail(User user, Query query) {
        return baseMapper.findUserDetailPage(Condition.getPage(query), user);
    }

    @Override
    @Transactional
    public void createUser(User user) {
        // 创建用户
        user.setCreateTime(new Date());
        user.setImage(User.DEFAULT_AVATAR);
        user.setPassword(passwordEncoder.encode(User.DEFAULT_PASSWORD));
        save(user);
        // 保存用户角色
        String[] roles = user.getRoleIds().split(StringPool.COMMA);
        setUserRoles(user, roles);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        // 更新用户
        user.setPassword(null);
        user.setUserName(null);
        user.setCreateTime(null);
        user.setUpdateTime(new Date());
        updateById(user);

        userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId()));
        String[] roles = user.getRoleIds().split(StringPool.COMMA);
        setUserRoles(user, roles);
    }

    @Override
    @Transactional
    public void deleteUsers(String[] userIds) {
        List<String> list = Arrays.asList(userIds);
        removeByIds(list);
        // 删除用户角色
        userRoleService.deleteUserRolesByUserId(userIds);
    }

    @Override
    public User findByName(String userName) {
        return baseMapper.findByName(userName);
    }

    private void setUserRoles(User user, String[] roles) {
        Arrays.stream(roles).forEach(roleId -> {
            UserRole ur = new UserRole();
            ur.setUserId(user.getId());
            ur.setRoleId(Long.valueOf(roleId));
            userRoleService.save(ur);
        });
    }

}