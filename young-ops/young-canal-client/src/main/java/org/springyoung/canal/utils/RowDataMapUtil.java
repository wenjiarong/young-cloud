package org.springyoung.canal.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.protocol.CanalEntry;

/**
 * @ClassName RowDataMapUtil
 * @Description TODO
 * @Author 小温
 * @Version 1.0
 */
public class RowDataMapUtil {

	public static <T> T rowToMap(CanalEntry.RowData rowData, Class<T> clazz) {
		StringBuffer json = new StringBuffer();
		rowData.getAfterColumnsList().forEach((c) -> {
			json.append("\"" + c.getName().replace("\"", "") + "\":\""
				+ c.getValue().replace("\"", "") + "\",");
		});
		json.deleteCharAt(json.length() - 1);
		json.insert(0, "{");
		json.insert(json.length(), "}");
		return JSONObject.parseObject(json.toString(), clazz);
	}

	public static <T> T delToMap(CanalEntry.RowData rowData, Class<T> clazz) {
		StringBuffer json = new StringBuffer();
		rowData.getBeforeColumnsList().forEach((c) -> {
			json.append("\"" + c.getName().replace("\"", "") + "\":\""
				+ c.getValue().replace("\"", "") + "\",");
		});
		json.deleteCharAt(json.length() - 1);
		json.insert(0, "{");
		json.insert(json.length(), "}");
		return JSONObject.parseObject(json.toString(), clazz);
	}

}
