package org.springyoung.canal.client.core;

/**
 * Canal 的一些信息
 *
 */
public class CanalMsg{
	
	/**
	 * 指令
	 */
	private String destination;
	/**
	 * 数据库实例名称
	 */
	private String schemaName;
	/**
	 * 数据库表名称
	 */
	private String tableName;
	
	public String getDestination() {
		return destination;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public String getSchemaName() {
		return schemaName;
	}
	
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
