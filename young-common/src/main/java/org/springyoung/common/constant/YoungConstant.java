package org.springyoung.common.constant;

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
     * gif类型
     */
    String GIF = "gif";
    /**
     * png类型
     */
    String PNG = "png";

    /**
     * 验证码 key前缀
     */
    String CODE_PREFIX = "young.captcha.";

}