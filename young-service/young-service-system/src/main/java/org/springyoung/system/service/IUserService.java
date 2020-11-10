package org.springyoung.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springyoung.core.mp.support.Query;
import org.springyoung.system.entity.User;

/**
 * 接口定义了用户模块的增删改查抽象方法,其中Query为通用查询参数类,用于接收前端上送的分页和排序信息
 */
public interface IUserService extends IService<User> {

    /**
     * 查找用户详细信息
     *
     * @param query 分页
     * @param user  用户对象，用于传递查询条件
     * @return IPage
     */
    IPage<User> findUserDetail(User user, Query query);

    /**
     * 新增用户
     *
     * @param user user
     */
    void createUser(User user);

    /**
     * 修改用户
     *
     * @param user user
     */
    void updateUser(User user);

    /**
     * 删除用户
     *
     * @param userIds 用户 id数组
     */
    void deleteUsers(String[] userIds);

    /**
     * 通过账号查找用户信息
     *
     * @param account
     * @return
     */
    User findByAccount(String account);

}