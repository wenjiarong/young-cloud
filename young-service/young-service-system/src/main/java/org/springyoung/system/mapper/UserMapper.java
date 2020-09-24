package org.springyoung.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.data.repository.query.Param;
import org.springyoung.system.entity.User;

public interface UserMapper extends BaseMapper<User> {

    User findByName(String username);

    /**
     * 查找用户详细信息
     *
     * @param page 分页对象
     * @param user 用户对象，用于传递查询条件
     * @return Ipage
     */
    IPage<User> findUserDetailPage(IPage page, @Param("user") User user);

}