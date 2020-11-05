package org.springyoung.core.mp.support;

import com.baomidou.mybatisplus.core.toolkit.StringPool;

/**
 * 定义常用的 sql关键字
 *
 */
public class SqlKeyword {

    private final static String SQL_REGEX = "'|%|--|insert|delete|select|count|group|union|drop|truncate|alter|grant|execute|exec|xp_cmdshell|call|declare|sql";

    /**
     * 把SQL关键字替换为空字符串
     *
     * @param param 关键字
     * @return string
     */
    public static String filter(String param) {
        if (param == null) {
            return null;
        }
        return param.replaceAll("(?i)" + SQL_REGEX, StringPool.EMPTY);
    }

}

