package org.springyoung.canal.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springyoung.canal.client.core.SimpleCanalClient;
import org.springyoung.canal.client.interfaces.CanalClient;
import org.springyoung.core.tool.utils.SpringUtil;


public class CanalClientConfigure {
    /**
     * 记录日志
     */
    private final static Logger logger = LoggerFactory.getLogger(CanalClientConfigure.class);

    /**
     * canal 配置
     */
    @Autowired
    private CanalConfigure canalConfig;
    
    /**
     * 返回 bean 工具类
     *
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SpringUtil beanUtil() {
        return new SpringUtil();
    }
    
    /**
     * 返回 canal 的客户端
     *
     */
    @Bean
    private CanalClient canalClient() {
        logger.info("正在尝试连接 canal 客户端....");
        //连接 canal 客户端
        //CanalClient canalClient = new SimpleCanalClient(canalConfig, MessageTransponders.defaultMessageTransponder());
        CanalClient canalClient = new SimpleCanalClient(canalConfig);
        logger.info("正在尝试开启 canal 客户端....");
        //开启 canal 客户端
        canalClient.start();
        logger.info("启动 canal 客户端成功....");
        //返回结果
        return canalClient;
    }
}
