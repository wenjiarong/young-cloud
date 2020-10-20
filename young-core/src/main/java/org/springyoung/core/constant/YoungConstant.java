package org.springyoung.core.constant;

public interface YoungConstant {

    /**
     * gateway请求头TOKEN名称（不要有空格）
     */
    String GATEWAY_TOKEN_HEADER = "gatewayToken";
    /**
     * gateway请求头TOKEN值
     */
    String GATEWAY_TOKEN_VALUE = "young:gateway:123456";

    /**
     * JWT的密钥
     */
    String JWT_KEY = "young";

    /**
     * gif类型
     */
    String GIF = "gif";
    /**
     * png类型
     */
    String PNG = "png";

}