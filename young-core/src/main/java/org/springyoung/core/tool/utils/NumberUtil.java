package org.springyoung.core.tool.utils;

import org.springframework.lang.Nullable;
import org.springframework.util.NumberUtils;

public class NumberUtil extends NumberUtils {
    private static final char[] DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public NumberUtil() {
    }

    public static int toInt(final String str) {
        return toInt(str, -1);
    }

    public static int toInt(@Nullable final String str, final int defaultValue) {
        if (str == null) {
            return defaultValue;
        } else {
            try {
                return Integer.valueOf(str);
            } catch (NumberFormatException var3) {
                return defaultValue;
            }
        }
    }

    public static long toLong(final String str) {
        return toLong(str, 0L);
    }

    public static long toLong(@Nullable final String str, final long defaultValue) {
        if (str == null) {
            return defaultValue;
        } else {
            try {
                return Long.valueOf(str);
            } catch (NumberFormatException var4) {
                return defaultValue;
            }
        }
    }

    public static Double toDouble(String value) {
        return toDouble(value, (Double)null);
    }

    public static Double toDouble(@Nullable String value, Double defaultValue) {
        return value != null ? Double.valueOf(value.trim()) : defaultValue;
    }

    public static Float toFloat(String value) {
        return toFloat(value, (Float)null);
    }

    public static Float toFloat(@Nullable String value, Float defaultValue) {
        return value != null ? Float.valueOf(value.trim()) : defaultValue;
    }

    public static String to62String(long i) {
        int radix = DIGITS.length;
        char[] buf = new char[65];
        int charPos = 64;

        for(i = -i; i <= (long)(-radix); i /= (long)radix) {
            buf[charPos--] = DIGITS[(int)(-(i % (long)radix))];
        }

        buf[charPos] = DIGITS[(int)(-i)];
        return new String(buf, charPos, 65 - charPos);
    }

}
