package org.springyoung.canal.client.transfer;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import org.springframework.util.StringUtils;
import org.springyoung.canal.annotation.ListenPoint;
import org.springyoung.canal.client.abstracts.AbstractBasicMessageTransponder;
import org.springyoung.canal.client.core.CanalMsg;
import org.springyoung.canal.client.core.ListenerPoint;
import org.springyoung.canal.client.interfaces.CanalEventListener;
import org.springyoung.canal.config.CanalConfigure;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * 默認的信息轉換器
 *
 */
public class DefaultMessageTransponder extends AbstractBasicMessageTransponder {
	
	
	public DefaultMessageTransponder(CanalConnector connector, Map.Entry<String, CanalConfigure.Instance> config, List<CanalEventListener> listeners, List<ListenerPoint> annoListeners) {
		super(connector, config, listeners, annoListeners);
	}
	
	
	/**
	 * 断言注解方式的监听过滤规则
	 *
	 * @param destination 指定
	 * @param schemaName  数据库实例
	 * @param tableName   表名称
	 * @param eventType   事件类型
	 */
	@Override
	protected Predicate<Map.Entry<Method, ListenPoint>> getAnnotationFilter(String destination, String schemaName, String tableName, CanalEntry.EventType eventType) {
		//看看指令是否正确
		Predicate<Map.Entry<Method, ListenPoint>> df = e -> StringUtils.isEmpty(e.getValue().destination())
				|| e.getValue().destination().equals(destination) || destination == null;
		
		//看看数据库实例名是否一样
		Predicate<Map.Entry<Method, ListenPoint>> sf = e -> e.getValue().schema().length == 0
				|| Arrays.stream(e.getValue().schema()).anyMatch(s -> s.equals(schemaName)) || schemaName == null;
		
		//看看表名是否一样
		Predicate<Map.Entry<Method, ListenPoint>> tf = e -> e.getValue().table().length == 0
				|| Arrays.stream(e.getValue().table()).anyMatch(t -> t.equals(tableName)) || tableName == null;
		
		//类型一致？
		Predicate<Map.Entry<Method, ListenPoint>> ef = e -> e.getValue().eventType().length == 0
				|| Arrays.stream(e.getValue().eventType()).anyMatch(ev -> ev == eventType) || eventType == null;
		
		return df.and(sf).and(tf).and(ef);
	}
	
	/**
	 * 获取处理的参数
	 *
	 * @param method    监听的方法
	 * @param canalMsg  事件节点
	 * @param rowChange 詳細參數
	 */
	@Override
	protected Object[] getInvokeArgs(Method method, CanalMsg canalMsg, CanalEntry.RowChange rowChange) {
		return Arrays.stream(method.getParameterTypes())
				.map(p -> p == CanalMsg.class ? canalMsg : p == CanalEntry.RowChange.class ? rowChange : null)
				.toArray();
	}
	
	
	/**
	 * 忽略实体类的类型
	 *
	 */
	@Override
	protected List<CanalEntry.EntryType> getIgnoreEntryTypes() {
		return Arrays.asList(CanalEntry.EntryType.TRANSACTIONBEGIN, CanalEntry.EntryType.TRANSACTIONEND, CanalEntry.EntryType.HEARTBEAT);
	}
}
