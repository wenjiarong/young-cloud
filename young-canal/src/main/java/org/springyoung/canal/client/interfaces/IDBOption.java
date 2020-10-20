package org.springyoung.canal.client.interfaces;

import com.alibaba.otter.canal.protocol.CanalEntry;

/**
 * 数据库操作接口层
 *
 */
@FunctionalInterface
public interface IDBOption {
	
	/**
	 * 操作
	 *
	 */
	void doOption(String destination, String schemaName, String tableName, CanalEntry.RowChange rowChange);
}
