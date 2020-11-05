package org.springyoung.auth.translator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;
import org.springyoung.core.response.R;
import org.springyoung.core.response.ResultCode;

/**
 * YoungWebResponseExceptionTranslator实现了WebResponseExceptionTranslator接口，用于覆盖默认的认证异常响应
 * 定义一个异常翻译器，将这些认证类型异常翻译为友好的格式
 */
@Slf4j
@Component
public class YoungWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity translate(Exception e) {
        ResponseEntity.BodyBuilder status = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        String message = "认证失败";
        int code = ResultCode.INTERNAL_SERVER_ERROR.getCode();
        log.error(message, e);
        if (e instanceof UnsupportedGrantTypeException) {
            message = "不支持该认证类型";
            return status.body(R.fail(code, message));
        }
        if (e instanceof InvalidGrantException) {
            if (StringUtils.containsIgnoreCase(e.getMessage(), "Invalid refresh token")) {
                message = "refresh token无效";
                return status.body(R.fail(code, message));
            }
            if (StringUtils.containsIgnoreCase(e.getMessage(), "locked")) {
                message = "用户已被锁定，请联系管理员";
                return status.body(R.fail(code, message));
            }
            message = "用户名或密码错误";
            return status.body(R.fail(code, message));
        }
        return status.body(R.fail(code, message));
    }
}