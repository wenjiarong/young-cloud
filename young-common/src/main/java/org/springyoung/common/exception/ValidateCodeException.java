package org.springyoung.common.exception;

/**
 * 定义一个验证码类型异常
 */
public class ValidateCodeException extends Exception{

    private static final long serialVersionUID = 7514854456967620043L;

    public ValidateCodeException(String message){
        super(message);
    }

}