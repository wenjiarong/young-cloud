package org.springyoung.demo.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springyoung.common.handler.BaseExceptionHandler;

/**
 * 通用的异常类型捕获可以在BaseExceptionHandler中定义，
 * 而当前微服务系统独有的异常类型捕获可以在GlobalExceptionHandler中定义
 */

@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends BaseExceptionHandler {

}