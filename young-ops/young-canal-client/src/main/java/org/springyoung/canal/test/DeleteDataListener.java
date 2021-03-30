package org.springyoung.canal.test;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springyoung.canal.annotation.CanalEventListener;
import org.springyoung.canal.annotation.content.DeleteListenPoint;
import org.springyoung.canal.client.core.CanalMsg;
import org.springyoung.canal.entity.UserCanal;
import org.springyoung.canal.utils.RowDataMapUtil;

import java.util.List;

/**
 * @ClassName: DeleteDataListener
 * @Author: 小温
 * @Date: 2020/5/30 21:03
 * @Version: 1.0
 */
@CanalEventListener
@Slf4j
public class DeleteDataListener {

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

}
