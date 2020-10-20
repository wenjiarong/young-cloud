package org.springyoung.canal.client.abstracts.option.table;

import com.alibaba.otter.canal.protocol.CanalEntry;
import org.springyoung.canal.client.abstracts.option.AbstractDBOption;

/**
 * 创建表操作
 *
 */
public abstract class CreateTableOption extends AbstractDBOption {
	
	/**
	 * 创建表操作
	 *
	 */
	@Override
	protected void setEventType() {
		this.eventType = CanalEntry.EventType.CREATE;
	}
}
