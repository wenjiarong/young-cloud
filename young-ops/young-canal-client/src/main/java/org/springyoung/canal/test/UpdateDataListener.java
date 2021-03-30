package org.springyoung.canal.test;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;
import org.springyoung.canal.annotation.CanalEventListener;
import org.springyoung.canal.annotation.content.UpdateListenPoint;
import org.springyoung.canal.client.core.CanalMsg;
import org.springyoung.canal.entity.UserCanal;
import org.springyoung.canal.utils.RowDataMapUtil;

import java.util.List;

/**
 * @ClassName: UpdateDataListener
 * @Author: 小温
 * @Date: 2020/5/30 21:03
 * @Version: 1.0
 */
@CanalEventListener
@Slf4j
public class UpdateDataListener {

    @UpdateListenPoint
    public void onEventUpdateData(CanalMsg canalMsg, CanalEntry.RowChange rowChange) {
        log.info("======================注解方式（更新数据操作）==========================");
        List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
        for (CanalEntry.RowData rowData : rowDatasList) {
            String sql = "use " + canalMsg.getSchemaName() + ";\n";
            StringBuffer updates = new StringBuffer();
            StringBuffer conditions = new StringBuffer();
            rowData.getAfterColumnsList().forEach((c) -> {
                if (c.getIsKey()) {
                    conditions.append(c.getName() + "='" + c.getValue() + "'");
                } else {
                    updates.append(c.getName() + "='" + c.getValue() + "',");
                }
            });
            sql += "UPDATE " + canalMsg.getTableName() + " SET " + updates.substring(0, updates.length() - 1) + " WHERE " + conditions;
            log.info(sql);
            UserCanal user = RowDataMapUtil.rowToMap(rowData, UserCanal.class);
            log.info(user.toString());
        }
        log.info("\n======================================================");
    }

}
