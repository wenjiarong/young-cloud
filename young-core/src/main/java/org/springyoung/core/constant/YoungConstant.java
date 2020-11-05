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

    /**
     * 默认为空消息
     */
    String DEFAULT_NULL_MESSAGE = "暂无承载数据";
    /**
     * 默认成功消息
     */
    String DEFAULT_SUCCESS_MESSAGE = "操作成功";
    /**
     * 默认失败消息
     */
    String DEFAULT_FAILURE_MESSAGE = "操作失败";

}