package org.springyoung.canal.client.abstracts.option.content;

import com.alibaba.otter.canal.protocol.CanalEntry;
import org.springyoung.canal.client.abstracts.option.AbstractDBOption;

/**
 * 更新数据
 *
 */

public abstract class UpdateOption extends AbstractDBOption {
	
	
	/**
	 * 设置更新属性
	 *
	 */
	@Override
	protected void setEventType() {
		this.eventType = CanalEntry.EventType.UPDATE;
	}
	
}
