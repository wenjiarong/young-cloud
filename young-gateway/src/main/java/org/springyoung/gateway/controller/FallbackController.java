package org.springyoung.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springyoung.core.tool.api.R;
import org.springyoung.core.tool.api.ResultCode;
import reactor.core.publisher.Mono;

/**
 * 定义处理熔断回退的Controller
 */
@RestController
public class FallbackController {

    @RequestMapping("fallback/{name}")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<R> systemFallback(@PathVariable String name) {
        String response = String.format("访问%s超时或者服务不可用", name);
        return Mono.just(R.fail(ResultCode.INTERNAL_SERVER_ERROR.getCode(), response));
    }

}