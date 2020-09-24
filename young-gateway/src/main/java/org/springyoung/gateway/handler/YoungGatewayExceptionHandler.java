package org.springyoung.gateway.handler;

import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.TimeoutException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

/**
 *  创建自定义异常类覆盖默认异常DefaultErrorWebExceptionHandler
 *
 *  YoungGatewayExceptionHandler代码参考DefaultErrorWebExceptionHandler类的源码实现。
 *  在getHttpStatus方法中，定义了状态码为500（可以通过不同的异常类型来返回不同的状态码）；
 *  在getErrorAttributes方法中，我们根据异常的类型或者异常的错误信息来归类，构建了响应的异常信息对象。
 */
@Slf4j
public class YoungGatewayExceptionHandler extends DefaultErrorWebExceptionHandler {

    public YoungGatewayExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
                                        ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    /**
     * 异常处理，定义返回报文格式
     */
    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Throwable error = super.getError(request);
        log.error(
                "请求发生异常，请求URI：{}，请求方法：{}，异常信息：{}",
                request.path(), request.methodName(), error.getMessage()
        );
        String errorMessage;
        if (error instanceof NotFoundException) {
            String serverId = StringUtils.substringAfterLast(error.getMessage(), "Unable to find instance for ");
            serverId = StringUtils.replace(serverId, "\"", StringUtils.EMPTY);
            errorMessage = String.format("无法找到%s服务", serverId);
        } else if (StringUtils.containsIgnoreCase(error.getMessage(), "connection refused")) {
            errorMessage = "目标服务拒绝连接";
        } else if (error instanceof TimeoutException) {
            errorMessage = "访问服务超时";
        } else if (error instanceof ResponseStatusException
                && StringUtils.containsIgnoreCase(error.getMessage(), HttpStatus.NOT_FOUND.toString())) {
            errorMessage = "未找到该资源";
        } else if (error instanceof ParamFlowException){
            errorMessage = "访问频率超限";
        } else {
            errorMessage = "网关转发异常";
        }
        Map<String, Object> errorAttributes = new HashMap<>(3);
        errorAttributes.put("message", errorMessage);
        return errorAttributes;
    }

    @Override
    @SuppressWarnings("all")
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    @Override
    protected HttpStatus getHttpStatus(Map<String, Object> errorAttributes) {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}