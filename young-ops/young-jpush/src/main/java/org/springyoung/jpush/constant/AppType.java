package org.springyoung.jpush.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppType {

	ios("苹果系统", "ios"),
	android("安卓系统", "android");

	String name;
	String sign;

}
