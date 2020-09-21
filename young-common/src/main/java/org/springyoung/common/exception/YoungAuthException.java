package org.springyoung.common.exception;

/**
 * 定义一个授权服务异常
 */
public class YoungAuthException extends Exception {

    private static final long serialVersionUID = -6916154462432027437L;

    public YoungAuthException(String message) {
        super(message);
    }

}