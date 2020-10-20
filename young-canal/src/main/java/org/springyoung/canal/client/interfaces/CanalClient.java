package org.springyoung.canal.client.interfaces;

/**
 * canal 客户端接口层
 *
 */


public interface CanalClient {

    /**
     * 开启 canal 客户端，并根绝配置连接到 canal ,然后进行针对性的监听
     *
     */
    void start();
    
    
    /**
     * 关闭 canal 客户端
     *
     */
    void stop();
    
    /**
     * 判断 canal 客户端是否是开启状态
     *
     */
    boolean isRunning();
}
