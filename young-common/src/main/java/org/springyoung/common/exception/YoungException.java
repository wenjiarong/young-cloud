package org.springyoung.common.exception;

/**
 *  Young系统通用业务异常
 */
public class YoungException extends Exception{

    private static final long serialVersionUID = -6916154462432027437L;

    public YoungException(String message){
        super(message);
    }

}