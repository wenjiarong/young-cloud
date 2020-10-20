package org.springyoung.canal.client.abstracts.option;

import com.alibaba.otter.canal.protocol.CanalEntry;
import org.springyoung.canal.client.interfaces.IDBOption;

/**
 * 数据库操作抽象类
 *
 */
public abstract class AbstractDBOption implements IDBOption {
	
	/**
	 * 操作类型
	 */
	protected CanalEntry.EventType eventType;
	/**
	 * 下一个节点
	 */
	protected AbstractDBOption next;
	
	
	
	/**
	 * 默认构造方法
	 *
	 */
	public AbstractDBOption() {
		this.setEventType();
	}
	
	/**
	 * 进行类型设置
	 *
	 */
	protected abstract void setEventType();
	
	
	/**
	 * 设置下一个节点
	 *
	 */
	public void setNext(AbstractDBOption next) {
		this.next = next;
	}
	
	
	
	/**
	 * 责任链处理
	 *
	 */
	public void doChain(String destination, String schemaName, String tableName, CanalEntry.RowChange rowChange) {
		if (this.eventType.equals(rowChange.getEventType())) {
			this.doOption(destination, schemaName, tableName, rowChange);
		} else {
			if(this.next==null){
				return;
			}
			this.next.doChain(destination, schemaName, tableName,rowChange);
		}
	}
	
	
}
