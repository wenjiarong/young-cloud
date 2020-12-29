package org.springyoung.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.data.repository.query.Param;
import org.springyoung.system.entity.User;

public interface UserMapper extends BaseMapper<User> {

    /**
     * 通过账号查找用户信息
     *
     * @param userName
     * @return
     */
    User findByUserName(String userName);

    /**
     * 查找用户详细信息
     *
     * @param page 分页对象
     * @param user 用户对象，用于传递查询条件
     * @return Ipage
     */
    IPage<User> findUserDetailPage(IPage page, @Param("user") User user);

}