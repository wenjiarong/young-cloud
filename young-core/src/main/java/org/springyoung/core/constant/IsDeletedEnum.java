package org.springyoung.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsDeletedEnum {
	/**
	 *已逻辑删除
	 */
	yes("yes", "1"),

	/**
	 * 未逻辑删除
	 */
	no("no", "0"),
	;

	final String name;
	final String status;
}
