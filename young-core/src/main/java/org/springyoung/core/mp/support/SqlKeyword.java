package org.springyoung.core.mp.support;

public class SqlKeyword {
    private static final String SQL_REGEX = "'|%|--|insert|delete|select|count|group|union|drop|truncate|alter|grant|execute|exec|xp_cmdshell|call|declare|sql";
    private static final String EQUAL = "_equal";
    private static final String NOT_EQUAL = "_notequal";
    private static final String LIKE = "_like";
    private static final String LIKE_LEFT = "_likeleft";
    private static final String LIKE_RIGHT = "_likeright";
    private static final String NOT_LIKE = "_notlike";
    private static final String GE = "_ge";
    private static final String LE = "_le";
    private static final String GT = "_gt";
    private static final String LT = "_lt";
    private static final String DATE_GE = "_datege";
    private static final String DATE_GT = "_dategt";
    private static final String DATE_EQUAL = "_dateequal";
    private static final String DATE_LT = "_datelt";
    private static final String DATE_LE = "_datele";
    private static final String IS_NULL = "_null";
    private static final String NOT_NULL = "_notnull";
    private static final String IGNORE = "_ignore";

    public SqlKeyword() {
    }

    public static String filter(String param) {
        return param == null ? null : param.replaceAll("(?i)'|%|--|insert|delete|select|count|group|union|drop|truncate|alter|grant|execute|exec|xp_cmdshell|call|declare|sql", "");
    }

}
