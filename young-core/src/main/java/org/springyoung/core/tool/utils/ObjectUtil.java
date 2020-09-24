//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springyoung.core.tool.utils;

import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

public class ObjectUtil extends ObjectUtils {

    public ObjectUtil() {
    }

    public static boolean isNotEmpty(@Nullable Object obj) {
        return !isEmpty(obj);
    }

}
