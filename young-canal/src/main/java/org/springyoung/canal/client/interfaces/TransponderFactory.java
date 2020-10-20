package org.springyoung.canal.client.interfaces;

import com.alibaba.otter.canal.client.CanalConnector;
import org.springyoung.canal.client.core.ListenerPoint;
import org.springyoung.canal.config.CanalConfig;

import java.util.List;
import java.util.Map;

/**
 * 信息转换工厂类接口层
 *
 */
@FunctionalInterface
public interface TransponderFactory {
	
	/**
	 * @param connector        canal 连接工具
	 * @param config           canal 链接信息
	 * @param listeners 实现接口的监听器
	 * @param annoListeners    注解监听拦截
	 */
	MessageTransponder newTransponder(CanalConnector connector, Map.Entry<String, CanalConfig.Instance> config, List<CanalEventListener> listeners, List<ListenerPoint> annoListeners);
}
