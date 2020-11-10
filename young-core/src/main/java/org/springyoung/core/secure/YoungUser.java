package org.springyoung.core.secure;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户实体
 *
 */
@Data
public class YoungUser implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 客户端id
	 */
	@ApiModelProperty(hidden = true)
	private String clientId;
	/**
	 * 用户id
	 */
	@ApiModelProperty(hidden = true)
	private Long userId;
	/**
	 * 账号
	 */
	@ApiModelProperty(hidden = true)
	private String account;
	/**
	 * 用户名
	 */
	@ApiModelProperty(hidden = true)
	private String userName;
	/**
	 * 租户ID
	 */
	@ApiModelProperty(hidden = true)
	private String tenantId;
	/**
	 * 第三方认证ID
	 */
	@ApiModelProperty(hidden = true)
	private String oauthId;
	/**
	 * 部门id
	 */
	@ApiModelProperty(hidden = true)
	private Long deptId;
	/**
	 * 岗位id
	 */
	@ApiModelProperty(hidden = true)
	private String postId;
	/**
	 * 角色id
	 */
	@ApiModelProperty(hidden = true)
	private String roleIds;
	/**
	 * 角色名
	 */
	@ApiModelProperty(hidden = true)
	private String roleNames;

}