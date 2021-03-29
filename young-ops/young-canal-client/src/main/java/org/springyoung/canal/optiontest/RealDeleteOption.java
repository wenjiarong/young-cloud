package org.springyoung.canal.optiontest;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springyoung.canal.client.abstracts.option.content.DeleteOption;

import java.util.List;

/**
 * 需要自己实现的删除处理机制
 */
@Component
@Slf4j
public class RealDeleteOption extends DeleteOption {

    /**
     * 删除操作操作
     *
     * @param destination 指令
     * @param schemaName  实例名称
     * @param tableName   表名称
     * @param rowChange   数据
     */
    @Override
    public void doOption(String destination, String schemaName, String tableName, CanalEntry.RowChange rowChange) {
        log.info("======================接口方式（删除数据操作）==========================");
        List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
        for (CanalEntry.RowData rowData : rowDatasList) {
            if (!CollectionUtils.isEmpty(rowData.getBeforeColumnsList())) {
                String sql = "use " + schemaName + ";\n";
                sql += "DELETE FROM " + tableName + " WHERE ";
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
        }
    }

}
