package org.springyoung.canal.test;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springyoung.canal.annotation.CanalEventListener;
import org.springyoung.canal.annotation.content.DeleteListenPoint;
import org.springyoung.canal.annotation.content.InsertListenPoint;
import org.springyoung.canal.annotation.content.UpdateListenPoint;
import org.springyoung.canal.annotation.table.AlertTableListenPoint;
import org.springyoung.canal.annotation.table.CreateIndexListenPoint;
import org.springyoung.canal.annotation.table.CreateTableListenPoint;
import org.springyoung.canal.annotation.table.DropTableListenPoint;
import org.springyoung.canal.client.core.CanalMsg;
import org.springyoung.canal.entity.UserCanal;
import org.springyoung.canal.utils.RowDataMapUtil;

import java.util.List;

/**
 * 注解方法测试
 */
@CanalEventListener
@Slf4j
public class MyAnnoEventListener {

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

    @DeleteListenPoint
    public void onEventDeleteData(CanalEntry.RowChange rowChange, CanalMsg canalMsg) {
        log.info("======================注解方式（删除数据操作）==========================");
        List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
        for (CanalEntry.RowData rowData : rowDatasList) {
            if (!CollectionUtils.isEmpty(rowData.getBeforeColumnsList())) {
                String sql = "use " + canalMsg.getSchemaName() + ";\n";
                sql += "DELETE FROM " + canalMsg.getTableName() + " WHERE ";
                StringBuffer idKey = new StringBuffer();
                StringBuffer idValue = new StringBuffer();
                for (CanalEntry.Column c : rowData.getBeforeColumnsList()) {
                    if (c.getIsKey()) {
                        idKey.append(c.getName());
                        idValue.append(c.getValue());
                        break;
                    }
                }
                sql += idKey + " =" + idValue + ";";
                log.info(sql);
            }
            log.info("\n======================================================");
            UserCanal user = RowDataMapUtil.delToMap(rowData, UserCanal.class);
            log.info(user.toString());
        }
    }

    @CreateTableListenPoint
    public void onEventCreateTable(CanalEntry.RowChange rowChange) {
        log.info("======================注解方式（创建表操作）==========================");
        log.info("use " + rowChange.getDdlSchemaName() + ";\n" + rowChange.getSql());
        log.info("\n======================================================");
    }

    @DropTableListenPoint
    public void onEventDropTable(CanalEntry.RowChange rowChange) {
        log.info("======================注解方式（删除表操作）==========================");
        log.info("use " + rowChange.getDdlSchemaName() + ";\n" + rowChange.getSql());
        log.info("\n======================================================");
    }

    @AlertTableListenPoint
    public void onEventAlertTable(CanalEntry.RowChange rowChange) {
        log.info("======================注解方式（修改表信息操作）==========================");
        log.info("use " + rowChange.getDdlSchemaName() + ";\n" + rowChange.getSql());
        log.info("\n======================================================");
    }

    @CreateIndexListenPoint
    public void onEventCreateIndex(CanalMsg canalMsg, CanalEntry.RowChange rowChange) {
        log.info("======================注解方式（创建索引操作）==========================");
        log.info("use " + canalMsg.getSchemaName() + ";\n" + rowChange.getSql());
        log.info("\n======================================================");
    }

}
