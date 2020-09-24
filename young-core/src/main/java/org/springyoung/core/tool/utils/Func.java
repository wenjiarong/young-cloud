package org.springyoung.core.tool.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.lang.Nullable;

public class Func {

    public Func() {
    }

    public static String[] toStrArray(String str) {
        return toStrArray(",", str);
    }

    public static String[] toStrArray(String split, String str) {
        return isBlank(str) ? new String[0] : str.split(split);
    }

    public static boolean isBlank(@Nullable final CharSequence cs) {
        return StringUtils.isBlank(cs);
    }

    public static int toInt(final Object str) {
        return NumberUtil.toInt(String.valueOf(str));
    }

    public static int toInt(@Nullable final Object str, final int defaultValue) {
        return NumberUtil.toInt(String.valueOf(str), defaultValue);
    }

}
