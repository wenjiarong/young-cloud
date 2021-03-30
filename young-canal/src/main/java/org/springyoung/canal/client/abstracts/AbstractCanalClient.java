package org.springyoung.canal.client.abstracts;


import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import org.apache.commons.lang3.StringUtils;
import org.springyoung.canal.client.exception.CanalClientException;
import org.springyoung.canal.client.interfaces.CanalClient;
import org.springyoung.canal.client.interfaces.TransponderFactory;
import org.springyoung.canal.client.transfer.DefaultMessageTransponder;
import org.springyoung.canal.config.CanalConfigure;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Canal 客户端抽象类
 */
public abstract class AbstractCanalClient implements CanalClient {

    /**
     * 运行状态
     */
    private volatile boolean running;

    /**
     * canal 配置
     */
    private CanalConfigure canalConfig;

    /**
     * 转换工厂类
     */
    protected final TransponderFactory factory;

    /**
     * 构造方法，初始化 canal 的配置以及转换信息的工厂实例
     */
    protected AbstractCanalClient(CanalConfigure canalConfig) {
        //参数校验
        Objects.requireNonNull(canalConfig, "canalConfig 不能为空!");
        Objects.requireNonNull(canalConfig, "transponderFactory 不能为空!");
        //初始化配置
        this.canalConfig = canalConfig;
        this.factory = (connector, config, listeners, annoListeners) -> new DefaultMessageTransponder(connector, config, listeners, annoListeners);
    }

    /**
     * 开启 canal 客户端
     */
    @Override
    public void start() {
        //可能有多个客户端
        Map<String, CanalConfigure.Instance> instanceMap = getConfig();
        //遍历开启进程
        for (Map.Entry<String, CanalConfigure.Instance> instanceEntry : instanceMap.entrySet()) {
            process(processInstanceEntry(instanceEntry), instanceEntry);
        }

    }

    /**
     * 初始化 canal 连接
     */
    protected abstract void process(CanalConnector connector, Map.Entry<String, CanalConfigure.Instance> config);

    /**
     * 处理 canal 连接实例
     */
    private CanalConnector processInstanceEntry(Map.Entry<String, CanalConfigure.Instance> instanceEntry) {
        //获取配置
        CanalConfigure.Instance instance = instanceEntry.getValue();
        //声明连接
        CanalConnector connector;
        //是否是集群模式
        if (instance.isClusterEnabled()) {
            //zookeeper 连接集合
            List<SocketAddress> addresses = new ArrayList<>();
            for (String s : instance.getZookeeperAddress()) {
                String[] entry = s.split(":");
                if (entry.length != 2) {
                    throw new CanalClientException("zookeeper 地址格式不正确，应该为 ip:port....:" + s);
                }
                //若符合设定规则，先加入集合
                addresses.add(new InetSocketAddress(entry[0], Integer.parseInt(entry[1])));
            }
            //若集群的话，使用 newClusterConnector 方法初始化
            connector = CanalConnectors.newClusterConnector(addresses, instanceEntry.getKey(), instance.getUserName(), instance.getPassword());
        } else {
            //若不是集群的话，使用 newSingleConnector 初始化
            connector = CanalConnectors.newSingleConnector(new InetSocketAddress(instance.getHost(), instance.getPort()), instanceEntry.getKey(), instance.getUserName(), instance.getPassword());
        }
        //canal 连接
        connector.connect();
        if (!StringUtils.isEmpty(instance.getFilter())) {
            //canal 连接订阅，包含过滤规则
            connector.subscribe(instance.getFilter());
        } else {
            //canal 连接订阅，无过滤规则
            connector.subscribe();
        }
        //canal 连接反转
        connector.rollback();
        //返回 canal 连接
        return connector;
    }


    /**
     * 获取 canal 配置
     */
    protected Map<String, CanalConfigure.Instance> getConfig() {
        //canal 配置
        CanalConfigure config = canalConfig;
        Map<String, CanalConfigure.Instance> instanceMap;
        if (config != null && (instanceMap = config.getInstances()) != null && !instanceMap.isEmpty()) {
            //返回配置实例
            return config.getInstances();
        } else {
            throw new CanalClientException("无法解析 canal 的连接信息，请联系开发人员!");
        }
    }

    /**
     * 停止 canal 客户端
     */
    @Override
    public void stop() {
        setRunning(false);
    }

    /**
     * 返回 canal 客户端的状态
     */
    @Override
    public boolean isRunning() {
        return running;
    }

    /**
     * 设置 canal 客户端状态
     */
    private void setRunning(boolean running) {
        this.running = running;
    }
}