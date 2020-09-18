package org.springyoung.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import org.springyoung.common.constant.YoungConstant;
import reactor.core.publisher.Mono;

/**
 * @ClassName 全局过滤器
 * @Description TODO
 * @Author 小温
 * @Date 2020/9/18 17:25
 * @Version 1.0
 */
public class YoungGatewayRequestFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //通过ServerWebExchange对象获取到ServerHttpRequest请求和ServerHttpResponse响应对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        byte[] token = Base64Utils.encode((YoungConstant.GATEWAY_TOKEN_VALUE).getBytes());
        //通过ServerHttpRequest的mutate方法可以修改请求，在请求头部添加了之前定义的gateway网关密钥
        ServerHttpRequest build = request.mutate()
                .header(YoungConstant.GATEWAY_TOKEN_HEADER, new String(token))
                .build();
        //修改了ServerHttpRequest对象后，需要将它设置到ServerWebExchange对象中
        //同样的，我们可以调用ServerWebExchange的mutate方法来修改ServerWebExchange
        ServerWebExchange newExchange = exchange.mutate().request(build).build();
        //然后将新的ServerWebExchange添加到GatewayFilterChain过滤器链中
        return chain.filter(newExchange);
    }

}
