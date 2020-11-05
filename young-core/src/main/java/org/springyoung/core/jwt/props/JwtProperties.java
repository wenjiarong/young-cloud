package org.springyoung.core.jwt.props;

import lombok.Data;

/**
 * JWT配置
 */
@Data
public class JwtProperties {

	/**
	 * token是否有状态
	 */
	private Boolean state = Boolean.FALSE;

	/**
	 * 是否只可同时在线一人
	 */
	private Boolean single = Boolean.FALSE;

	/**
	 * token签名
	 */
	private String signKey = "YoungX";

}