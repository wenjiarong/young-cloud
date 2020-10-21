package org.springyoung.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springyoung.common.exception.YoungAuthException;
import org.springyoung.common.exception.YoungException;
import org.springyoung.common.response.R;
import org.springyoung.common.response.ResultCode;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.Set;

/**
 * 全局异常处理指的是全局处理Controller层抛出来的异常。
 * 因为全局异常处理器在各个微服务系统里都能用到，所以我们把它定义在young-common模块里
 */
@Slf4j
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleException(Exception e) {
        log.error("系统内部异常，异常信息", e);
        return R.fail(ResultCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = YoungAuthException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleYoungAuthException(YoungAuthException e) {
        log.error("系统错误", e);
        return R.fail(ResultCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public R handleAccessDeniedException() {
        return R.fail(ResultCode.REQ_REJECT);
    }

    @ExceptionHandler(value = YoungException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleYoungException(YoungException e) {
        log.error("系统错误", e);
        return R.fail(ResultCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * 统一处理请求参数校验(普通传参)
     * 当普通类型参数校验不合法时，控制器层会抛出javax.validation.ConstraintViolationException异常
     *
     * @param e ConstraintViolationException
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(path.toString(), ".");
            message.append(pathArr[1]).append(violation.getMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return R.fail(ResultCode.MSG_NOT_READABLE);
    }


    /**
     * 统一处理请求参数校验(实体对象传参)
     * 使用实体对象传参的方式参数校验需要在相应的参数前加上@Valid注解。
     * 当校验不通过时，控制器层将抛出BindException类型异常
     *
     * @param e BindException
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R handleBindException(BindException e) {
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return R.fail(ResultCode.MSG_NOT_READABLE);
    }

}