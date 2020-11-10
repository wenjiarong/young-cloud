package org.springyoung.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springyoung.system.entity.Menu;

import java.util.List;

/**
 *  实现根据用户获取菜单信息，并将菜单转换为包含上下级结构的路由信息的功能
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 通过用户id查找用户权限集合
     *
     * @param userId 用户id
     * @return 权限信息
     */
    List<Menu> findUserPermissions(Long userId);

    /**
     * 通过用户id查询菜单信息
     *
     * @param userId 用户id
     * @return 菜单信息
     */
    List<Menu> findUserMenus(Long userId);

}