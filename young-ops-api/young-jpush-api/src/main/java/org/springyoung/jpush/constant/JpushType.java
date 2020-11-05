package org.springyoung.jpush.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JpushType {

    notice("公告", "0");

    String name;
    String sign;
}
