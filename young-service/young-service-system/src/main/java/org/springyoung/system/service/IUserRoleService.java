package org.springyoung.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springyoung.system.entity.UserRole;

public interface IUserRoleService extends IService<UserRole> {

	void deleteUserRolesByRoleId(String[] roleIds);

	void deleteUserRolesByUserId(String[] userIds);

}