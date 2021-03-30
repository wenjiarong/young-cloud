package org.springyoung.canal.test;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;
import org.springyoung.canal.annotation.CanalEventListener;
import org.springyoung.canal.annotation.content.InsertListenPoint;
import org.springyoung.canal.client.core.CanalMsg;
import org.springyoung.canal.entity.UserCanal;
import org.springyoung.canal.utils.RowDataMapUtil;

import java.util.List;

/**
 * @ClassName: InsertDataListener
 * @Author: 小温
 * @Date: 2020/5/30 21:03
 * @Version: 1.0
 */
@CanalEventListener
@Slf4j
public class InsertDataListener {

    @InsertListenPoint
    public void onEventInsertData(CanalMsg canalMsg, CanalEntry.RowChange rowChange) {
        log.info("======================注解方式（新增数据操作）==========================");
        List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
        for (CanalEntry.RowData rowData : rowDatasList) {
            String sql = "use " + canalMsg.getSchemaName() + ";\n";
            StringBuffer colums = new StringBuffer();
            StringBuffer values = new StringBuffer();
            rowData.getAfterColumnsList().forEach((c) -> {
                colums.append(c.getName() + ",");
                values.append("'" + c.getValue() + "',");
            });
            sql += "INSERT INTO " + canalMsg.getTableName() + "(" + colums.substring(0, colums.length() - 1) + ") VALUES(" + values.substring(0, values.length() - 1) + ");";
            log.info(sql);
            UserCanal user = RowDataMapUtil.rowToMap(rowData, UserCanal.class);
            log.info(user.toString());
        }
        log.info("\n======================================================");
    }

}
