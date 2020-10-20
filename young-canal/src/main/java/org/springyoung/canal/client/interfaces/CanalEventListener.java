package org.springyoung.canal.client.interfaces;

import com.alibaba.otter.canal.protocol.CanalEntry;

/**
 * canal 的事件接口层（表数据改变）
 *
 */
@FunctionalInterface
public interface CanalEventListener {
	
	
	/**
	 * 处理事件
	 *
	 */
	void onEvent(String destination, String schemaName, String tableName, CanalEntry.RowChange rowChange);
	
}
