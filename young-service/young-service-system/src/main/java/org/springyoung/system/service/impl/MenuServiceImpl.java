package org.springyoung.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springyoung.system.entity.Menu;
import org.springyoung.system.entity.router.RouterMeta;
import org.springyoung.system.entity.router.VueRouter;
import org.springyoung.system.mapper.MenuMapper;
import org.springyoung.system.service.IMenuService;
import org.springyoung.system.utils.TreeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    /**
     *  findUserPermissions方法的实现逻辑为：通过用户名查询出用户权限集合。
     *
     * @param username 用户名
     * @return
     */
    @Override
    public Set<String> findUserPermissions(String username) {
        List<Menu> userPermissions = this.baseMapper.findUserPermissions(username);
        return userPermissions.stream().map(Menu::getPerms).collect(Collectors.toSet());
    }

    /**
     *  getUserRouters方法的实现逻辑为：
     *  通过用户名查询出用户菜单集合，然后遍历集合，将菜单对象一一转换为路由对象，
     *  然后添加到路由集合中。这时候的路由集合是没有层级结构的，
     *  我们可以通过TreeUtil的buildVueRouter方法，
     *  将路由集合转换为包含层级结构的路由信息
     *
     * @param username 用户名
     * @return
     */
    @Override
    public List<VueRouter<Menu>> getUserRouters(String username) {
        List<VueRouter<Menu>> routes = new ArrayList<>();
        List<Menu> menus = this.baseMapper.findUserMenus(username);
        menus.forEach(menu -> {
            VueRouter<Menu> route = new VueRouter<>();
            route.setId(menu.getId().toString());
            route.setParentId(menu.getParentId().toString());
            route.setPath(menu.getPath());
            route.setComponent(menu.getComponent());
            route.setName(menu.getMenuName());
            route.setMeta(new RouterMeta(menu.getMenuName(), menu.getIcon()));
            routes.add(route);
        });
        return TreeUtil.buildVueRouter(routes);
    }
}