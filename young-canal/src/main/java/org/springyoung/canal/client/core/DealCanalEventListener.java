package org.springyoung.canal.client.core;

import com.alibaba.otter.canal.protocol.CanalEntry;
import org.springframework.util.CollectionUtils;
import org.springyoung.canal.client.abstracts.option.AbstractDBOption;
import org.springyoung.canal.client.interfaces.CanalEventListener;

import java.util.List;

/**
 * 处理 Canal 监听器
 *
 */
@SuppressWarnings("all")
public class DealCanalEventListener implements CanalEventListener {
	
	/**
	 * 頭結點
	 */
	private AbstractDBOption header;
	
	/**
	 * 默認構造方法，必須傳入鏈路
	 *
	 */
	public DealCanalEventListener(AbstractDBOption... dbOptions) {
		AbstractDBOption tmp = null;
		for (AbstractDBOption dbOption : dbOptions) {
			if (tmp != null) {
				tmp.setNext(dbOption);
			} else {
				this.header = dbOption;
			}
			tmp = dbOption;
		}
		
	}
	
	public DealCanalEventListener(List<AbstractDBOption> dbOptions) {
		if (CollectionUtils.isEmpty(dbOptions)) {
			return;
		}
		AbstractDBOption tmp = null;
		for (AbstractDBOption dbOption : dbOptions) {
			if (tmp != null) {
				tmp.setNext(dbOption);
			} else {
				this.header = dbOption;
			}
			tmp = dbOption;
		}
	}
	
	/**
	 * 处理数据库的操作
	 *
	 * @param destination
	 * @param schemaName
	 * @param tableName
	 * @param rowChange
	 */
	@Override
	public void onEvent(String destination, String schemaName, String tableName, CanalEntry.RowChange rowChange) {
		this.header.doChain(destination, schemaName, tableName, rowChange);
	}
	
	
}
