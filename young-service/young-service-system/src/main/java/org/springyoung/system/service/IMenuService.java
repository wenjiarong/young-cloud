package org.springyoung.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springyoung.system.entity.Menu;
import org.springyoung.system.entity.router.VueRouter;

import java.util.List;
import java.util.Set;

public interface IMenuService extends IService<Menu> {

    /**
     * 通过用户id查询用户权限信息
     *
     * @param userId 用户id
     * @return 权限信息
     */
    Set<String> findUserPermissions(Long userId);

    /**
     * 通过用户id创建对应的 Vue路由信息
     *
     * @param userId 用户id
     * @return 路由信息
     */
    List<VueRouter<Menu>> getUserRouters(Long userId);

}